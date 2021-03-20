package ru.clevertec.checksystem.core.io.print.layout;

import ru.clevertec.checksystem.core.common.ICurrencyOptionable;
import ru.clevertec.checksystem.core.common.IReceiptHeader;
import ru.clevertec.checksystem.core.common.IScalable;
import ru.clevertec.checksystem.core.entity.receipt.Receipt;

public interface IReceiptLayout extends ILayout<Receipt>, IReceiptHeader, IScalable<Integer>, ICurrencyOptionable {
}
