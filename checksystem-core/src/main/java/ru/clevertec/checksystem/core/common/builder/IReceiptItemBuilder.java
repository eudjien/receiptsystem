package ru.clevertec.checksystem.core.common.builder;

import ru.clevertec.checksystem.core.common.IBuildable;
import ru.clevertec.checksystem.core.entity.Product;
import ru.clevertec.checksystem.core.entity.discount.receiptitem.ReceiptItemDiscount;
import ru.clevertec.checksystem.core.entity.receipt.ReceiptItem;

import java.util.Collection;

public interface IReceiptItemBuilder extends IBuildable<ReceiptItem> {

    IReceiptItemBuilder setId(Long id);

    IReceiptItemBuilder setProduct(Product product);

    IReceiptItemBuilder setQuantity(Long quantity);

    IReceiptItemBuilder setDiscounts(Collection<ReceiptItemDiscount> receiptItemDiscounts);

    IReceiptItemBuilder addDiscount(ReceiptItemDiscount receiptItemDiscount);

    IReceiptItemBuilder addDiscounts(Collection<ReceiptItemDiscount> receiptItemDiscounts);
}
