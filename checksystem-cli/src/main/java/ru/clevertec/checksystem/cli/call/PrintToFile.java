package ru.clevertec.checksystem.cli.call;

import ru.clevertec.checksystem.cli.Constants;
import ru.clevertec.checksystem.cli.argument.ArgumentsFinder;
import ru.clevertec.checksystem.core.common.service.IPrintingCheckService;
import ru.clevertec.checksystem.core.entity.check.Check;
import ru.clevertec.checksystem.core.exception.ArgumentUnsupportedException;
import ru.clevertec.checksystem.core.factory.service.ServiceFactory;
import ru.clevertec.checksystem.core.service.PrintingCheckService;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.concurrent.Callable;

import static ru.clevertec.checksystem.core.Constants.Format;

public class PrintToFile implements Callable<CallResult> {

    private final ArgumentsFinder argumentsFinder;
    private final ServiceFactory serviceFactory;
    private final Collection<Check> sourceChecks;

    public PrintToFile(
            ArgumentsFinder argumentsFinder, ServiceFactory serviceFactory, Collection<Check> sourceChecks) {

        this.argumentsFinder = argumentsFinder;
        this.serviceFactory = serviceFactory;
        this.sourceChecks = sourceChecks;
    }

    @Override
    public CallResult call() {
        try {
            printToFile(argumentsFinder, serviceFactory, sourceChecks);
        } catch (Exception e) {
            return CallResult.fail(e, "Something went wrong while printing to a file.");
        }
        return CallResult.success("Printing to a file is complete.");
    }

    private static void printToFile(
            ArgumentsFinder finder, ServiceFactory serviceFactory, Collection<Check> checks) throws IOException {

        var format = finder.firstStringOrThrow(Constants.Keys.PRINT_FORMAT);
        var path = finder.firstStringOrThrow(Constants.Keys.PRINT_PATH);
        IPrintingCheckService printingCheckService = serviceFactory.instance(PrintingCheckService.class);

        switch (format.toLowerCase()) {
            case Format.Print.TEXT -> printingCheckService.printToText(checks, new File(path));
            case Format.Print.HTML -> printingCheckService.printToHtml(checks, new File(path));
            case Format.Print.PDF -> {
                if (finder.firstBoolOrDefault(Constants.Keys.PRINT_PDF_TEMPLATE)) {
                    var pdfTemplatePath =
                            finder.firstStringOrDefault(Constants.Keys.PRINT_PDF_TEMPLATE_PATH);
                    printingCheckService.printWithTemplateToPdf(checks, new File(path),
                            new File(pdfTemplatePath),
                            finder.firstIntOrDefault(Constants.Keys.PRINT_PDF_TEMPLATE_OFFSET));
                } else {
                    printingCheckService.printToPdf(checks, new File(path));
                }
            }
            default -> throw new ArgumentUnsupportedException(Constants.Keys.PRINT_FORMAT, format);
        }
    }
}
