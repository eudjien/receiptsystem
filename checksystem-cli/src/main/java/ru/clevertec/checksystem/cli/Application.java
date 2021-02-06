package ru.clevertec.checksystem.cli;

import ru.clevertec.checksystem.cli.argument.ArgumentsFinder;
import ru.clevertec.checksystem.core.ProxyIdentifier;
import ru.clevertec.checksystem.core.common.functional.Action;
import ru.clevertec.checksystem.core.common.service.IGenerateCheckService;
import ru.clevertec.checksystem.core.common.service.IIoCheckService;
import ru.clevertec.checksystem.core.common.service.IPrintingCheckService;
import ru.clevertec.checksystem.core.data.DataSeed;
import ru.clevertec.checksystem.core.entity.check.Check;
import ru.clevertec.checksystem.core.exception.ArgumentUnsupportedException;
import ru.clevertec.checksystem.core.factory.service.ServiceFactory;
import ru.clevertec.checksystem.core.service.GenerateCheckService;
import ru.clevertec.checksystem.core.service.IoCheckService;
import ru.clevertec.checksystem.core.service.PrintingCheckService;
import ru.clevertec.custom.list.SinglyLinkedList;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.stream.Collectors;

public final class Application {

    private static ArgumentsFinder finder;

    public void start(String[] args) throws InterruptedException {

        finder = new ArgumentsFinder(args);

        ProxyIdentifier.setProxied(finder.findFirstBoolOrDefault(Constants.Keys.PROXIED_SERVICES));

        var checks = new SinglyLinkedList<Check>();

        var mode = finder.findFirstStringOrThrow(Constants.Keys.MODE);

        switch (mode) {
            case Constants.Mode.GENERATE -> deserializeGenerateFile(checks);
            case Constants.Mode.FILE_DESERIALIZE -> deserializeFile(checks);
            case Constants.Mode.PRE_DEFINED -> checks.addAll(applyFilterIfExist(DataSeed.checks()));
        }

        var threads = new SinglyLinkedList<Thread>();

        if (finder.findFirstBoolOrDefault(Constants.Keys.FILE_SERIALIZE))
            threads.add(createThread(() -> serializeToFile(checks), Constants.Task.Names.SERIALIZE));

        if (finder.findFirstBoolOrDefault(Constants.Keys.FILE_PRINT))
            threads.add(createThread(() -> printToFile(checks), Constants.Task.Names.PRINT));

        if (finder.findFirstBoolOrDefault(Constants.Keys.FILE_GENERATE_SERIALIZE))
            threads.add(createThread(() ->
                    serializeToGenerateFile(checks), Constants.Task.Names.SERIALIZE_GENERATE));

        if (!threads.isEmpty())
            startAndJoinThreads(threads);
    }

