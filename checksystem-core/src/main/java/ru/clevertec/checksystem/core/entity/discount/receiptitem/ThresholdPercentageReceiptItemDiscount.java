package ru.clevertec.checksystem.core.entity.discount.receiptitem;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import ru.clevertec.checksystem.core.common.builder.discount.receiptitem.IThresholdPercentageReceiptItemDiscountBuilder;
import ru.clevertec.checksystem.core.constant.Entities;
import ru.clevertec.checksystem.core.entity.receipt.ReceiptItem;
import ru.clevertec.checksystem.core.util.ThrowUtils;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collection;

@Entity
@Table(name = Entities.Table.THRESHOLD_PERCENTAGE_RECEIPT_ITEM_DISCOUNT)
@DiscriminatorValue(Entities.DiscriminatorValues.THRESHOLD_PERCENTAGE_RECEIPT_ITEM_DISCOUNT)
public final class ThresholdPercentageReceiptItemDiscount extends PercentageReceiptItemDiscount {

    private static final int MIN_THRESHOLD = 1;

    @Column(name = Entities.Column.THRESHOLD, nullable = false)
    private Long threshold = 0L;

    @JsonCreator(mode = JsonCreator.Mode.DISABLED)
    public ThresholdPercentageReceiptItemDiscount() {
    }

    @JsonCreator(mode = JsonCreator.Mode.DISABLED)
    public ThresholdPercentageReceiptItemDiscount(String description, Double percent, Long threshold) {
        super(description, percent);
        setThreshold(threshold);
    }

    @JsonCreator(mode = JsonCreator.Mode.DISABLED)
    public ThresholdPercentageReceiptItemDiscount(
            String description, Double percent, Long threshold, ReceiptItemDiscount dependentDiscount) {
        super(description, percent, dependentDiscount);
        setThreshold(threshold);
    }

    @JsonCreator
    public ThresholdPercentageReceiptItemDiscount(
            @JsonProperty("id") Long id,
            @JsonProperty("description") String description,
            @JsonProperty("percent") double percent,
            @JsonProperty("threshold") Long threshold,
            @JsonProperty("dependentDiscount") ReceiptItemDiscount dependentDiscount) {
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
    public BigDecimal discountAmount(ReceiptItem receiptItem) {

        var total = receiptItem.subTotalAmount();
        var dependentDiscountAmount = BigDecimal.ZERO;

        if (getDependentDiscount() != null) {
            total = total.subtract(getDependentDiscount().discountAmount(receiptItem));
            dependentDiscountAmount = getDependentDiscount().discountAmount(receiptItem);
        }

        if (receiptItem.getQuantity() <= threshold)
            return dependentDiscountAmount;

        var discount = total.divide(BigDecimal.valueOf(100), RoundingMode.HALF_EVEN)
                .multiply(BigDecimal.valueOf(getPercent()));

        return discount.add(dependentDiscountAmount);
    }

    public static class Builder implements IThresholdPercentageReceiptItemDiscountBuilder {

        private final ThresholdPercentageReceiptItemDiscount discount = new ThresholdPercentageReceiptItemDiscount();

        @Override
        public IThresholdPercentageReceiptItemDiscountBuilder setId(Long id) {
            discount.setId(id);
            return this;
        }

        @Override
        public IThresholdPercentageReceiptItemDiscountBuilder setDescription(String description) {
            discount.setDescription(description);
            return this;
        }

        @Override
        public IThresholdPercentageReceiptItemDiscountBuilder setDependentDiscount(ReceiptItemDiscount discount) {
            this.discount.setDependentDiscount(discount);
            return this;
        }

        @Override
        public IThresholdPercentageReceiptItemDiscountBuilder setReceiptItems(Collection<ReceiptItem> receiptItems) {
            discount.setReceiptItems(receiptItems);
            return this;
        }

        @Override
        public IThresholdPercentageReceiptItemDiscountBuilder setPercent(Double percent) {
            discount.setPercent(percent);
            return this;
        }

        @Override
        public IThresholdPercentageReceiptItemDiscountBuilder setThreshold(Long threshold) {
            discount.setThreshold(threshold);
            return this;
        }

        @Override
        public ThresholdPercentageReceiptItemDiscount build() {
            throwIfInvalid();
            return discount;
        }

        private void throwIfInvalid() {
            if (discount.getDescription() == null) {
                throw new IllegalArgumentException(
                        "Description required to build ThresholdPercentageReceiptItemDiscount");
            }
        }
    }
}
