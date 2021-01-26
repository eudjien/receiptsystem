package ru.clevertec.checksystem.core.io.print.strategy;

import ru.clevertec.checksystem.core.entity.check.Check;

public abstract class CheckPrintStrategy implements IPrintStrategy<Check> {

    private String headerQuantity = "QTY";
    private String headerPrice = "PRICE";
    private String headerName = "NAME";
    private String headerTotal = "TOTAL";
    private int scale = 2;
    private String currency = "$";

    public CheckPrintStrategy() {
    }

    public int getScale() {
        return scale;
    }

    public void setScale(int scale) throws IllegalArgumentException {
        if (scale < 0) {
            throw new IllegalArgumentException("Scale cannot be less than 0");
        }
        this.scale = scale;
    }

    public String getHeaderQuantity() {
        return headerQuantity;
    }

    public void setHeaderQuantity(String headerQuantity) {
        this.headerQuantity = headerQuantity;
    }

    public String getHeaderPrice() {
        return headerPrice;
    }

    public void setHeaderPrice(String headerPrice) {
        this.headerPrice = headerPrice;
    }

    public String getHeaderName() {
        return headerName;
    }

    public void setHeaderName(String headerName) {
        this.headerName = headerName;
    }

    public String getHeaderTotal() {
        return headerTotal;
    }

    public void setHeaderTotal(String headerTotal) {
        this.headerTotal = headerTotal;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
