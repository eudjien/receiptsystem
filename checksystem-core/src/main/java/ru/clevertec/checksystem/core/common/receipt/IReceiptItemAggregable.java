package ru.clevertec.checksystem.core.common.receipt;

import ru.clevertec.checksystem.core.entity.receipt.ReceiptItem;

import java.util.Collection;

public interface IReceiptItemAggregable {

    Collection<ReceiptItem> getReceiptItems();

    void setReceiptItems(Collection<ReceiptItem> receiptItems);

    void addReceiptItem(ReceiptItem receiptItem);

    void addReceiptItems(Collection<ReceiptItem> receiptItems);

    void removeReceiptItem(ReceiptItem receiptItem);

    void removeReceiptItems(Collection<ReceiptItem> receiptItems);

    void clearReceiptItems();
}
