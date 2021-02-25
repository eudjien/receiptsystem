package ru.clevertec.checksystem.cli.task;

import ru.clevertec.checksystem.cli.Constants;
import ru.clevertec.checksystem.cli.argument.ArgumentsFinder;
import ru.clevertec.checksystem.cli.exception.ArgumentNotSupportValueException;
import ru.clevertec.checksystem.core.common.service.IPrintingCheckService;
import ru.clevertec.checksystem.core.entity.check.Check;
import ru.clevertec.checksystem.core.factory.service.ServiceFactory;
import ru.clevertec.checksystem.core.service.PrintingCheckService;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.concurrent.Callable;

import static ru.clevertec.checksystem.cli.Constants.Keys;
import static ru.clevertec.checksystem.core.Constants.Format;

public class PrintToFile implements Callable<Void> {

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
    public Void call() throws Exception {

        var path = argumentsFinder.firstStringOrThrow(Keys.PRINT_PATH);
        var format = argumentsFinder.firstStringOrThrow(Keys.PRINT_FORMAT);

        printToFile(path, format);

        return null;
    }

    private void printToFile(String path, String format) throws IOException, ArgumentNotSupportValueException {

        IPrintingCheckService printingCheckService = serviceFactory.instance(PrintingCheckService.class);

        switch (format) {
            case Format.Print.TEXT -> printingCheckService.printToText(sourceChecks, new File(path));
            case Format.Print.HTML -> printingCheckService.printToHtml(sourceChecks, new File(path));
            case Format.Print.PDF -> {
                if (argumentsFinder.firstBoolOrDefault(Keys.PRINT_PDF_TEMPLATE)) {
                    var pdfTemplatePath = argumentsFinder.firstStringOrDefault(Keys.PRINT_PDF_TEMPLATE_PATH);
                    printingCheckService.printWithTemplateToPdf(sourceChecks, new File(path),
                            new File(pdfTemplatePath), argumentsFinder.firstIntOrDefault(Keys.PRINT_PDF_TEMPLATE_OFFSET));
                } else {
                    printingCheckService.printToPdf(sourceChecks, new File(path));
                }
            }
            default -> throw new ArgumentNotSupportValueException(Constants.Keys.PRINT_FORMAT, format);
        }
    }
}
