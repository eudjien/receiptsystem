package ru.clevertec.checksystem.core.io.print;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.stereotype.Component;
import ru.clevertec.checksystem.core.entity.receipt.Receipt;
import ru.clevertec.checksystem.core.util.FileUtils;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Collection;

@Component
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
public class ReceiptPrinter extends AbstractPrinter<Receipt> implements IReceiptPrinter {

    private Collection<Receipt> receipts;

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
        var layoutData = getLayout().getAllLayoutData(getReceipts());
        FileUtils.writeBytesToFile(layoutData, destinationFile);
    }
}
