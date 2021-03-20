package ru.clevertec.checksystem.core.entity.discount.receiptitem;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import ru.clevertec.checksystem.core.common.builder.discount.receiptitem.ISimplePercentageReceiptItemDiscountBuilder;
import ru.clevertec.checksystem.core.constant.Entities;
import ru.clevertec.checksystem.core.entity.receipt.ReceiptItem;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Collection;


@Entity
@Table(name = Entities.Table.SIMPLE_PERCENTAGE_RECEIPT_ITEM_DISCOUNT)
@DiscriminatorValue(Entities.DiscriminatorValues.SIMPLE_PERCENTAGE_RECEIPT_ITEM_DISCOUNT)
public final class SimplePercentageReceiptItemDiscount extends PercentageReceiptItemDiscount {

    @JsonCreator(mode = JsonCreator.Mode.DISABLED)
    public SimplePercentageReceiptItemDiscount() {
    }

    @JsonCreator(mode = JsonCreator.Mode.DISABLED)
    public SimplePercentageReceiptItemDiscount(String description, Double percent) {
        super(description, percent);
    }

    @JsonCreator(mode = JsonCreator.Mode.DISABLED)
    public SimplePercentageReceiptItemDiscount(String description, Double percent, ReceiptItemDiscount dependentDiscount) {
        super(description, percent, dependentDiscount);
    }

    @JsonCreator
    public SimplePercentageReceiptItemDiscount(
            @JsonProperty("id") Long id,
            @JsonProperty("description") String description,
            @JsonProperty("percent") double percent,
            @JsonProperty("dependentDiscount") ReceiptItemDiscount dependentDiscount) {
        super(description, percent, dependentDiscount);
        setId(id);
    }

    public static class BuilderSimple implements ISimplePercentageReceiptItemDiscountBuilder {

        private final SimplePercentageReceiptItemDiscount discount = new SimplePercentageReceiptItemDiscount();

        @Override
        public ISimplePercentageReceiptItemDiscountBuilder setId(Long id) {
            discount.setId(id);
            return this;
        }

        @Override
        public ISimplePercentageReceiptItemDiscountBuilder setDescription(String description) {
            discount.setDescription(description);
            return this;
        }

        @Override
        public ISimplePercentageReceiptItemDiscountBuilder setDependentDiscount(ReceiptItemDiscount receiptItemDiscount) {
            discount.setDependentDiscount(receiptItemDiscount);
            return this;
        }

        @Override
        public ISimplePercentageReceiptItemDiscountBuilder setReceiptItems(Collection<ReceiptItem> receiptItems) {
            discount.setReceiptItems(receiptItems);
            return this;
        }

        @Override
        public ISimplePercentageReceiptItemDiscountBuilder setPercent(Double percent) {
            discount.setPercent(percent);
            return this;
        }

        @Override
        public SimplePercentageReceiptItemDiscount build() {
            throwIfInvalid();
            return discount;
        }

        private void throwIfInvalid() {
            if (discount.getDescription() == null)
                throw new IllegalArgumentException("Description required to build SimplePercentageReceiptItemDiscount");
        }
    }
}
