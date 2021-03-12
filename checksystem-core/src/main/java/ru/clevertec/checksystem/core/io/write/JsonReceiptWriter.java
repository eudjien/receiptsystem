package ru.clevertec.checksystem.core.io.write;

import org.springframework.stereotype.Component;
import ru.clevertec.checksystem.core.common.io.write.IReceiptWriter;
import ru.clevertec.checksystem.core.configuration.ApplicationJsonMapper;
import ru.clevertec.checksystem.core.entity.receipt.Receipt;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.Collection;

@Component
public class JsonReceiptWriter implements IReceiptWriter {

    private final ApplicationJsonMapper jsonMapper;

    public JsonReceiptWriter(ApplicationJsonMapper jsonMapper) {
        this.jsonMapper = jsonMapper;
    }

    @Override
    public byte[] write(Collection<Receipt> receipts) throws IOException {
        return jsonMapper.writeValueAsBytes(receipts);
    }

    @Override
    public void write(Collection<Receipt> receipts, OutputStream outputStream) throws IOException {
        jsonMapper.writeValue(outputStream, receipts);
    }

    @Override
    public void write(Collection<Receipt> receipts, File destinationFile) throws IOException {
        Files.createDirectories(destinationFile.toPath().getParent());
        jsonMapper.writeValue(destinationFile, receipts);
    }
}
