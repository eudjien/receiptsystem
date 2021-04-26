package ru.clevertec.checksystem.core.io.print.layout;

import lombok.Data;
import ru.clevertec.checksystem.core.service.common.IReceiptService;
import ru.clevertec.checksystem.core.util.ThrowUtils;

import java.util.Objects;

@Data
public abstract class AbstractReceiptLayout implements IReceiptLayout {

    private static final int MIN_SCALE = 0;

    private final IReceiptService receiptService;

    private String headerQuantity = "QTY";
    private String headerPrice = "PRICE";
    private String headerName = "NAME";
    private String headerTotal = "TOTAL";

    private String currency = "$";

    private int scale = 2;

    public Integer getScale() {
        return this.scale;
    }

    @Override
    public void setScale(Integer scale) {
        Objects.requireNonNull(scale);
        ThrowUtils.Argument.lessThan("scale", scale, MIN_SCALE);
        this.scale = scale;
    }
}
