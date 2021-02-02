package ru.clevertec.checksystem.core.entity.discount.check.item;

import ru.clevertec.checksystem.core.Constants;
import ru.clevertec.checksystem.core.common.IPercentageable;
import ru.clevertec.checksystem.core.exception.ArgumentOutOfRangeException;
import ru.clevertec.checksystem.core.util.ThrowUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public abstract class PercentageCheckItemDiscount extends CheckItemDiscount implements IPercentageable {

    private double percent;

    protected PercentageCheckItemDiscount() {
    }

    public PercentageCheckItemDiscount(String description, double percent)
            throws ArgumentOutOfRangeException {
        super(description);
        setPercent(percent);
    }

    public PercentageCheckItemDiscount(int id, String description, double percent)
            throws ArgumentOutOfRangeException {
        super(id, description);
        setPercent(percent);
    }

    public PercentageCheckItemDiscount(
            int id, String description, double percent, CheckItemDiscount dependentDiscount)
            throws ArgumentOutOfRangeException {
        super(id, description, dependentDiscount);
        setPercent(percent);
    }

    public double getPercent() {
        return percent;
    }

    public void setPercent(double percent) throws ArgumentOutOfRangeException {
        ThrowUtils.Argument.outOfRange("percent", percent, Constants.Percent.MIN, Constants.Percent.MAX);
        this.percent = percent;
    }

    public BigDecimal discountAmount() {

        var subTotal = getCheckItem().subTotalAmount();
        var dependentDiscountAmount = BigDecimal.ZERO;

        if (getDependentDiscount() != null) {
            subTotal = subTotal.subtract(getDependentDiscount().discountAmount());
            dependentDiscountAmount = getDependentDiscount().discountAmount();
        }

        var discount = subTotal.divide(BigDecimal.valueOf(Constants.Percent.MAX), RoundingMode.HALF_EVEN)
                .multiply(BigDecimal.valueOf(percent));

        return discount.add(dependentDiscountAmount);
    }
}
