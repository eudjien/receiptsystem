package ru.clevertec.checksystem.core.dto.discount.receiptitem;

public class SimplePercentageReceiptItemDiscountDto extends ReceiptItemDiscountDto {

    private Double percent = 0D;

    public Double getPercent() {
        return percent;
    }

    public void setPercent(Double percent) {
        this.percent = percent;
    }
}
