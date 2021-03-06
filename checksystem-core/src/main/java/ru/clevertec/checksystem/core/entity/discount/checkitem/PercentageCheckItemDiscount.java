package ru.clevertec.checksystem.core.entity.discount.checkitem;

import ru.clevertec.checksystem.core.common.IPercentable;
import ru.clevertec.checksystem.core.entity.check.CheckItem;
import ru.clevertec.checksystem.core.util.ThrowUtils;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.math.BigDecimal;
import java.math.RoundingMode;

import static ru.clevertec.checksystem.core.Constants.Entities;
import static ru.clevertec.checksystem.core.Constants.Percent;

@MappedSuperclass
public abstract class PercentageCheckItemDiscount extends CheckItemDiscount implements IPercentable {

    @Column(name = Entities.Mapping.Column.PERCENT, nullable = false)
    private Double percent = 0D;

    protected PercentageCheckItemDiscount() {
    }

    protected PercentageCheckItemDiscount(String description, Double percent) {
        super(description);
        setPercent(percent);
    }

    protected PercentageCheckItemDiscount(String description, Double percent, CheckItemDiscount dependentDiscount) {
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
    public BigDecimal discountAmount(CheckItem checkItem) {

        var subTotalAmount = checkItem.subTotalAmount();
        var dependentDiscountAmount = BigDecimal.ZERO;

        if (getDependentDiscount() != null) {
            subTotalAmount = subTotalAmount.subtract(getDependentDiscount().discountAmount(checkItem));
            dependentDiscountAmount = getDependentDiscount().discountAmount(checkItem);
        }

        var discount = subTotalAmount.divide(BigDecimal.valueOf(Percent.MAX), RoundingMode.HALF_EVEN)
                .multiply(BigDecimal.valueOf(percent));

        return discount.add(dependentDiscountAmount);
    }
}
