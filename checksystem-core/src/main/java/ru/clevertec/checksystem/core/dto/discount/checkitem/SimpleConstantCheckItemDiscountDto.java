package ru.clevertec.checksystem.core.dto.discount.checkitem;

import java.math.BigDecimal;

public class SimpleConstantCheckItemDiscountDto extends CheckItemDiscountDto {

    private BigDecimal constant = BigDecimal.ZERO;

    public BigDecimal getConstant() {
        return constant;
    }

    public void setConstant(BigDecimal constant) {
        this.constant = constant;
    }
}
