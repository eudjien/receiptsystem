package ru.clevertec.checksystem.core.entity.discount.check;

import ru.clevertec.checksystem.core.common.IPercentable;
import ru.clevertec.checksystem.core.entity.check.Check;
import ru.clevertec.checksystem.core.util.ThrowUtils;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.math.BigDecimal;
import java.math.RoundingMode;

import static ru.clevertec.checksystem.core.Constants.Entities;
import static ru.clevertec.checksystem.core.Constants.Percent;

@MappedSuperclass
public abstract class PercentageCheckDiscount extends CheckDiscount implements IPercentable {

    @Column(name = Entities.Mapping.Column.PERCENT, nullable = false)
    private Double percent = 0D;

    protected PercentageCheckDiscount() {
    }

    protected PercentageCheckDiscount(String description, Double percent) {
        super(description);
        setPercent(percent);
    }

    protected PercentageCheckDiscount(String description, Double percent, CheckDiscount dependentDiscount) {
        super(description, dependentDiscount);
        setPercent(percent);
    }

    @Override
    public double getPercent() {
        return percent;
    }

    @Override
    public void setPercent(Double percent) {
        ThrowUtils.Argument.nullValue("percent", percent);
        ThrowUtils.Argument.outOfRange("percent", percent, Percent.MIN, Percent.MAX);
        this.percent = percent;
    }

    @Override
    public BigDecimal discountAmount(Check check) {

        var subTotalAmount = check.subTotalAmount();
        var dependentDiscountAmount = BigDecimal.ZERO;
        var itemsDiscountAmount = check.itemsDiscountSum();

        if (getDependentDiscount() != null) {
            subTotalAmount = subTotalAmount.subtract(getDependentDiscount().discountAmount(check));
            dependentDiscountAmount = getDependentDiscount().discountAmount(check);
        }

        subTotalAmount = subTotalAmount.subtract(itemsDiscountAmount);

        var discount = subTotalAmount
                .divide(BigDecimal.valueOf(Percent.MAX), RoundingMode.HALF_EVEN)
                .multiply(BigDecimal.valueOf(getPercent()));

        return discount.add(dependentDiscountAmount).add(itemsDiscountAmount);
    }
}
