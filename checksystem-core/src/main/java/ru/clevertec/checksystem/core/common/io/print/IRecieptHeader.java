package ru.clevertec.checksystem.core.common.io.print;

public interface IRecieptHeader {

    String getHeaderQuantity();

    void setHeaderQuantity(String headerQuantity);

    String getHeaderPrice();

    void setHeaderPrice(String headerPrice);

    String getHeaderName();

    void setHeaderName(String headerName);

    String getHeaderTotal();

    void setHeaderTotal(String headerTotal);
}
