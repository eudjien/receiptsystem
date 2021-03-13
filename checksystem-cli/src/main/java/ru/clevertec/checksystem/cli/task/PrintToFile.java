package ru.clevertec.checksystem.cli.task;

import ru.clevertec.checksystem.cli.Constants;
import ru.clevertec.checksystem.cli.argument.ArgumentsFinder;
import ru.clevertec.checksystem.cli.exception.ArgumentNotSupportValueException;
import ru.clevertec.checksystem.core.common.service.IPrintingReceiptService;
import ru.clevertec.checksystem.core.entity.receipt.Receipt;
import ru.clevertec.checksystem.core.factory.service.ServiceFactory;
import ru.clevertec.checksystem.core.service.PrintingReceiptService;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.concurrent.Callable;

import static ru.clevertec.checksystem.cli.Constants.Keys;
import static ru.clevertec.checksystem.core.Constants.Formats;

public class PrintToFile implements Callable<Void> {

    private final ArgumentsFinder argumentsFinder;
    private final ServiceFactory serviceFactory;
    private final Collection<Receipt> sourceReceipts;

    public PrintToFile(
            ArgumentsFinder argumentsFinder, ServiceFactory serviceFactory, Collection<Receipt> sourceReceipts) {

        this.argumentsFinder = argumentsFinder;
        this.serviceFactory = serviceFactory;
        this.sourceReceipts = sourceReceipts;
    }

    @Override
    public Void call() throws Exception {

        var path = argumentsFinder.firstStringOrThrow(Keys.PRINT_PATH);
        var format = argumentsFinder.firstStringOrThrow(Keys.PRINT_FORMAT);

        printToFile(path, format);

        return null;
    }

    private void printToFile(String path, String format) throws IOException, ArgumentNotSupportValueException {

        IPrintingReceiptService printingReceiptService = serviceFactory.instance(PrintingReceiptService.class);

        switch (format) {
            case Formats.TEXT -> printingReceiptService.printToText(sourceReceipts, new File(path));
            case Formats.HTML -> printingReceiptService.printToHtml(sourceReceipts, new File(path));
            case Formats.PDF -> {
                if (argumentsFinder.firstBoolOrDefault(Keys.PRINT_PDF_TEMPLATE)) {
                    var pdfTemplatePath = argumentsFinder.firstStringOrDefault(Keys.PRINT_PDF_TEMPLATE_PATH);
                    printingReceiptService.printWithTemplateToPdf(sourceReceipts, new File(path),
                            new File(pdfTemplatePath), argumentsFinder.firstIntOrDefault(Keys.PRINT_PDF_TEMPLATE_OFFSET));
                } else {
                    printingReceiptService.printToPdf(sourceReceipts, new File(path));
                }
            }
            default -> throw new ArgumentNotSupportValueException(Constants.Keys.PRINT_FORMAT, format);
        }
    }
}
