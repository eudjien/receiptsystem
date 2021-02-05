package ru.clevertec.checksystem.core.entity.discount.checkitem;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import ru.clevertec.checksystem.core.Constants;
import ru.clevertec.checksystem.core.common.builder.discount.checkitem.IThresholdPercentageCheckItemDiscountBuilder;
import ru.clevertec.checksystem.core.entity.check.CheckItem;
import ru.clevertec.checksystem.core.util.ThrowUtils;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collection;

@Entity
@Table(name = Constants.Entities.Mapping.Table.THRESHOLD_PERCENTAGE_CHECK_ITEM_DISCOUNT)
@DiscriminatorValue("ThresholdPercentageCheckItemDiscount")
public final class ThresholdPercentageCheckItemDiscount extends PercentageCheckItemDiscount {

    private static final int MIN_THRESHOLD = 1;

    @Column(name = Constants.Entities.Mapping.Column.THRESHOLD, nullable = false)
    private Long threshold = 0L;

    @JsonCreator(mode = JsonCreator.Mode.DISABLED)
    public ThresholdPercentageCheckItemDiscount() {
    }

    @JsonCreator(mode = JsonCreator.Mode.DISABLED)
    public ThresholdPercentageCheckItemDiscount(String description, Double percent, Long threshold) {
        super(description, percent);
        setThreshold(threshold);
    }

    @JsonCreator(mode = JsonCreator.Mode.DISABLED)
    public ThresholdPercentageCheckItemDiscount(
            String description, Double percent, Long threshold, CheckItemDiscount dependentDiscount) {
        super(description, percent, dependentDiscount);
        setThreshold(threshold);
    }

    @JsonCreator
    public ThresholdPercentageCheckItemDiscount(
            @JsonProperty("id") Long id,
            @JsonProperty("description") String description,
            @JsonProperty("percent") double percent,
            @JsonProperty("threshold") Long threshold,
            @JsonProperty("dependentDiscount") CheckItemDiscount dependentDiscount) {
        super(description, percent, dependentDiscount);
        setId(id);
        setThreshold(threshold);
    }

    public Long getThreshold() {
        return threshold;
    }

    public void setThreshold(Long threshold) {
        ThrowUtils.Argument.nullValue("threshold", threshold);
        ThrowUtils.Argument.lessThan("threshold", threshold, MIN_THRESHOLD);
        this.threshold = threshold;
    }

    @Override
    public BigDecimal discountAmount(CheckItem checkItem) {

        var total = checkItem.subTotalAmount();
        var dependentDiscountAmount = BigDecimal.ZERO;

        if (getDependentDiscount() != null) {
            total = total.subtract(getDependentDiscount().discountAmount(checkItem));
            dependentDiscountAmount = getDependentDiscount().discountAmount(checkItem);
        }

        if (checkItem.getQuantity() <= threshold)
            return dependentDiscountAmount;

        var discount = total.divide(BigDecimal.valueOf(100), RoundingMode.HALF_EVEN)
                .multiply(BigDecimal.valueOf(getPercent()));

        return discount.add(dependentDiscountAmount);
    }

    public static class Builder implements IThresholdPercentageCheckItemDiscountBuilder {

        private final ThresholdPercentageCheckItemDiscount discount = new ThresholdPercentageCheckItemDiscount();

        @Override
        public IThresholdPercentageCheckItemDiscountBuilder setId(Long id) {
            discount.setId(id);
            return this;
        }

        @Override
        public IThresholdPercentageCheckItemDiscountBuilder setDescription(String description) {
            discount.setDescription(description);
            return this;
        }

        @Override
        public IThresholdPercentageCheckItemDiscountBuilder setDependentDiscount(CheckItemDiscount discount) {
            this.discount.setDependentDiscount(discount);
            return this;
        }

        @Override
        public IThresholdPercentageCheckItemDiscountBuilder setCheckItems(Collection<CheckItem> checkItems) {
            discount.setCheckItems(checkItems);
            return this;
        }

        @Override
        public IThresholdPercentageCheckItemDiscountBuilder setPercent(Double percent) {
            discount.setPercent(percent);
            return this;
        }

        @Override
        public IThresholdPercentageCheckItemDiscountBuilder setThreshold(Long threshold) {
            discount.setThreshold(threshold);
            return this;
        }

        @Override
        public ThresholdPercentageCheckItemDiscount build() {
            throwIfInvalid();
            return discount;
        }

        private void throwIfInvalid() {
            if (discount.getDescription() == null) {
                throw new IllegalArgumentException(
                        "Description required to build ThresholdPercentageCheckItemDiscount");
            }
        }
    }
}
