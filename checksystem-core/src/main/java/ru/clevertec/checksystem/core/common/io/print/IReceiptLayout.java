package ru.clevertec.checksystem.core.common.io.print;

import org.springframework.stereotype.Component;
import ru.clevertec.checksystem.core.common.ICurrencyOptionable;
import ru.clevertec.checksystem.core.common.ILayout;
import ru.clevertec.checksystem.core.common.IScalable;
import ru.clevertec.checksystem.core.entity.receipt.Receipt;

@Component
public interface IReceiptLayout extends ILayout<Receipt>, IRecieptHeader, IScalable<Integer>, ICurrencyOptionable {
}