    private void deserializeGenerateFile(Collection<Check> checkCollection) {
        var format = finder.findFirstStringOrDefault(
                Constants.Keys.FILE_DESERIALIZE_GENERATE_FORMAT, Constants.Format.IO.JSON);
        var path = finder.findFirstStringOrThrow(Constants.Keys.FILE_DESERIALIZE_GENERATE_PATH);
        try {
            IGenerateCheckService generateCheckService = ServiceFactory.instance(GenerateCheckService.class);
            var checkList = generateCheckService.fromGenerated(new File(path), format);
            checkCollection.addAll(applyFilterIfExist(checkList));
        } catch (IOException
                | InstantiationException
                | InvocationTargetException
                | NoSuchMethodException
                | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private void serializeToGenerateFile(Collection<Check> checkCollection) {
        var format = finder.findFirstStringOrThrow(Constants.Keys.FILE_GENERATE_SERIALIZE_FORMAT);
        var path = finder.findFirstStringOrThrow(Constants.Keys.FILE_GENERATE_SERIALIZE_PATH);
        try {
            IGenerateCheckService generateCheckService = ServiceFactory.instance(GenerateCheckService.class);
            generateCheckService.toGenerated(checkCollection, new File(path), format);
        } catch (IOException
                | InstantiationException
                | InvocationTargetException
                | NoSuchMethodException
                | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private void deserializeFile(Collection<Check> checkCollection) {
        var format = finder.findFirstStringOrThrow(Constants.Keys.FILE_DESERIALIZE_FORMAT);
        var path = finder.findFirstStringOrThrow(Constants.Keys.FILE_DESERIALIZE_PATH);
        try {
            IIoCheckService ioService = ServiceFactory.instance(IoCheckService.class);
            var deserializedChecks = ioService.deserialize(new File(path), format);
            checkCollection.addAll(applyFilterIfExist(deserializedChecks));
        } catch (IOException
                | InstantiationException
                | InvocationTargetException
                | NoSuchMethodException
                | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private void serializeToFile(Collection<Check> checkCollection) {
        var format = finder.findFirstStringOrThrow(Constants.Keys.FILE_SERIALIZE_FORMAT);
        var path = finder.findFirstStringOrThrow(Constants.Keys.FILE_SERIALIZE_PATH);
        try {
            IIoCheckService ioService = ServiceFactory.instance(IoCheckService.class);
            ioService.serialize(checkCollection, new File(path), format);
        } catch (IOException
                | InstantiationException
                | InvocationTargetException
                | NoSuchMethodException
                | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private void printToFile(Collection<Check> checkCollection) {
        var format = finder.findFirstStringOrThrow(Constants.Keys.FILE_PRINT_FORMAT);
        var path = finder.findFirstStringOrThrow(Constants.Keys.FILE_PRINT_PATH);
        try {
            IPrintingCheckService printingService =
                    ServiceFactory.instance(PrintingCheckService.class);
            switch (format.toLowerCase()) {
                case Constants.Format.Print.TEXT -> printingService.printToText(checkCollection, new File(path));
                case Constants.Format.Print.HTML -> printingService.printToHtml(checkCollection, new File(path));
                case Constants.Format.Print.PDF -> {
                    if (finder.findFirstBoolOrDefault(Constants.Keys.FILE_PRINT_PDF_TEMPLATE)) {
                        var pdfTemplatePath =
                                finder.findFirstStringOrDefault(Constants.Keys.FILE_PRINT_PDF_TEMPLATE_PATH);
                        printingService.printWithTemplateToPdf(checkCollection, new File(path),
                                new File(pdfTemplatePath),
                                finder.findFirstIntOrDefault(Constants.Keys.FILE_PRINT_PDF_TEMPLATE_OFFSET));
                    } else {
                        printingService.printToPdf(checkCollection, new File(path));
                    }
                }
                default -> throw new ArgumentUnsupportedException(Constants.Keys.FILE_PRINT_FORMAT, format);
            }

        } catch (IOException
                | InstantiationException
                | InvocationTargetException
                | NoSuchMethodException
                | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private Collection<Check> applyFilterIfExist(Collection<Check> checkCollection) {
        var value = finder.findFirstStringOrDefault(Constants.Keys.INPUT_FILTER_ID);
        if (value != null) {
            var idList = new SinglyLinkedList<Integer>();
            var values = value.split(",");
            for (String s : values) {
                try {
                    var intValue = Integer.parseInt(s);
                    idList.add(intValue);
                } catch (Exception ignored) {
                }
            }
            return checkCollection.stream()
                    .filter(check -> idList.contains(check.getId()))
                    .collect(Collectors.toCollection(SinglyLinkedList<Check>::new));
        }
        return checkCollection;
    }

    private void startAndJoinThreads(Collection<Thread> threadCollection) throws InterruptedException {
        for (var thread : threadCollection)
            thread.start();
        for (var thread : threadCollection)
            thread.join();
    }

    private Thread createThread(Action action, String taskName) {
        return new Thread(() -> {
            try {
                action.action();
                System.out.printf("[%s] completed.%n", taskName);
            } catch (Exception e) {
                System.out.printf("[%s] %s%n", taskName, e.getMessage());
                throw e;
            }
        });
    }
}
