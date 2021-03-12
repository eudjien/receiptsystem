package ru.clevertec.checksystem.core.common.receipt;

import ru.clevertec.checksystem.core.entity.receipt.Receipt;

import java.util.Collection;

public interface IReceiptAggregable {

    Collection<Receipt> getReceipts();

    void setReceipts(Collection<Receipt> receipts);

    void addReceipt(Receipt receipt);

    void addReceipts(Collection<Receipt> receipts);

    void removeReceipt(Receipt receipt);

    void removeReceipts(Collection<Receipt> receipts);

    void clearReceipts();
}
