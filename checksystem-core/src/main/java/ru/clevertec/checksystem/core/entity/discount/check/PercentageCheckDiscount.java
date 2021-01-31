package ru.clevertec.checksystem.core.entity.discount.check;

import com.fasterxml.jackson.annotation.JsonCreator;
import ru.clevertec.checksystem.core.common.IPercentageable;

import java.math.BigDecimal;
import java.math.RoundingMode;

public abstract class PercentageCheckDiscount extends CheckDiscount implements IPercentageable {

    private double percent;

    protected PercentageCheckDiscount()
            throws IllegalArgumentException {
    }

    public PercentageCheckDiscount(String description, double percent)
            throws IllegalArgumentException {
        super(description);
        setPercent(percent);
    }

    public PercentageCheckDiscount(int id, String description, double percent)
            throws IllegalArgumentException {
        super(id, description);
        setPercent(percent);
    }

    @JsonCreator
    public PercentageCheckDiscount(
            int id, String description, double percent, CheckDiscount dependentDiscount)
            throws IllegalArgumentException {
        super(id, description, dependentDiscount);
        setPercent(percent);
    }

    @Override
    public double getPercent() {
        return percent;
    }

    @Override
    public void setPercent(double percent) throws IllegalArgumentException {
        if (this.percent < 0 || this.percent > 100) {
            throw new IllegalArgumentException("Discount percent out of range (0-100)");
        }
        this.percent = percent;
    }

    @Override
    public BigDecimal discountAmount() {

        var subTotal = getCheck().subTotal();
        var dependentDiscountAmount = BigDecimal.ZERO;
        var itemsDiscountAmount = getCheck().itemsDiscountSum();

        if (getDependentDiscount() != null) {
            subTotal = subTotal.subtract(getDependentDiscount().discountAmount());
            dependentDiscountAmount = getDependentDiscount().discountAmount();
        }

        subTotal = subTotal.subtract(itemsDiscountAmount);

        var discount = subTotal.divide(BigDecimal.valueOf(100), RoundingMode.HALF_EVEN)
                .multiply(BigDecimal.valueOf(percent));

        return discount.add(dependentDiscountAmount).add(itemsDiscountAmount);
    }
}
