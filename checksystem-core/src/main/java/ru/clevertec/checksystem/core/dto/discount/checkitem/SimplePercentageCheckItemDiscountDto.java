package ru.clevertec.checksystem.core.dto.discount.checkitem;

public class SimplePercentageCheckItemDiscountDto extends CheckItemDiscountDto {

    private Double percent = 0D;

    public Double getPercent() {
        return percent;
    }

    public void setPercent(Double percent) {
        this.percent = percent;
    }
}
