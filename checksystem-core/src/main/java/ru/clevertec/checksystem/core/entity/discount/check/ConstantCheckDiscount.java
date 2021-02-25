package ru.clevertec.checksystem.core.entity.discount.check;

import ru.clevertec.checksystem.core.Constants;
import ru.clevertec.checksystem.core.common.IConstable;
import ru.clevertec.checksystem.core.entity.check.Check;
import ru.clevertec.checksystem.core.util.ThrowUtils;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.math.BigDecimal;

@MappedSuperclass
public abstract class ConstantCheckDiscount extends CheckDiscount implements IConstable<BigDecimal> {

    @Column(name = Constants.Entities.Mapping.Column.CONSTANT, nullable = false)
    private BigDecimal constant = BigDecimal.ZERO;

    protected ConstantCheckDiscount() {
    }

    protected ConstantCheckDiscount(String description, BigDecimal constant) {
        super(description);
        setConstant(constant);
    }

    protected ConstantCheckDiscount(int id, String description, BigDecimal constant) {
        super(description);
        setConstant(constant);
    }

    protected ConstantCheckDiscount(String description, BigDecimal constant, CheckDiscount dependentDiscount) {
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
    public BigDecimal discountAmount(Check check) {

        var dependentDiscountAmount = getDependentDiscount() != null
                ? getDependentDiscount().discountAmount(check)
                : BigDecimal.ZERO;

        var itemsDiscountSum = check.itemsDiscountSum();

        return constant.add(dependentDiscountAmount).add(itemsDiscountSum);
    }
}
