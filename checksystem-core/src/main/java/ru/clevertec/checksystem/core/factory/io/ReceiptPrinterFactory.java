package ru.clevertec.checksystem.core.factory.io;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import ru.clevertec.checksystem.core.entity.receipt.Receipt;
import ru.clevertec.checksystem.core.exception.ArgumentNotSupportedException;
import ru.clevertec.checksystem.core.io.print.ReceiptPrinter;
import ru.clevertec.checksystem.core.io.print.layout.HtmlReceiptLayout;
import ru.clevertec.checksystem.core.io.print.layout.PdfReceiptLayout;
import ru.clevertec.checksystem.core.io.print.layout.TextReceiptLayout;
import ru.clevertec.checksystem.core.util.ThrowUtils;

import java.util.Collection;

import static ru.clevertec.checksystem.core.Constants.Format;

@Component
public final class ReceiptPrinterFactory {

    private final ApplicationContext applicationContext;

    @Autowired
    private ReceiptPrinterFactory(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public ReceiptPrinter instance(String format) {
        return instance(format, null);
    }

    public ReceiptPrinter instance(String format, Collection<Receipt> receipts) {

        ThrowUtils.Argument.nullOrBlank("format", format);

        var receiptPrinter = applicationContext.getBean(ReceiptPrinter.class);
        receiptPrinter.setReceipts(receipts);

        switch (format) {
            case Format.TEXT -> receiptPrinter.setLayout(new TextReceiptLayout());
            case Format.PDF -> receiptPrinter.setLayout(new PdfReceiptLayout());
            case Format.HTML -> receiptPrinter.setLayout(new HtmlReceiptLayout());
            default -> throw new ArgumentNotSupportedException("format");
        }

        return receiptPrinter;
    }
}
