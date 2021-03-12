package ru.clevertec.checksystem.core.common.builder;

import ru.clevertec.checksystem.core.common.IBuildable;
import ru.clevertec.checksystem.core.entity.discount.receipt.ReceiptDiscount;
import ru.clevertec.checksystem.core.entity.receipt.Receipt;
import ru.clevertec.checksystem.core.entity.receipt.ReceiptItem;

import java.util.Collection;
import java.util.Date;

public interface IReceiptBuilder extends IBuildable<Receipt> {

    IReceiptBuilder setId(Long id);

    IReceiptBuilder setName(String name);

    IReceiptBuilder setDescription(String description);

    IReceiptBuilder setAddress(String address);

    IReceiptBuilder setPhoneNumber(String phoneNumber);

    IReceiptBuilder setCashier(String cashier);

    IReceiptBuilder setDate(Date date);

    IReceiptBuilder setItems(Collection<ReceiptItem> receiptItems);

    IReceiptBuilder addItem(ReceiptItem receiptItem);

    IReceiptBuilder addItems(Collection<ReceiptItem> receiptItems);

    IReceiptBuilder setDiscounts(Collection<ReceiptDiscount> receiptDiscounts);

    IReceiptBuilder addDiscount(ReceiptDiscount checkDiscount);

    IReceiptBuilder addDiscounts(Collection<ReceiptDiscount> receiptDiscounts);

    Receipt build();
}
