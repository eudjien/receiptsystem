package ru.clevertec.checksystem.core.dto.discount.receiptitem;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

public final class ThresholdPercentageReceiptItemDiscountDto extends ReceiptItemDiscountDto {

    @NotNull
    @Positive
    private Double percent = 0D;

    @NotNull
    @PositiveOrZero
    private Long threshold = 0L;

    public Double getPercent() {
        return percent;
    }

    public void setPercent(Double percent) {
        this.percent = percent;
    }

    public Long getThreshold() {
        return threshold;
    }

    public void setThreshold(Long threshold) {
        this.threshold = threshold;
    }
}
