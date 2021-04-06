package ru.clevertec.checksystem.core.dto.discount.receiptitem;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

public class SimpleConstantReceiptItemDiscountDto extends ReceiptItemDiscountDto {

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
