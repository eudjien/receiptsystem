package ru.clevertec.checksystem.cli.application;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.clevertec.checksystem.cli.argument.ArgumentsFinder;
import ru.clevertec.checksystem.cli.argument.CheckIdFilter;
import ru.clevertec.checksystem.cli.exception.ArgumentNotExistException;
import ru.clevertec.checksystem.cli.exception.ArgumentNotSupportValueException;
import ru.clevertec.checksystem.cli.task.*;
import ru.clevertec.checksystem.core.ProxyIdentifier;
import ru.clevertec.checksystem.core.entity.check.Check;
import ru.clevertec.checksystem.core.factory.service.ServiceFactory;
import ru.clevertec.custom.list.SynchronizedSinglyLinkedList;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static ru.clevertec.checksystem.cli.Constants.Inputs;
import static ru.clevertec.checksystem.cli.Constants.Keys;

@Component
public final class Application implements Callable<ApplicationResult> {

    private static final Logger logger = LogManager.getLogger("ConsumerLogger");

    private static final Map<String, String> startMap = new HashMap<>();
    private static final Map<String, String> errorsMap = new HashMap<>();

    private final ArgumentsFinder argumentsFinder;
    private final ServiceFactory serviceFactory;
    private final CheckIdFilter checkIdFilter;

    private final List<Check> checks = new SynchronizedSinglyLinkedList<>();

    static {
        startMap.put(Keys.SERIALIZE, "Serializing to a file...");
        startMap.put(Keys.PRINT, "Printing to a file...");
        startMap.put(Keys.GENERATE_SERIALIZE, "Serializing to a generate file...");
        errorsMap.put(Inputs.DESERIALIZE, "Something went wrong with deserializing from a file.");
        errorsMap.put(Inputs.DESERIALIZE_GENERATE, "Something went wrong with deserializing from a generate file.");
        errorsMap.put(Inputs.DATABASE, "Something went wrong with load from a database.");
        errorsMap.put(Keys.SERIALIZE, "Something went wrong with serializing to a file.");
        errorsMap.put(Keys.PRINT, "Something went wrong with printing to a file.");
        errorsMap.put(Keys.GENERATE_SERIALIZE, "Something went wrong with serializing to a generate file.");
    }

    @Autowired
    public Application(
            ArgumentsFinder argumentsFinder,
            ServiceFactory serviceFactory,
            CheckIdFilter checkIdFilter) {
        this.argumentsFinder = argumentsFinder;
        this.serviceFactory = serviceFactory;
        this.checkIdFilter = checkIdFilter;
        ProxyIdentifier.setProxied(argumentsFinder.firstBoolOrDefault(Keys.PROXIED_SERVICES));
    }

    @Override
    public ApplicationResult call() {

        var executeResult = doInput();

        if (!executeResult.hasErrors())
            executeResult = doOutput();

        resetAll();
        return executeResult;
    }

    private ApplicationResult doInput() {

        var executeResultBuilder = new ApplicationResult.Builder();

        try {
            var inputType = argumentsFinder.firstStringOrThrow(Keys.INPUT);

            var inputCallableEntry = selectCallable(inputType);
            var future = Executors.newSingleThreadExecutor().submit(inputCallableEntry.getValue());

            futureResult(inputCallableEntry.getKey(), future, executeResultBuilder);

        } catch (ArgumentNotExistException | ArgumentNotSupportValueException e) {
            logger.error(e.getMessage());
            executeResultBuilder.addError(e, e.getMessage());
        }

        return executeResultBuilder.build();
    }

    private ApplicationResult doOutput() {

        var executeResultBuilder = new ApplicationResult.Builder();

        var executor = Executors.newCachedThreadPool();
        var futuresMap = new HashMap<String, Future<Void>>();

        if (argumentsFinder.firstBoolOrDefault(Keys.SERIALIZE)) {
            futuresMap.put(Keys.SERIALIZE, executor.submit(new SerializeToFile(argumentsFinder, serviceFactory, checks)));
            logger.info(startMap.get(Keys.SERIALIZE));
        }

        if (argumentsFinder.firstBoolOrDefault(Keys.PRINT)) {
            futuresMap.put(Keys.PRINT, executor.submit(new PrintToFile(argumentsFinder, serviceFactory, checks)));
            logger.info(startMap.get(Keys.PRINT));
        }

        if (argumentsFinder.firstBoolOrDefault(Keys.GENERATE_SERIALIZE)) {
            futuresMap.put(Keys.GENERATE_SERIALIZE, executor.submit(new SerializeToGenerateFile(argumentsFinder, serviceFactory, checks)));
            logger.info(startMap.get(Keys.GENERATE_SERIALIZE));
        }

        for (var entry : futuresMap.entrySet())
            futureResult(entry.getKey(), entry.getValue(), executeResultBuilder);

        return executeResultBuilder.build();
    }

    private Map.Entry<String, Callable<Void>> selectCallable(String inputType) throws ArgumentNotSupportValueException {
        return switch (inputType) {
            case Inputs.DESERIALIZE_GENERATE -> new AbstractMap.SimpleEntry<>(
                    Inputs.DESERIALIZE_GENERATE,
                    new DeserializeFromGenerateFile(argumentsFinder, checkIdFilter, serviceFactory, checks));
            case Inputs.DESERIALIZE -> new AbstractMap.SimpleEntry<>(
                    Inputs.DESERIALIZE,
                    new DeserializeFromFile(argumentsFinder, checkIdFilter, serviceFactory, checks));
            case Inputs.DATABASE -> new AbstractMap.SimpleEntry<>(
                    Inputs.DATABASE,
                    new LoadFromDatabase(argumentsFinder, checkIdFilter, serviceFactory, checks));
            default -> throw new ArgumentNotSupportValueException(Keys.INPUT, inputType);
        };
    }

    private void futureResult(String futureKey, Future<Void> future, ApplicationResult.Builder executeResultBuilder) {
        try {
            future.get();
        } catch (InterruptedException | ExecutionException e) {
            logger.error(errorsMap.get(futureKey));
            logger.error(e.getMessage());
            executeResultBuilder.addError(e, errorsMap.get(futureKey));
        }
    }

    private void resetAll() {
        argumentsFinder.clearArguments();
        checks.clear();
    }

    public ArgumentsFinder getArgumentsFinder() {
        return argumentsFinder;
    }
}
