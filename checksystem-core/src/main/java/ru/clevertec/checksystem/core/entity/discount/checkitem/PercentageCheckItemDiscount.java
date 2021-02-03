package ru.clevertec.checksystem.core.entity.discount.checkitem;

import ru.clevertec.checksystem.core.Constants;
import ru.clevertec.checksystem.core.common.IPercentageable;
import ru.clevertec.checksystem.core.util.ThrowUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public abstract class PercentageCheckItemDiscount extends CheckItemDiscount implements IPercentageable {

    private double percent;

    protected PercentageCheckItemDiscount() {
    }

    public PercentageCheckItemDiscount(String description, double percent) {
        super(description);
        setPercent(percent);
    }

    public PercentageCheckItemDiscount(int id, String description, double percent) {
        super(id, description);
        setPercent(percent);
    }

    public PercentageCheckItemDiscount(int id, String description, double percent, CheckItemDiscount dependentDiscount) {
        super(id, description, dependentDiscount);
        setPercent(percent);
    }

    public double getPercent() {
        return percent;
    }

    public void setPercent(double percent) {
        ThrowUtils.Argument.outOfRange("percent", percent, Constants.Percent.MIN, Constants.Percent.MAX);
        this.percent = percent;
    }

    public BigDecimal discountAmount() {

        var subTotalAmount = getCheckItem().subTotalAmount();
        var dependentDiscountAmount = BigDecimal.ZERO;

        if (getDependentDiscount() != null) {
            subTotalAmount = subTotalAmount.subtract(getDependentDiscount().discountAmount());
            dependentDiscountAmount = getDependentDiscount().discountAmount();
        }

        var discount = subTotalAmount.divide(BigDecimal.valueOf(Constants.Percent.MAX), RoundingMode.HALF_EVEN)
                .multiply(BigDecimal.valueOf(percent));

        return discount.add(dependentDiscountAmount);
    }
}
