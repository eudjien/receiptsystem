package ru.clevertec.checksystem.core.discount.item.base;

import java.math.BigDecimal;
import java.math.RoundingMode;

public abstract class PercentageOffCheckItemDiscount extends CheckItemDiscount {

    private double discountPercent;

    public PercentageOffCheckItemDiscount(String description, double discountPercent) throws Exception {
        super(description);
        setDiscountPercent(discountPercent);
    }

    public PercentageOffCheckItemDiscount(int id, String description, double discountPercent) throws Exception {
        super(id, description);
        setDiscountPercent(discountPercent);
    }

    public PercentageOffCheckItemDiscount(
            int id, String description, double discountPercent, CheckItemDiscount childDiscount) throws Exception {
        super(id, description, childDiscount);
        setDiscountPercent(discountPercent);
    }

    public double getDiscountPercent() {
        return discountPercent;
    }

    public void setDiscountPercent(double percent) throws Exception {
        if (discountPercent < 0 || discountPercent > 100) {
            throw new Exception("Discount percent out of range (0-100)");
        }
        this.discountPercent = percent;
    }

    public BigDecimal discountSum() {

        var subTotal = getCheckItem().subTotal();
        var childDiscountSum = BigDecimal.ZERO;

        if (getChildDiscount() != null) {
            subTotal = subTotal.subtract(getChildDiscount().discountSum());
            childDiscountSum = getChildDiscount().discountSum();
        }

        var discount = subTotal.divide(BigDecimal.valueOf(100), RoundingMode.HALF_EVEN)
                .multiply(BigDecimal.valueOf(discountPercent));

        return discount.add(childDiscountSum);
    }
}
