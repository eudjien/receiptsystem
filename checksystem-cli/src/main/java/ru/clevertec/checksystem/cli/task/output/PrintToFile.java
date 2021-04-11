package ru.clevertec.checksystem.cli.task.output;

import ru.clevertec.checksystem.cli.Constants;
import ru.clevertec.checksystem.cli.argument.ArgumentFinder;
import ru.clevertec.checksystem.core.entity.receipt.Receipt;
import ru.clevertec.checksystem.core.io.format.PrintFormat;
import ru.clevertec.checksystem.core.service.common.IIoReceiptService;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.concurrent.Callable;

import static ru.clevertec.checksystem.cli.Constants.Keys;

public class PrintToFile implements Callable<Void> {

    private final ArgumentFinder argumentFinder;
    private final IIoReceiptService receiptService;
    private final Collection<Receipt> receipts;

    public PrintToFile(
            ArgumentFinder argumentFinder, IIoReceiptService receiptService, Collection<Receipt> receipts) {
        this.argumentFinder = argumentFinder;
        this.receiptService = receiptService;
        this.receipts = receipts;
    }

    @Override
    public Void call() throws Exception {

        var path = argumentFinder.firstStringOrThrow(Keys.PRINT_PATH);
        var format = argumentFinder.firstStringOrThrow(Keys.PRINT_FORMAT);

        printToFile(path, format);

        return null;
    }

    private void printToFile(String path, String format) throws IOException {
        try {
            var printFormat = PrintFormat.from(format);
            if (printFormat == PrintFormat.PDF && argumentFinder.firstBoolOrDefault(Keys.PRINT_PDF_TEMPLATE)) {
                var pdfTemplatePath = argumentFinder.firstStringOrDefault(Keys.PRINT_PDF_TEMPLATE_PATH);
                receiptService.printWithTemplateToPdf(receipts, new File(path),
                        new File(pdfTemplatePath), argumentFinder.firstIntOrDefault(Keys.PRINT_PDF_TEMPLATE_OFFSET));
            } else {
                receiptService.print(receipts, new File(path), printFormat);
            }
        } catch (IllegalArgumentException ex1) {
            throw new IllegalArgumentException(String.format("Argument '%s' does not support '%s' value", Constants.Keys.PRINT_FORMAT, format));
        } catch (Exception e) {
            throw e;
        }
    }
}
