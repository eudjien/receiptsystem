package ru.clevertec.checksystem.cli.application;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.clevertec.checksystem.cli.argument.ArgumentFinder;
import ru.clevertec.checksystem.cli.argument.ReceiptIdFilter;
import ru.clevertec.checksystem.cli.task.input.DeserializeFromFile;
import ru.clevertec.checksystem.cli.task.input.DeserializeFromGenerateFile;
import ru.clevertec.checksystem.cli.task.input.LoadFromDatabase;
import ru.clevertec.checksystem.cli.task.output.PrintToFile;
import ru.clevertec.checksystem.cli.task.output.SerializeToFile;
import ru.clevertec.checksystem.cli.task.output.SerializeToGenerateFile;
import ru.clevertec.checksystem.core.entity.receipt.Receipt;
import ru.clevertec.checksystem.core.exception.ArgumentNotSupportedException;
import ru.clevertec.checksystem.core.repository.ReceiptRepository;
import ru.clevertec.checksystem.core.service.common.IIoReceiptService;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

import static ru.clevertec.checksystem.cli.Constants.Inputs;
import static ru.clevertec.checksystem.cli.Constants.Keys;

@Component
public final class Application implements Callable<ApplicationResult> {

    private static final Logger logger = LogManager.getLogger("ConsumerLogger");

    private static final Map<String, String> startMap = new HashMap<>();
    private static final Map<String, String> errorsMap = new HashMap<>();

    private final IIoReceiptService ioReceiptService;

    private final ReceiptRepository receiptRepository;

    private final ArgumentFinder argumentFinder;
    private final ReceiptIdFilter receiptIdFilter;

    private final List<Receipt> receipts = new CopyOnWriteArrayList<>();

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
            IIoReceiptService ioReceiptService,
            ReceiptRepository receiptRepository,
            ArgumentFinder argumentFinder,
            ReceiptIdFilter receiptIdFilter) {
        this.ioReceiptService = ioReceiptService;
        this.receiptRepository = receiptRepository;
        this.argumentFinder = argumentFinder;
        this.receiptIdFilter = receiptIdFilter;
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
            var inputType = argumentFinder.firstStringOrThrow(Keys.INPUT);

            var inputCallableEntry = selectCallable(inputType);
            var future = Executors.newSingleThreadExecutor().submit(inputCallableEntry.getValue());

            futureResult(inputCallableEntry.getKey(), future, executeResultBuilder);

        } catch (Throwable e) {
            logger.error(e.getMessage());
            executeResultBuilder.addError(e, e.getMessage());
        }

        return executeResultBuilder.build();
    }

    private ApplicationResult doOutput() {

        var executeResultBuilder = new ApplicationResult.Builder();

        var executor = Executors.newCachedThreadPool();
        var futuresMap = new HashMap<String, Future<Void>>();

        if (argumentFinder.firstBoolOrDefault(Keys.SERIALIZE)) {
            futuresMap.put(Keys.SERIALIZE, executor.submit(new SerializeToFile(argumentFinder, ioReceiptService, receipts)));
            logger.info(startMap.get(Keys.SERIALIZE));
        }

        if (argumentFinder.firstBoolOrDefault(Keys.PRINT)) {
            futuresMap.put(Keys.PRINT, executor.submit(new PrintToFile(argumentFinder, ioReceiptService, receipts)));
            logger.info(startMap.get(Keys.PRINT));
        }

        if (argumentFinder.firstBoolOrDefault(Keys.GENERATE_SERIALIZE)) {
            futuresMap.put(Keys.GENERATE_SERIALIZE, executor.submit(new SerializeToGenerateFile(argumentFinder, ioReceiptService, receipts)));
            logger.info(startMap.get(Keys.GENERATE_SERIALIZE));
        }

        for (var entry : futuresMap.entrySet())
            futureResult(entry.getKey(), entry.getValue(), executeResultBuilder);

        return executeResultBuilder.build();
    }

    private Map.Entry<String, Callable<Void>> selectCallable(String inputType) {
        return switch (inputType) {
            case Inputs.DESERIALIZE_GENERATE -> new AbstractMap.SimpleEntry<>(
                    Inputs.DESERIALIZE_GENERATE,
                    new DeserializeFromGenerateFile(argumentFinder, receiptIdFilter, ioReceiptService, receipts));
            case Inputs.DESERIALIZE -> new AbstractMap.SimpleEntry<>(
                    Inputs.DESERIALIZE,
                    new DeserializeFromFile(argumentFinder, receiptIdFilter, ioReceiptService, receipts));
            case Inputs.DATABASE -> new AbstractMap.SimpleEntry<>(
                    Inputs.DATABASE,
                    new LoadFromDatabase(argumentFinder, receiptIdFilter, receiptRepository, receipts));
            default -> throw new ArgumentNotSupportedException(Keys.INPUT, inputType);
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
        argumentFinder.clearArguments();
        receipts.clear();
    }

    public ArgumentFinder getArgumentsFinder() {
        return argumentFinder;
    }
}
