package ru.clevertec.checksystem.core.dto.discount.receipt;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

public class SimpleConstantReceiptDiscountDto extends ReceiptDiscountDto {

    @NotNull
    @Positive
    private BigDecimal constant = BigDecimal.ZERO;

    public BigDecimal getConstant() {
        return constant;
    }

    public void setConstant(BigDecimal constant) {
        this.constant = constant;
    }
}
