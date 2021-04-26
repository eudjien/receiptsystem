package ru.clevertec.checksystem.core.io.print.layout;

import ru.clevertec.checksystem.core.common.ICurrencyOptionable;
import ru.clevertec.checksystem.core.common.IScalable;
import ru.clevertec.checksystem.core.entity.receipt.Receipt;

public interface IReceiptLayout extends ILayout<Receipt>, IScalable<Integer>, ICurrencyOptionable {

    String getHeaderQuantity();

    void setHeaderQuantity(String headerQuantity);

    String getHeaderPrice();

    void setHeaderPrice(String headerPrice);

    String getHeaderName();

    void setHeaderName(String headerName);

    String getHeaderTotal();

    void setHeaderTotal(String headerTotal);
}
