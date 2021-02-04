package ru.clevertec.checksystem.cli;

import ru.clevertec.checksystem.cli.argument.ArgumentsFinder;
import ru.clevertec.checksystem.core.ProxyIdentifier;
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
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.stream.Collectors;

public final class Application {

    private static ArgumentsFinder finder;

    public void start(String[] args) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, IOException {

        finder = new ArgumentsFinder(args);

        ProxyIdentifier.setProxied(finder.findFirstBoolOrDefault(Constants.Keys.PROXIED_SERVICES));

        var checks = new SinglyLinkedList<Check>();

        var mode = finder.findFirstStringOrThrow(Constants.Keys.MODE);

        switch (mode) {
            case Constants.Mode.GENERATE -> generatedDeserialize(checks);
            case Constants.Mode.FILE_DESERIALIZE -> fileDeserialize(checks);
            case Constants.Mode.PRE_DEFINED -> checks.addAll(applyFilterIfExist(DataSeed.checks()));
        }

        if (finder.findFirstBoolOrDefault(Constants.Keys.FILE_SERIALIZE)) {
            fileSerialize(checks);
        }

        if (finder.findFirstBoolOrDefault(Constants.Keys.FILE_PRINT)) {
            filePrint(checks);
        }

        if (finder.findFirstBoolOrDefault(Constants.Keys.GENERATE_FILE_SERIALIZE)) {
            generatedSerialize(checks);
        }
    }

    private void generatedDeserialize(Collection<Check> checkCollection) throws IOException, ArgumentUnsupportedException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {

        IGenerateCheckService generateCheckService =
                ServiceFactory.instance(GenerateCheckService.class);

        var source = finder.findFirstStringOrThrow(Constants.Keys.GENERATE_DESERIALIZE_SOURCE);

        var format = finder.findFirstStringOrDefault(Constants.Keys.GENERATE_DESERIALIZE_FORMAT,
                Constants.Format.IO.JSON);

        var dataOrPath = finder.findFirstStringOrThrow(Constants.Keys.GENERATE_DESERIALIZE_DATA);

        Collection<Check> checkList = switch (source) {
            case Constants.Source.FILE -> generateCheckService.fromGenerated(new File(dataOrPath), format);
            case Constants.Source.DATA -> generateCheckService.fromGenerated(dataOrPath.getBytes(StandardCharsets.UTF_8), format);
            default -> throw new ArgumentUnsupportedException(Constants.Keys.GENERATE_DESERIALIZE_SOURCE, source);
        };

        checkCollection.addAll(applyFilterIfExist(checkList));
    }

    private void generatedSerialize(Collection<Check> checkCollection) throws IOException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {

        IGenerateCheckService generateCheckService = ServiceFactory.instance(GenerateCheckService.class);

        var format = finder.findFirstStringOrThrow(Constants.Keys.GENERATE_FILE_SERIALIZE_FORMAT);
        var destinationPath = finder.findFirstStringOrThrow(Constants.Keys.GENERATE_FILE_SERIALIZE_PATH);

        generateCheckService.toGenerated(checkCollection, new File(destinationPath), format);
    }

    private void fileDeserialize(Collection<Check> checkCollection) throws IOException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {

        var format = finder.findFirstStringOrThrow(Constants.Keys.FILE_DESERIALIZE_FORMAT);
        var sourcePath = finder.findFirstStringOrThrow(Constants.Keys.FILE_DESERIALIZE_PATH);
        IIoCheckService ioService = ServiceFactory.instance(IoCheckService.class);
        var deserializedChecks = ioService.deserialize(new File(sourcePath), format);
        checkCollection.addAll(applyFilterIfExist(deserializedChecks));
    }

    private void fileSerialize(Collection<Check> checkCollection) throws IOException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        var format = finder.findFirstStringOrThrow(Constants.Keys.FILE_SERIALIZE_FORMAT);
        var destinationPath = finder.findFirstStringOrThrow(Constants.Keys.FILE_SERIALIZE_PATH);
        IIoCheckService ioService = ServiceFactory.instance(IoCheckService.class);
        ioService.serialize(checkCollection, new File(destinationPath), format);
    }

    private void filePrint(Collection<Check> checkCollection) throws ArgumentUnsupportedException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException, IOException {

        var printFormat = finder.findFirstStringOrThrow(Constants.Keys.FILE_PRINT_FORMAT);
        var destinationPath = finder.findFirstStringOrThrow(Constants.Keys.FILE_PRINT_PATH);
        IPrintingCheckService printingService =
                ServiceFactory.instance(PrintingCheckService.class);

        switch (printFormat.toLowerCase()) {
            case Constants.Format.Print.TEXT -> printingService.printToText(checkCollection, new File(destinationPath));
            case Constants.Format.Print.HTML -> printingService.printToHtml(checkCollection, new File(destinationPath));
            case Constants.Format.Print.PDF -> {
                if (finder.findFirstBoolOrDefault(Constants.Keys.FILE_PRINT_PDF_TEMPLATE)) {
                    var pdfTemplatePath =
                            finder.findFirstStringOrDefault(Constants.Keys.FILE_PRINT_PDF_TEMPLATE_PATH);
                    printingService.printWithTemplateToPdf(checkCollection, new File(destinationPath),
                            new File(pdfTemplatePath),
                            finder.findFirstIntOrDefault(Constants.Keys.FILE_PRINT_PDF_TEMPLATE_OFFSET));

                } else {
                    printingService.printToPdf(checkCollection, new File(destinationPath));
                }
            }
            default -> throw new ArgumentUnsupportedException(Constants.Keys.FILE_PRINT_FORMAT, printFormat);
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
}
