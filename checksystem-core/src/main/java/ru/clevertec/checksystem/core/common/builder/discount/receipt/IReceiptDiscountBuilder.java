package ru.clevertec.checksystem.core.common.builder.discount.receipt;

import ru.clevertec.checksystem.core.common.builder.discount.IDiscountBuilder;
import ru.clevertec.checksystem.core.entity.receipt.Receipt;
import ru.clevertec.checksystem.core.entity.discount.receipt.ReceiptDiscount;

import java.util.Collection;

public interface IReceiptDiscountBuilder<T extends IReceiptDiscountBuilder<T>> extends IDiscountBuilder<ReceiptDiscount, T> {

    T setReceipts(Collection<Receipt> receipts);
}
