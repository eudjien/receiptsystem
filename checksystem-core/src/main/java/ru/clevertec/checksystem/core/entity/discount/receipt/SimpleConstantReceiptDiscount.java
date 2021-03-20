package ru.clevertec.checksystem.core.entity.discount.receipt;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import ru.clevertec.checksystem.core.common.builder.discount.receipt.IConstantReceiptDiscountBuilder;
import ru.clevertec.checksystem.core.constant.Entities;
import ru.clevertec.checksystem.core.entity.receipt.Receipt;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Collection;


@Entity
@Table(name = Entities.Table.SIMPLE_CONSTANT_RECEIPT_DISCOUNT)
@DiscriminatorValue(Entities.DiscriminatorValues.SIMPLE_CONSTANT_RECEIPT_DISCOUNT)
public final class SimpleConstantReceiptDiscount extends ConstantReceiptDiscount {

    @JsonCreator(mode = JsonCreator.Mode.DISABLED)
    public SimpleConstantReceiptDiscount() {
    }

    @JsonCreator(mode = JsonCreator.Mode.DISABLED)
    public SimpleConstantReceiptDiscount(String description, BigDecimal constant) {
        super(description, constant);
    }

    @JsonCreator(mode = JsonCreator.Mode.DISABLED)
    public SimpleConstantReceiptDiscount(String description, BigDecimal constant, ReceiptDiscount dependentDiscount) {
        super(description, constant, dependentDiscount);
    }

    @JsonCreator
    public SimpleConstantReceiptDiscount(
            @JsonProperty("id") Long id,
            @JsonProperty("description") String description,
            @JsonProperty("constant") BigDecimal constant,
            @JsonProperty("dependentDiscount") ReceiptDiscount dependentDiscount) {
        super(description, constant, dependentDiscount);
        setId(id);
    }

    public static class Builder implements IConstantReceiptDiscountBuilder {

        private final SimpleConstantReceiptDiscount discount = new SimpleConstantReceiptDiscount();

        @Override
        public IConstantReceiptDiscountBuilder setId(Long id) {
            discount.setId(id);
            return this;
        }

        @Override
        public IConstantReceiptDiscountBuilder setDescription(String description) {
            discount.setDescription(description);
            return this;
        }

        @Override
        public IConstantReceiptDiscountBuilder setDependentDiscount(ReceiptDiscount discount) {
            this.discount.setDependentDiscount(discount);
            return this;
        }

        @Override
        public IConstantReceiptDiscountBuilder setReceipts(Collection<Receipt> receipts) {
            discount.setReceipts(receipts);
            return this;
        }

        @Override
        public IConstantReceiptDiscountBuilder setConstant(BigDecimal constant) {
            discount.setConstant(constant);
            return this;
        }

        @Override
        public SimpleConstantReceiptDiscount build() {
            throwIfInvalid();
            return discount;
        }

        private void throwIfInvalid() {
            if (discount.getDescription() == null)
                throw new IllegalArgumentException("Description required to build SimpleConstantReceiptDiscount");
            if (discount.getConstant() == null)
                throw new IllegalArgumentException("Constant required to build SimpleConstantReceiptDiscount");
        }
    }
}
