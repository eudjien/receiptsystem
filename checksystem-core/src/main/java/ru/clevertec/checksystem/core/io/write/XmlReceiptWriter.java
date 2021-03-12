package ru.clevertec.checksystem.core.io.write;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.clevertec.checksystem.core.common.io.write.IReceiptWriter;
import ru.clevertec.checksystem.core.configuration.ApplicationXmlMapper;
import ru.clevertec.checksystem.core.entity.receipt.Receipt;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.Collection;

@Component
public class XmlReceiptWriter implements IReceiptWriter {

    private final ApplicationXmlMapper xmlMapper;

    @Autowired
    public XmlReceiptWriter(ApplicationXmlMapper xmlMapper) {
        this.xmlMapper = xmlMapper;
    }

    @Override
    public byte[] write(Collection<Receipt> receipts) throws IOException {
        return xmlMapper.writeValueAsBytes(receipts);
    }

    @Override
    public void write(Collection<Receipt> receipts, OutputStream outputStream) throws IOException {
        xmlMapper.writeValue(outputStream, receipts);
    }

    @Override
    public void write(Collection<Receipt> receipts, File destinationFile) throws IOException {
        Files.createDirectories(destinationFile.toPath().getParent());
        xmlMapper.writeValue(destinationFile, receipts);
    }
}
