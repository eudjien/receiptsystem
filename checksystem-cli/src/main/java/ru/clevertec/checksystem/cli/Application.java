package ru.clevertec.checksystem.cli;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.clevertec.checksystem.cli.argument.ArgumentsFinder;
import ru.clevertec.checksystem.cli.argument.CheckIdArgumentFilter;
import ru.clevertec.checksystem.cli.call.*;
import ru.clevertec.checksystem.core.ProxyIdentifier;
import ru.clevertec.checksystem.core.entity.check.Check;
import ru.clevertec.checksystem.core.factory.service.ServiceFactory;
import ru.clevertec.custom.list.SinglyLinkedList;
import ru.clevertec.custom.list.SynchronizedSinglyLinkedList;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static ru.clevertec.checksystem.cli.Constants.Inputs;
import static ru.clevertec.checksystem.cli.Constants.Keys;

@Component
public final class Application {

    private final ServiceFactory serviceFactory;
    private final CheckIdArgumentFilter checkIdArgumentFilter;

    private final List<Check> checks = new SynchronizedSinglyLinkedList<>();

    private ArgumentsFinder argumentsFinder;

    @Autowired
    public Application(ServiceFactory serviceFactory, CheckIdArgumentFilter checkIdArgumentFilter) {
        this.serviceFactory = serviceFactory;
        this.checkIdArgumentFilter = checkIdArgumentFilter;
    }

    public void start(ArgumentsFinder argumentsFinder) throws Exception {
        this.argumentsFinder = argumentsFinder;
        start();
    }

    private void start() throws Exception {

        ProxyIdentifier.setProxied(argumentsFinder.firstBoolOrDefault(Keys.PROXIED_SERVICES));

        doInput();
        doOutput();
    }

    private void doInput() throws Exception {

        var input = argumentsFinder.firstStringOrThrow(Keys.INPUT);

        var loadChecksCallable = getChecksInputSource(input);
        var loadChecksResult = Executors.newSingleThreadExecutor().submit(loadChecksCallable).get();

        if (!loadChecksResult.isSuccess())
            throw loadChecksResult.getException();
    }

    private void doOutput() throws InterruptedException, ExecutionException {

        var executor = Executors.newCachedThreadPool();
        var futures = new SinglyLinkedList<Future<CallResult>>();

        if (argumentsFinder.firstBoolOrDefault(Keys.SERIALIZE)) {
            futures.add(executor.submit(new SerializeToFile(argumentsFinder, serviceFactory, checks)));
            System.out.println("Serializing to a file...");
        }

        if (argumentsFinder.firstBoolOrDefault(Keys.PRINT)) {
            futures.add(executor.submit(new PrintToFile(argumentsFinder, serviceFactory, checks)));
            System.out.println("Printing to a file...");
        }

        if (argumentsFinder.firstBoolOrDefault(Keys.GENERATE_SERIALIZE)) {
            futures.add(executor.submit(new SerializeToGenerateFile(argumentsFinder, serviceFactory, checks)));
            System.out.println("Serializing to a generate file...");
        }

        if (!futures.isEmpty()) {
            for (var future : futures) {
                var callResult = future.get();
                if (callResult.isSuccess())
                    System.out.println(callResult.getMessage());
                else
                    System.err.println(callResult.getMessage());
            }
        }
    }

    private Callable<CallResult> getChecksInputSource(String input) {
        return switch (input) {
            case Inputs.DESERIALIZE_GENERATE -> new DeserializeFromGenerateFile(
                    argumentsFinder, checkIdArgumentFilter, serviceFactory, checks);
            case Inputs.DESERIALIZE -> new DeserializeFromFile(
                    argumentsFinder, checkIdArgumentFilter, serviceFactory, checks);
            case Inputs.DATABASE -> new LoadFromDatabase(
                    argumentsFinder, checkIdArgumentFilter, serviceFactory, checks);
            default -> throw new IllegalArgumentException("Unsupported mode");
        };
    }
}
