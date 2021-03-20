package ru.clevertec.checksystem.core.io.print;

import org.springframework.stereotype.Component;
import ru.clevertec.checksystem.core.common.receipt.IReceiptAggregable;
import ru.clevertec.checksystem.core.entity.receipt.Receipt;
import ru.clevertec.checksystem.core.io.print.layout.IReceiptLayout;
import ru.clevertec.checksystem.core.util.FileUtils;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

@Component
public class ReceiptPrinter extends AbstractPrinter<Receipt> implements IReceiptAggregable {

    private final Collection<Receipt> receipts = new ArrayList<>();

    public ReceiptPrinter() {
    }

    public ReceiptPrinter(IReceiptLayout receiptLayout) {
        super(receiptLayout);
    }

    public ReceiptPrinter(Collection<Receipt> receipts, IReceiptLayout receiptLayout) {
        super(receiptLayout);
        setReceipts(receipts);
    }

    @Override
    public Collection<Receipt> getReceipts() {
        return receipts;
    }

    @Override
    public void setReceipts(Collection<Receipt> receipts) {
        this.receipts.clear();
        if (receipts != null)
            this.receipts.addAll(receipts);
    }

    @Override
    public void addReceipt(Receipt receipt) {
        receipts.add(receipt);
    }

    @Override
    public void addReceipts(Collection<Receipt> receipts) {
        this.receipts.addAll(receipts);
    }

    @Override
    public void removeReceipt(Receipt receipt) {
        this.receipts.remove(receipt);
    }

    @Override
    public void removeReceipts(Collection<Receipt> receipts) {
        this.receipts.removeAll(receipts);
    }

    @Override
    public void clearReceipts() {
        this.receipts.clear();
    }

    @Override
    public byte[] print() throws IOException {
        return getLayout().getAllLayoutData(getReceipts());
    }

    @Override
    public void print(OutputStream os) throws IOException {
        os.write(getLayout().getAllLayoutData(getReceipts()));
    }

    @Override
    public void print(File destinationFile) throws IOException {
        FileUtils.writeBytesToFile(getLayout().getAllLayoutData(getReceipts()), destinationFile);
    }
}
