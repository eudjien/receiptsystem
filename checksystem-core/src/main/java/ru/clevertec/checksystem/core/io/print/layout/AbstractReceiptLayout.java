package ru.clevertec.checksystem.core.io.print.layout;

import ru.clevertec.checksystem.core.common.io.print.IReceiptLayout;
import ru.clevertec.checksystem.core.util.ThrowUtils;

public abstract class AbstractReceiptLayout implements IReceiptLayout {

    private static final int MIN_SCALE = 0;

    private String headerQuantity = "QTY";
    private String headerPrice = "PRICE";
    private String headerName = "NAME";
    private String headerTotal = "TOTAL";
    private int scale = 2;
    private String currency = "$";

    protected AbstractReceiptLayout() {
    }

    @Override
    public Integer getScale() {
        return scale;
    }

    @Override
    public void setScale(Integer scale) {
        ThrowUtils.Argument.nullValue("scale", scale);
        ThrowUtils.Argument.lessThan("scale", scale, MIN_SCALE);
        this.scale = scale;
    }

    @Override
    public String getHeaderQuantity() {
        return headerQuantity;
    }

    @Override
    public void setHeaderQuantity(String headerQuantity) {
        this.headerQuantity = headerQuantity;
    }

    @Override
    public String getHeaderPrice() {
        return headerPrice;
    }

    @Override
    public void setHeaderPrice(String headerPrice) {
        this.headerPrice = headerPrice;
    }

    @Override
    public String getHeaderName() {
        return headerName;
    }

    @Override
    public void setHeaderName(String headerName) {
        this.headerName = headerName;
    }

    @Override
    public String getHeaderTotal() {
        return headerTotal;
    }

    @Override
    public void setHeaderTotal(String headerTotal) {
        this.headerTotal = headerTotal;
    }

    @Override
    public String getCurrency() {
        return currency;
    }

    @Override
    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
