package ru.clevertec.checksystem.core.dto.discount.check;

public class SimplePercentageCheckDiscountDto extends CheckDiscountDto {

    private Double percent = 0D;

    public Double getPercent() {
        return percent;
    }

    public void setPercent(Double percent) {
        this.percent = percent;
    }
}
