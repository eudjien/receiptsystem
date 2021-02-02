package ru.clevertec.checksystem.core.entity.discount.check.item;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import ru.clevertec.checksystem.core.common.builder.discount.check.item.IThresholdPercentageCheckItemDiscountBuilder;
import ru.clevertec.checksystem.core.entity.check.CheckItem;
import ru.clevertec.checksystem.core.exception.ArgumentNullException;
import ru.clevertec.checksystem.core.exception.ArgumentOutOfRangeException;
import ru.clevertec.checksystem.core.util.ThrowUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public final class ThresholdPercentageCheckItemDiscount extends PercentageCheckItemDiscount {

    private static final int MIN_THRESHOLD = 1;

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
            @JsonProperty("dependentDiscount") CheckItemDiscount dependentDiscount)
            throws IllegalArgumentException {
        super(id, description, percent, dependentDiscount);
        setThreshold(threshold);
    }

    public int getThreshold() {
        return threshold;
    }

    public void setThreshold(int threshold) throws ArgumentOutOfRangeException {
        ThrowUtils.Argument.lessThan("threshold", threshold, MIN_THRESHOLD);
        this.threshold = threshold;
    }

    @Override
    public BigDecimal discountAmount() {

        var total = getCheckItem().subTotalAmount();
        var dependentDiscountAmount = BigDecimal.ZERO;

        if (getDependentDiscount() != null) {
            total = total.subtract(getDependentDiscount().discountAmount());
            dependentDiscountAmount = getDependentDiscount().discountAmount();
        }

        if (getCheckItem().getQuantity() <= threshold) {
            return dependentDiscountAmount;
        }

        var discount = total.divide(BigDecimal.valueOf(100), RoundingMode.HALF_EVEN)
                .multiply(BigDecimal.valueOf(getPercent()));

        return discount.add(dependentDiscountAmount);
    }

    public static class Builder implements IThresholdPercentageCheckItemDiscountBuilder {

        private final ThresholdPercentageCheckItemDiscount discount = new ThresholdPercentageCheckItemDiscount();

        @Override
        public IThresholdPercentageCheckItemDiscountBuilder setId(int id)
                throws IllegalArgumentException {
            discount.setId(id);
            return this;
        }

        @Override
        public IThresholdPercentageCheckItemDiscountBuilder setDescription(String description)
                throws IllegalArgumentException {
            discount.setDescription(description);
            return this;
        }

        @Override
        public IThresholdPercentageCheckItemDiscountBuilder setDependentDiscount(CheckItemDiscount discount)
                throws IllegalArgumentException {
            this.discount.setDependentDiscount(discount);
            return this;
        }

        @Override
        public IThresholdPercentageCheckItemDiscountBuilder setCheckItem(CheckItem checkItem) throws ArgumentNullException {
            discount.setCheckItem(checkItem);
            return this;
        }

        @Override
        public IThresholdPercentageCheckItemDiscountBuilder setPercent(double percent) throws ArgumentOutOfRangeException {
            discount.setPercent(percent);
            return this;
        }

        @Override
        public IThresholdPercentageCheckItemDiscountBuilder setThreshold(int threshold) throws ArgumentOutOfRangeException {
            discount.setThreshold(threshold);
            return this;
        }

        @Override
        public ThresholdPercentageCheckItemDiscount build() throws IllegalArgumentException {
            throwIfInvalid();
            return discount;
        }

        private void throwIfInvalid() throws IllegalArgumentException {
            if (discount.getDescription() == null) {
                throw new IllegalArgumentException(
                        "Description required to build ThresholdPercentageCheckItemDiscount");
            }
        }
    }
}
