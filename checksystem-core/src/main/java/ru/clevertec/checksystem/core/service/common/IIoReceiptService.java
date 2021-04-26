package ru.clevertec.checksystem.core.service.common;

import ru.clevertec.checksystem.core.data.generate.ReceiptGenerate;
import ru.clevertec.checksystem.core.entity.receipt.Receipt;
import ru.clevertec.checksystem.core.event.IEventEmitter;
import ru.clevertec.checksystem.core.io.FormatType;
import ru.clevertec.checksystem.core.io.format.GenerateFormat;
import ru.clevertec.checksystem.core.io.format.PrintFormat;
import ru.clevertec.checksystem.core.io.format.StructureFormat;

import javax.mail.MessagingException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;

public interface IIoReceiptService extends IEventEmitter<Object> {

    void write(Collection<Receipt> receipts, OutputStream os, FormatType formatType) throws IOException;

    void write(Collection<Receipt> receipts, File destinationFile, FormatType formatType) throws IOException;

    void serialize(Collection<Receipt> receipts, File destinationFile, StructureFormat structureFormat) throws IOException;

    void serialize(Collection<Receipt> receipts, OutputStream os, StructureFormat structureFormat) throws IOException;

    Collection<Receipt> deserialize(File sourceFile, StructureFormat structureFormat) throws IOException;

    Collection<Receipt> deserialize(InputStream is, StructureFormat structureFormat) throws IOException;

    void print(Collection<Receipt> receipts, File destinationFile, PrintFormat printFormat) throws IOException;

    void print(Collection<Receipt> receipts, OutputStream os, PrintFormat printFormat) throws IOException;

    void printWithTemplateToPdf(Collection<Receipt> receipts, File destinationFile, File templateFile) throws IOException;

    void printWithTemplateToPdf(Collection<Receipt> receipts, OutputStream os, File templateFile) throws IOException;

    void printWithTemplateToPdf(Collection<Receipt> receipts, File destinationFile, File templateFile, long templateTopOffset) throws IOException;

    void printWithTemplateToPdf(Collection<Receipt> receipts, OutputStream os, File templateFile, long templateTopOffset) throws IOException;

    Collection<ReceiptGenerate> toGenerate(Collection<Receipt> receipts);

    void toGenerate(Collection<Receipt> receipts, File destinationFile, GenerateFormat generateFormat) throws IOException;

    void toGenerate(Collection<Receipt> receipts, OutputStream os, GenerateFormat generateFormat) throws IOException;

    Collection<Receipt> fromGenerate(File sourceFile, GenerateFormat generateFormat) throws IOException;

    Collection<Receipt> fromGenerate(byte[] bytes, GenerateFormat generateFormat) throws IOException;

    Collection<Receipt> fromGenerate(Collection<ReceiptGenerate> receiptGenerates);

    void sendToEmail(String subject, Collection<Receipt> receipts, FormatType formatType, String... addresses) throws IOException, MessagingException;
}
