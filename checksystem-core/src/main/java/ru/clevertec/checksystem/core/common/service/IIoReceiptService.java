package ru.clevertec.checksystem.core.common.service;

import ru.clevertec.checksystem.core.entity.receipt.Receipt;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;

public interface IIoReceiptService extends IService {

    void serialize(Collection<Receipt> receipts, File destinationFile, String format)
            throws IOException;

    void serialize(Collection<Receipt> receipts, OutputStream os, String format)
            throws IOException;

    void serializeToJson(Collection<Receipt> receipts, File destinationFile)
            throws IOException;

    String serializeToJson(Collection<Receipt> receipts)
            throws IOException;

    void serializeToXml(Collection<Receipt> receipts, File destinationFile)
            throws IOException;

    String serializeToXml(Collection<Receipt> receipts)
            throws IOException;

    Collection<Receipt> deserialize(File sourceFile, String format)
            throws IOException;

    Collection<Receipt> deserialize(InputStream is, String format)
            throws IOException;

    Collection<Receipt> deserializeFromJson(File sourceFile)
            throws IOException;

    Collection<Receipt> deserializeFromXml(File sourceFile)
            throws IOException;
}
