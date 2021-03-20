package ru.clevertec.checksystem.core.io.write;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.clevertec.checksystem.core.entity.receipt.Receipt;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.Collection;

@Component
public class XmlReceiptWriter implements IReceiptWriter {

    private final XmlMapper xmlMapper;

    @Autowired
    public XmlReceiptWriter(XmlMapper xmlMapper) {
        this.xmlMapper = xmlMapper;
    }

    @Override
    public byte[] write(Collection<Receipt> receipts) throws IOException {
        return xmlMapper.writeValueAsBytes(receipts);
    }

    @Override
    public void write(Collection<Receipt> receipts, OutputStream os) throws IOException {
        xmlMapper.writeValue(os, receipts);
    }

    @Override
    public void write(Collection<Receipt> receipts, File destinationFile) throws IOException {
        Files.createDirectories(destinationFile.toPath().getParent());
        xmlMapper.writeValue(destinationFile, receipts);
    }
}
