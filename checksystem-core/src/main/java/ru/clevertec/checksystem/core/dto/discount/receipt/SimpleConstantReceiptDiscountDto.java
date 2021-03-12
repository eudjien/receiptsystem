package ru.clevertec.checksystem.core.dto.discount.receipt;

import java.math.BigDecimal;

public class SimpleConstantReceiptDiscountDto extends ReceiptDiscountDto {

    private BigDecimal constant = BigDecimal.ZERO;

    public BigDecimal getConstant() {
        return constant;
    }

    public void setConstant(BigDecimal constant) {
        this.constant = constant;
    }
}
