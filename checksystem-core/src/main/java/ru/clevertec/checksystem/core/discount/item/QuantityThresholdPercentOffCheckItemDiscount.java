package ru.clevertec.checksystem.core.discount.item;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import ru.clevertec.checksystem.core.discount.item.base.CheckItemDiscount;
import ru.clevertec.checksystem.core.discount.item.base.PercentageOffCheckItemDiscount;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class QuantityThresholdPercentOffCheckItemDiscount extends PercentageOffCheckItemDiscount {

    private int threshold;

    public QuantityThresholdPercentOffCheckItemDiscount(
            String description, double discountPercent, int threshold) throws Exception {
        super(description, discountPercent);
        setThreshold(threshold);
    }

    public QuantityThresholdPercentOffCheckItemDiscount(
            int id, String description, double discountPercent, int threshold) throws Exception {
        super(id, description, discountPercent);
        setThreshold(threshold);
    }

    @JsonCreator
    public QuantityThresholdPercentOffCheckItemDiscount(
            @JsonProperty("id") int id,
            @JsonProperty("description") String description,
            @JsonProperty("discountPercent") double discountPercent,
            @JsonProperty("threshold") int threshold,
            @JsonProperty("childDiscount") CheckItemDiscount childDiscount) throws Exception {
        super(id, description, discountPercent, childDiscount);
        setThreshold(threshold);
    }

    public int getThreshold() {
        return threshold;
    }

    public void setThreshold(int threshold) throws Exception {
        if (threshold < 1) {
            throw new Exception("Threshold cannot be less than 1");
        }
        this.threshold = threshold;
    }

    @Override
    public BigDecimal discountSum() {

        var total = getCheckItem().subTotal();
        var childDiscountSum = BigDecimal.ZERO;

        if (getChildDiscount() != null) {
            total = total.subtract(getChildDiscount().discountSum());
            childDiscountSum = getChildDiscount().discountSum();
        }

        if (getCheckItem().getQuantity() <= threshold) {
            return childDiscountSum;
        }

        var discount = total.divide(BigDecimal.valueOf(100), RoundingMode.HALF_EVEN)
                .multiply(BigDecimal.valueOf(getDiscountPercent()));

        return discount.add(childDiscountSum);
    }
}
