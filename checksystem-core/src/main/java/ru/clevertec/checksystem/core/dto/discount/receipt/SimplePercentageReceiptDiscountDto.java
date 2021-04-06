package ru.clevertec.checksystem.core.dto.discount.receipt;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class SimplePercentageReceiptDiscountDto extends ReceiptDiscountDto {

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
