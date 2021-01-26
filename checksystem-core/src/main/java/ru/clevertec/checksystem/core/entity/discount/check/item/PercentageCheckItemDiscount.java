package ru.clevertec.checksystem.core.entity.discount.check.item;

import ru.clevertec.checksystem.core.common.IPercentageable;

import java.math.BigDecimal;
import java.math.RoundingMode;

public abstract class PercentageCheckItemDiscount extends CheckItemDiscount implements IPercentageable {

    private double percent;

    protected PercentageCheckItemDiscount() {
    }

    public PercentageCheckItemDiscount(String description, double percent)
            throws IllegalArgumentException {
        super(description);
        setPercent(percent);
    }

    public PercentageCheckItemDiscount(int id, String description, double percent)
            throws IllegalArgumentException {
        super(id, description);
        setPercent(percent);
    }

    public PercentageCheckItemDiscount(
            int id, String description, double percent, CheckItemDiscount childDiscount)
            throws IllegalArgumentException {
        super(id, description, childDiscount);
        setPercent(percent);
    }

    public double getPercent() {
        return percent;
    }

    public void setPercent(double percent) throws IllegalArgumentException {
        if (this.percent < 0 || this.percent > 100) {
            throw new IllegalArgumentException("Discount percent out of range (0-100)");
        }
        this.percent = percent;
    }

    public BigDecimal discountSum() {

        var subTotal = getCheckItem().subTotal();
        var childDiscountSum = BigDecimal.ZERO;

        if (getChildDiscount() != null) {
            subTotal = subTotal.subtract(getChildDiscount().discountSum());
            childDiscountSum = getChildDiscount().discountSum();
        }

        var discount = subTotal.divide(BigDecimal.valueOf(100), RoundingMode.HALF_EVEN)
                .multiply(BigDecimal.valueOf(percent));

        return discount.add(childDiscountSum);
    }
}
