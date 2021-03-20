package ru.clevertec.checksystem.core.entity.discount.receipt;

import ru.clevertec.checksystem.core.common.IConstable;
import ru.clevertec.checksystem.core.constant.Entities;
import ru.clevertec.checksystem.core.entity.receipt.Receipt;
import ru.clevertec.checksystem.core.util.ThrowUtils;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.math.BigDecimal;

@MappedSuperclass
public abstract class ConstantReceiptDiscount extends ReceiptDiscount implements IConstable<BigDecimal> {

    @Column(name = Entities.Column.CONSTANT, nullable = false)
    private BigDecimal constant = BigDecimal.ZERO;

    protected ConstantReceiptDiscount() {
    }

    protected ConstantReceiptDiscount(String description, BigDecimal constant) {
        super(description);
        setConstant(constant);
    }

    protected ConstantReceiptDiscount(int id, String description, BigDecimal constant) {
        super(description);
        setConstant(constant);
    }

    protected ConstantReceiptDiscount(String description, BigDecimal constant, ReceiptDiscount dependentDiscount) {
        super(description, dependentDiscount);
        setConstant(constant);
    }

    public BigDecimal getConstant() {
        return constant;
    }

    public void setConstant(BigDecimal constant) {
        ThrowUtils.Argument.lessThan("constant", constant, BigDecimal.ZERO);
        this.constant = constant;
    }

    @Override
    public BigDecimal discountAmount(Receipt receipt) {

        var dependentDiscountAmount = getDependentDiscount() != null
                ? getDependentDiscount().discountAmount(receipt)
                : BigDecimal.ZERO;

        var itemsDiscountSum = receipt.itemsDiscountSum();

        return constant.add(dependentDiscountAmount).add(itemsDiscountSum);
    }
}
