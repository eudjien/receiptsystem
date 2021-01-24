package ru.clevertec.checksystem.cli;

import ru.clevertec.checksystem.cli.argument.ArgumentsFinder;
import ru.clevertec.checksystem.core.DataSeed;
import ru.clevertec.checksystem.core.check.Check;
import ru.clevertec.checksystem.core.factory.ServiceFactory;
import ru.clevertec.checksystem.core.service.*;
import ru.clevertec.normalino.list.NormalinoList;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.stream.Collectors;

public class Main {

    private static ArgumentsFinder finder;
    private static boolean isServicesUseProxy;

    public static void main(String[] args) throws Exception {

        finder = new ArgumentsFinder(args);

        isServicesUseProxy = finder.findFirstBoolOrDefault(Constants.Keys.PROXIED_SERVICES);

        var checks = new NormalinoList<Check>();

        var mode = finder.findFirstStringOrThrow(Constants.Keys.MODE);

        switch (mode) {
            case Constants.Mode.GENERATE -> generate(checks);
            case Constants.Mode.FILE_DESERIALIZE -> fileDeserialize(checks);
            case Constants.Mode.PRE_DEFINED -> checks.addAll(applyFilterIfExist(DataSeed.checks()));
        }

        if (finder.findFirstBoolOrDefault(Constants.Keys.FILE_SERIALIZE)) {
            fileSerialize(checks);
        }

        if (finder.findFirstBoolOrDefault(Constants.Keys.FILE_PRINT)) {
            filePrint(checks);
        }
    }

    static void generate(Collection<Check> checks) throws Exception {

        IGenerateCheckService generateCheckService =
                ServiceFactory.instance(GenerateCheckService.class, isServicesUseProxy);

        var source = finder.findFirstStringOrThrow(Constants.Keys.GENERATE_DESERIALIZE_SOURCE);

        var format = finder.findFirstStringOrDefault(Constants.Keys.GENERATE_DESERIALIZE_FORMAT,
                Constants.Format.IO.JSON);

        var dataOrPath = finder.findFirstStringOrThrow(Constants.Keys.GENERATE_DESERIALIZE_DATA);

        Collection<Check> checkList = switch (source) {
            case Constants.Source.FILE -> generateCheckService.fromGenerated(new File(dataOrPath), format);
            case Constants.Source.DATA -> generateCheckService.fromGenerated(dataOrPath.getBytes(StandardCharsets.UTF_8), format);
            default -> throw new IllegalArgumentException(
                    "Incorrect value for '" + Constants.Keys.GENERATE_DESERIALIZE_SOURCE + "' argument");
        };

        checks.addAll(applyFilterIfExist(checkList));
    }

    static void fileDeserialize(NormalinoList<Check> checks) throws Exception {

        var format = finder.findFirstStringOrThrow(Constants.Keys.FILE_DESERIALIZE_FORMAT);
        var srcPath = finder.findFirstStringOrThrow(Constants.Keys.FILE_DESERIALIZE_PATH);
        IIoCheckService ioService = ServiceFactory.instance(IoCheckService.class, isServicesUseProxy);
        var deserializedChecks = ioService.deserializeFromFile(srcPath, format);
        checks.addAll(applyFilterIfExist(deserializedChecks));
    }

    static void fileSerialize(NormalinoList<Check> checks) throws Exception {

        var format = finder.findFirstStringOrThrow(Constants.Keys.FILE_SERIALIZE_FORMAT);
        var destPath = finder.findFirstStringOrThrow(Constants.Keys.FILE_SERIALIZE_PATH);
        IIoCheckService ioService = ServiceFactory.instance(IoCheckService.class, isServicesUseProxy);
        ioService.serializeToFile(checks, new File(destPath), format);
    }

    static void filePrint(NormalinoList<Check> checks) throws Exception {

        var printFormat = finder.findFirstStringOrThrow(Constants.Keys.FILE_PRINT_FORMAT);
        var destPath = finder.findFirstStringOrThrow(Constants.Keys.FILE_PRINT_PATH);
        IPrintingCheckService printingService = ServiceFactory.instance(PrintingCheckService.class, isServicesUseProxy);

        switch (printFormat.toLowerCase()) {
            case Constants.Format.Print.TEXT -> printingService.printToTextFile(checks, destPath);
            case Constants.Format.Print.HTML -> printingService.printToHtmlFile(checks, destPath);
            case Constants.Format.Print.PDF -> {
                if (finder.findFirstBoolOrDefault(Constants.Keys.FILE_PRINT_PDF_TEMPLATE)) {
                    printingService.printToPdfFile(checks, destPath,
                            finder.findFirstStringOrDefault(Constants.Keys.FILE_PRINT_PDF_TEMPLATE_PATH),
                            finder.findFirstIntOrDefault(Constants.Keys.FILE_PRINT_PDF_TEMPLATE_OFFSET));
                } else {
                    printingService.printToPdfFile(checks, destPath);
                }
            }
            default -> throw new Exception("Print format '" + printFormat + " 'not supported");
        }
    }

    static Collection<Check> applyFilterIfExist(Collection<Check> checks) {
        var value = finder.findFirstStringOrDefault(Constants.Keys.INPUT_FILTER_ID);
        if (value != null) {
            var idList = new NormalinoList<Integer>();
            var values = value.split(",");
            for (String s : values) {
                try {
                    var intValue = Integer.parseInt(s);
                    idList.add(intValue);
                } catch (Exception ignored) {
                }
            }
            return checks.stream()
                    .filter(check -> idList.contains(check.getId()))
                    .collect(Collectors.toCollection(NormalinoList<Check>::new));
        }
        return checks;
    }
}
