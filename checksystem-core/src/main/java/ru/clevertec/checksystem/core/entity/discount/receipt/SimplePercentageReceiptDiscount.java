package ru.clevertec.checksystem.core.entity.discount.receipt;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import ru.clevertec.checksystem.core.common.builder.discount.receipt.IPercentageReceiptDiscountBuilder;
import ru.clevertec.checksystem.core.entity.receipt.Receipt;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Collection;

import static ru.clevertec.checksystem.core.Constants.Entities;

@Entity
@Table(name = Entities.Mapping.Table.SIMPLE_PERCENTAGE_RECEIPT_DISCOUNT)
@DiscriminatorValue("SimplePercentageReceiptDiscount")
public final class SimplePercentageReceiptDiscount extends PercentageReceiptDiscount {

    @JsonCreator(mode = JsonCreator.Mode.DISABLED)
    public SimplePercentageReceiptDiscount() {
    }

    @JsonCreator(mode = JsonCreator.Mode.DISABLED)
    public SimplePercentageReceiptDiscount(String description, Double percent) {
        super(description, percent);
    }

    @JsonCreator(mode = JsonCreator.Mode.DISABLED)
    public SimplePercentageReceiptDiscount(String description, Double percent, ReceiptDiscount dependentDiscount) {
        super(description, percent, dependentDiscount);
    }

    @JsonCreator
    public SimplePercentageReceiptDiscount(
            @JsonProperty("id") Long id,
            @JsonProperty("description") String description,
            @JsonProperty("percent") Double percent,
            @JsonProperty("dependentDiscount") ReceiptDiscount dependentDiscount) {
        super(description, percent, dependentDiscount);
        setId(id);
    }

    public static class Builder implements IPercentageReceiptDiscountBuilder {

        private final SimplePercentageReceiptDiscount discount = new SimplePercentageReceiptDiscount();

        @Override
        public IPercentageReceiptDiscountBuilder setId(Long id) {
            discount.setId(id);
            return this;
        }

        @Override
        public IPercentageReceiptDiscountBuilder setDescription(String description) {
            discount.setDescription(description);
            return this;
        }

        @Override
        public IPercentageReceiptDiscountBuilder setDependentDiscount(ReceiptDiscount discount) {
            this.discount.setDependentDiscount(discount);
            return this;
        }

        @Override
        public IPercentageReceiptDiscountBuilder setReceipts(Collection<Receipt> receipts) {
            discount.setReceipts(receipts);
            return this;
        }

        @Override
        public IPercentageReceiptDiscountBuilder setPercent(Double percent) {
            discount.setPercent(percent);
            return this;
        }

        @Override
        public SimplePercentageReceiptDiscount build() {
            throwIfInvalid();
            return discount;
        }

        private void throwIfInvalid() {
            if (discount.getDescription() == null)
                throw new IllegalArgumentException("Description is required");
        }
    }
}
