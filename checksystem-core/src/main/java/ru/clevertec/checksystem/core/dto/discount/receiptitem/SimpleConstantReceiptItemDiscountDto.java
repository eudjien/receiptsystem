package ru.clevertec.checksystem.core.dto.discount.receiptitem;

import java.math.BigDecimal;

public class SimpleConstantReceiptItemDiscountDto extends ReceiptItemDiscountDto {

    private BigDecimal constant = BigDecimal.ZERO;

    public BigDecimal getConstant() {
        return constant;
    }

    public void setConstant(BigDecimal constant) {
        this.constant = constant;
    }
}
