package ru.clevertec.checksystem.core.common.service;

import ru.clevertec.checksystem.core.entity.receipt.Receipt;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Collection;

public interface IPrintingReceiptService extends IService {

    void print(Collection<Receipt> receipts, File destinationFile, String format) throws IOException;

    void print(Collection<Receipt> receipts, OutputStream outputStream, String format) throws IOException;

    void printToHtml(Collection<Receipt> receipts, File destinationFile) throws IOException;

    void printToHtml(Collection<Receipt> receipts, OutputStream outputStream) throws IOException;

    String printToHtml(Collection<Receipt> receipts) throws IOException;

    void printToPdf(Collection<Receipt> receipts, File destinationFile) throws IOException;

    void printToPdf(Collection<Receipt> receipts, OutputStream outputStream) throws IOException;

    byte[] printToPdf(Collection<Receipt> receipts) throws IOException;

    void printWithTemplateToPdf(Collection<Receipt> receipts, File destinationFile, File templateFile) throws IOException;

    void printWithTemplateToPdf(Collection<Receipt> receipts, OutputStream stream, File templateFile) throws IOException;

    void printWithTemplateToPdf(Collection<Receipt> receipts, File destinationFile, File templateFile, long templateTopOffset) throws IOException;

    void printWithTemplateToPdf(Collection<Receipt> receipts, OutputStream outputStream, File templateFile, long templateTopOffset) throws IOException;

    byte[] printWithTemplateToPdf(Collection<Receipt> receipts, File templateFile) throws IOException;

    byte[] printWithTemplateToPdf(Collection<Receipt> receipts, File templateFile, long templateOffset) throws IOException;

    void printToText(Collection<Receipt> receipts, File destinationFile) throws IOException;

    void printToText(Collection<Receipt> receipts, OutputStream outputStream) throws IOException;

    String printToText(Collection<Receipt> receipts) throws IOException;
}
