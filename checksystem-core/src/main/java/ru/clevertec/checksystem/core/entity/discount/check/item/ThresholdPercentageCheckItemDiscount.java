package ru.clevertec.checksystem.core.entity.discount.check.item;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import ru.clevertec.checksystem.core.entity.check.CheckItem;
import ru.clevertec.checksystem.core.common.builder.discount.check.item.IThresholdPercentageCheckItemDiscountBuilder;

import java.math.BigDecimal;
import java.math.RoundingMode;

public final class ThresholdPercentageCheckItemDiscount extends PercentageCheckItemDiscount {

    private int threshold;

    private ThresholdPercentageCheckItemDiscount() {
    }

    public ThresholdPercentageCheckItemDiscount(
            String description, double percent, int threshold)
            throws IllegalArgumentException {
        super(description, percent);
        setThreshold(threshold);
    }

    public ThresholdPercentageCheckItemDiscount(
            int id, String description, double percent, int threshold)
            throws IllegalArgumentException {
        super(id, description, percent);
        setThreshold(threshold);
    }

    @JsonCreator
    public ThresholdPercentageCheckItemDiscount(
            @JsonProperty("id") int id,
            @JsonProperty("description") String description,
            @JsonProperty("percent") double percent,
            @JsonProperty("threshold") int threshold,
            @JsonProperty("childDiscount") CheckItemDiscount childDiscount)
            throws IllegalArgumentException {
        super(id, description, percent, childDiscount);
        setThreshold(threshold);
    }

    public int getThreshold() {
        return threshold;
    }

    public void setThreshold(int threshold) throws IllegalArgumentException {
        if (threshold < 1) {
            throw new IllegalArgumentException("Threshold cannot be less than 1");
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
                .multiply(BigDecimal.valueOf(getPercent()));

        return discount.add(childDiscountSum);
    }

    public static class Builder implements IThresholdPercentageCheckItemDiscountBuilder {

        private final ThresholdPercentageCheckItemDiscount thresholdPercentageCheckItemDiscount = new ThresholdPercentageCheckItemDiscount();

        @Override
        public IThresholdPercentageCheckItemDiscountBuilder setId(int id)
                throws IllegalArgumentException {
            thresholdPercentageCheckItemDiscount.setId(id);
            return this;
        }

        @Override
        public IThresholdPercentageCheckItemDiscountBuilder setDescription(String description)
                throws IllegalArgumentException {
            thresholdPercentageCheckItemDiscount.setDescription(description);
            return this;
        }

        @Override
        public IThresholdPercentageCheckItemDiscountBuilder setChildDiscount(CheckItemDiscount checkItemDiscount)
                throws IllegalArgumentException {
            this.thresholdPercentageCheckItemDiscount.setChildDiscount(checkItemDiscount);
            return this;
        }

        @Override
        public IThresholdPercentageCheckItemDiscountBuilder setCheckItem(CheckItem checkItem) {
            thresholdPercentageCheckItemDiscount.setCheckItem(checkItem);
            return this;
        }

        @Override
        public IThresholdPercentageCheckItemDiscountBuilder setPercent(double percent) {
            thresholdPercentageCheckItemDiscount.setPercent(percent);
            return this;
        }

        @Override
        public IThresholdPercentageCheckItemDiscountBuilder setThreshold(int threshold) {
            thresholdPercentageCheckItemDiscount.setThreshold(threshold);
            return this;
        }

        @Override
        public ThresholdPercentageCheckItemDiscount build() throws IllegalArgumentException {
            throwIfInvalid();
            return thresholdPercentageCheckItemDiscount;
        }

        private void throwIfInvalid() throws IllegalArgumentException {
            if (thresholdPercentageCheckItemDiscount.getDescription() == null) {
                throw new IllegalArgumentException(
                        "Description required to build ThresholdPercentageCheckItemDiscount");
            }
        }
    }
}
