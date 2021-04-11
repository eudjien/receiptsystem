package ru.clevertec.checksystem.core.io.factory;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.clevertec.checksystem.core.entity.receipt.Receipt;
import ru.clevertec.checksystem.core.io.format.PrintFormat;
import ru.clevertec.checksystem.core.io.print.IReceiptPrinter;
import ru.clevertec.checksystem.core.io.print.layout.HtmlReceiptLayout;
import ru.clevertec.checksystem.core.io.print.layout.PdfReceiptLayout;
import ru.clevertec.checksystem.core.io.print.layout.TextReceiptLayout;
import ru.clevertec.checksystem.core.service.common.IReceiptService;
import ru.clevertec.checksystem.core.util.ThrowUtils;

import java.util.Collection;

@Component
@RequiredArgsConstructor
public final class ReceiptPrinterFactory {

    private final IReceiptPrinter receiptPrinter;
    private final IReceiptService receiptService;

    public IReceiptPrinter instance(PrintFormat printFormat) {
        return instance(printFormat, null);
    }

    public IReceiptPrinter instance(PrintFormat printFormat, Collection<Receipt> receipts) {

        ThrowUtils.Argument.nullValue("printFormat", printFormat);

        receiptPrinter.setReceipts(receipts);

        switch (printFormat) {
            case TEXT -> receiptPrinter.setLayout(new TextReceiptLayout(receiptService));
            case PDF -> receiptPrinter.setLayout(new PdfReceiptLayout(receiptService));
            case HTML -> receiptPrinter.setLayout(new HtmlReceiptLayout(receiptService));
        }

        return receiptPrinter;
    }
}
