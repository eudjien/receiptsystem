package ru.clevertec.checksystem.core.io.print;

import ru.clevertec.checksystem.core.entity.receipt.Receipt;

import java.util.Collection;

public interface IReceiptPrinter extends IPrinter<Receipt> {
    void setReceipts(Collection<Receipt> receipts);
}
