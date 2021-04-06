package ru.clevertec.checksystem.core.dto.discount.receiptitem;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class SimplePercentageReceiptItemDiscountDto extends ReceiptItemDiscountDto {

    @NotNull
    @Positive
    private Double percent = 0D;

    public Double getPercent() {
        return percent;
    }

    public void setPercent(Double percent) {
        this.percent = percent;
    }
}
