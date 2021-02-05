package ru.clevertec.checksystem.core.dto.discount.check;

import java.math.BigDecimal;

public class SimpleConstantCheckDiscountDto extends CheckDiscountDto {

    private BigDecimal constant = BigDecimal.ZERO;

    public BigDecimal getConstant() {
        return constant;
    }

    public void setConstant(BigDecimal constant) {
        this.constant = constant;
    }
}
