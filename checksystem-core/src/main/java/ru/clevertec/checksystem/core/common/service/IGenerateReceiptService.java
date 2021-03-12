package ru.clevertec.checksystem.core.common.service;

import ru.clevertec.checksystem.core.dto.ReceiptGenerate;
import ru.clevertec.checksystem.core.entity.receipt.Receipt;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

public interface IGenerateReceiptService extends IService {

    Collection<ReceiptGenerate> toGenerate(Collection<Receipt> receipts);

    void toGenerate(Collection<Receipt> receipts, File destinationFile, String format) throws IOException;

    Collection<Receipt> fromGenerate(File sourceFile, String format) throws IOException;

    Collection<Receipt> fromGenerate(byte[] bytes, String format) throws IOException;

    Collection<Receipt> fromGenerate(Collection<ReceiptGenerate> receiptGenerates);
}
