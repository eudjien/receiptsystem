package ru.clevertec.checksystem.core.common;

public interface IReceiptHeader {

    String getHeaderQuantity();

    void setHeaderQuantity(String headerQuantity);

    String getHeaderPrice();

    void setHeaderPrice(String headerPrice);

    String getHeaderName();

    void setHeaderName(String headerName);

    String getHeaderTotal();

    void setHeaderTotal(String headerTotal);
}
