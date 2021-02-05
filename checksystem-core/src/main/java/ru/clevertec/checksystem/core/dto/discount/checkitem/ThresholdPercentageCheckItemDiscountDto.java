package ru.clevertec.checksystem.core.dto.discount.checkitem;

public final class ThresholdPercentageCheckItemDiscountDto extends CheckItemDiscountDto {

    private Double percent = 0D;
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
