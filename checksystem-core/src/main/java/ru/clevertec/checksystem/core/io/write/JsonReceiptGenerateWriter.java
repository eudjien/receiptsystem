package ru.clevertec.checksystem.core.io.write;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.clevertec.checksystem.core.data.generate.ReceiptGenerate;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.Collection;

@Component
public class JsonReceiptGenerateWriter implements IReceiptGenerateWriter {

    private final ObjectMapper objectMapper;

    @Autowired
    public JsonReceiptGenerateWriter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public byte[] write(Collection<ReceiptGenerate> receiptGenerates) throws IOException {
        return objectMapper.writeValueAsBytes(receiptGenerates);
    }

    @Override
    public void write(Collection<ReceiptGenerate> receiptGenerates, OutputStream os) throws IOException {
        objectMapper.writeValue(os, receiptGenerates);
    }

    @Override
    public void write(Collection<ReceiptGenerate> receiptGenerates, File destinationFile) throws IOException {
        Files.createDirectories(destinationFile.toPath().getParent());
        objectMapper.writeValue(destinationFile, receiptGenerates);
    }
}
