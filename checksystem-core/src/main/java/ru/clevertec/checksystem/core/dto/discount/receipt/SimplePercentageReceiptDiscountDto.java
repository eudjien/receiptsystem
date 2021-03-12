package ru.clevertec.checksystem.core.dto.discount.receipt;

public class SimplePercentageReceiptDiscountDto extends ReceiptDiscountDto {

    private Double percent = 0D;

    public Double getPercent() {
        return percent;
    }

    public void setPercent(Double percent) {
        this.percent = percent;
    }
}
