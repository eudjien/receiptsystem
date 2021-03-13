package ru.clevertec.checksystem.core.common.builder.discount.receiptitem;

import ru.clevertec.checksystem.core.common.builder.discount.IDiscountBuilder;
import ru.clevertec.checksystem.core.entity.discount.receiptitem.ReceiptItemDiscount;
import ru.clevertec.checksystem.core.entity.receipt.ReceiptItem;

import java.util.Collection;

public interface IReceiptItemDiscountBuilder<T extends IReceiptItemDiscountBuilder<T>>
        extends IDiscountBuilder<ReceiptItemDiscount, T> {

    T setReceiptItems(Collection<ReceiptItem> receiptItems);
}
