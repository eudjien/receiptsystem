package ru.clevertec.checksystem.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.clevertec.checksystem.core.annotation.execution.AroundExecutionLog;
import ru.clevertec.checksystem.core.common.service.IIoReceiptService;
import ru.clevertec.checksystem.core.entity.receipt.Receipt;
import ru.clevertec.checksystem.core.event.EventEmitter;
import ru.clevertec.checksystem.core.factory.io.ReceiptReaderFactory;
import ru.clevertec.checksystem.core.factory.io.ReceiptWriterFactory;
import ru.clevertec.checksystem.core.log.LogLevel;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;

import static ru.clevertec.checksystem.core.Constants.Formats;

@Service
public class IoReceiptService extends EventEmitter<Object> implements IIoReceiptService {

    private final ReceiptReaderFactory receiptReaderFactory;
    private final ReceiptWriterFactory receiptWriterFactory;

    @Autowired
    public IoReceiptService(ReceiptReaderFactory receiptReaderFactory, ReceiptWriterFactory receiptWriterFactory) {
        this.receiptReaderFactory = receiptReaderFactory;
        this.receiptWriterFactory = receiptWriterFactory;
    }

    @Override
    public void serialize(Collection<Receipt> receipts, File destinationFile, String format) throws IOException {
        receiptWriterFactory.instance(format).write(receipts, destinationFile);
    }

    @Override
    public void serialize(Collection<Receipt> receipts, OutputStream outputStream, String format) throws IOException {
        receiptWriterFactory.instance(format).write(receipts, outputStream);
    }

    @Override
    public void serializeToJson(Collection<Receipt> receipts, File destinationFile) throws IOException {
        receiptWriterFactory.instance(Formats.JSON).write(receipts, destinationFile);
    }

    @Override
    public String serializeToJson(Collection<Receipt> receipts) throws IOException {
        var writer = receiptWriterFactory.instance(Formats.JSON);
        return new String(writer.write(receipts));
    }

    @Override
    public void serializeToXml(Collection<Receipt> receipts, File destinationFile) throws IOException {
        receiptWriterFactory.instance(Formats.XML).write(receipts, destinationFile);
    }

    @Override
    public String serializeToXml(Collection<Receipt> receipts) throws IOException {
        var writer = receiptWriterFactory.instance(Formats.XML);
        return new String(writer.write(receipts));
    }

    @AroundExecutionLog(level = LogLevel.NONE)
    @Override
    public Collection<Receipt> deserialize(File sourceFile, String format) throws IOException {
        return receiptReaderFactory.instance(format).read(sourceFile);
    }

    @Override
    public Collection<Receipt> deserialize(InputStream is, String format) throws IOException {
        return receiptReaderFactory.instance(format).read(is);
    }

    @Override
    public Collection<Receipt> deserializeFromJson(File sourceFile) throws IOException {
        return receiptReaderFactory.instance(Formats.JSON).read(sourceFile);
    }

    @Override
    public Collection<Receipt> deserializeFromXml(File sourceFile) throws IOException {
        return receiptReaderFactory.instance(Formats.XML).read(sourceFile);
    }
}
