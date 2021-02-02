package ru.clevertec.checksystem.core.entity.discount.check;

import com.fasterxml.jackson.annotation.JsonCreator;
import ru.clevertec.checksystem.core.Constants;
import ru.clevertec.checksystem.core.common.IPercentageable;
import ru.clevertec.checksystem.core.exception.ArgumentNullException;
import ru.clevertec.checksystem.core.exception.ArgumentOutOfRangeException;
import ru.clevertec.checksystem.core.util.ThrowUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public abstract class PercentageCheckDiscount extends CheckDiscount implements IPercentageable {

    private double percent;

    protected PercentageCheckDiscount() throws IllegalArgumentException {
    }

    public PercentageCheckDiscount(String description, double percent) throws ArgumentNullException, ArgumentOutOfRangeException {
        super(description);
        setPercent(percent);
    }

    public PercentageCheckDiscount(int id, String description, double percent) throws ArgumentNullException, ArgumentOutOfRangeException {
        super(id, description);
        setPercent(percent);
    }

    @JsonCreator
    public PercentageCheckDiscount(
            int id, String description, double percent, CheckDiscount dependentDiscount) throws ArgumentNullException, ArgumentOutOfRangeException {
        super(id, description, dependentDiscount);
        setPercent(percent);
    }

    @Override
    public double getPercent() {
        return percent;
    }

    @Override
    public void setPercent(double percent) throws ArgumentOutOfRangeException {
        ThrowUtils.Argument.outOfRange("percent", percent, Constants.Percent.MIN, Constants.Percent.MAX);
        this.percent = percent;
    }

    @Override
    public BigDecimal discountAmount() {

        var subTotal = getCheck().subTotalAmount();
        var dependentDiscountAmount = BigDecimal.ZERO;
        var itemsDiscountAmount = getCheck().itemsDiscountSum();

        if (getDependentDiscount() != null) {
            subTotal = subTotal.subtract(getDependentDiscount().discountAmount());
            dependentDiscountAmount = getDependentDiscount().discountAmount();
        }

        subTotal = subTotal.subtract(itemsDiscountAmount);

        var discount = subTotal.divide(BigDecimal.valueOf(Constants.Percent.MAX), RoundingMode.HALF_EVEN)
                .multiply(BigDecimal.valueOf(percent));

        return discount.add(dependentDiscountAmount).add(itemsDiscountAmount);
    }
}
