package ru.clevertec.checksystem.core.entity.discount.receiptitem;

import ru.clevertec.checksystem.core.common.IConstable;
import ru.clevertec.checksystem.core.entity.receipt.ReceiptItem;
import ru.clevertec.checksystem.core.util.ThrowUtils;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.math.BigDecimal;

import static ru.clevertec.checksystem.core.Constants.Entities;

@MappedSuperclass
public abstract class ConstantReceiptItemDiscount extends ReceiptItemDiscount implements IConstable<BigDecimal> {

    @Column(name = Entities.Mapping.Column.CONSTANT, nullable = false)
    private BigDecimal constant = BigDecimal.valueOf(0);

    protected ConstantReceiptItemDiscount() {
    }

    protected ConstantReceiptItemDiscount(String description, BigDecimal constant) {
        super(description);
        setConstant(constant);
    }

    protected ConstantReceiptItemDiscount(String description, BigDecimal constant, ReceiptItemDiscount dependentDiscount) {
        super(description, dependentDiscount);
        setConstant(constant);
    }

    public BigDecimal getConstant() {
        return constant;
    }

    public void setConstant(BigDecimal constant) {
        ThrowUtils.Argument.nullValue("constant", constant);
        ThrowUtils.Argument.lessThan("constant", constant, BigDecimal.ZERO);
        this.constant = constant;
    }

    @Override
    public BigDecimal discountAmount(ReceiptItem receiptItem) {
        var dependentDiscountAmount = getDependentDiscount() != null
                ? getDependentDiscount().discountAmount(receiptItem)
                : BigDecimal.ZERO;
        return dependentDiscountAmount.add(constant);
    }
}
