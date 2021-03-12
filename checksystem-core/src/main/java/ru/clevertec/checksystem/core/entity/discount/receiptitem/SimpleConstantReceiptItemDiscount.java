package ru.clevertec.checksystem.core.entity.discount.receiptitem;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import ru.clevertec.checksystem.core.common.builder.discount.receiptitem.IConstantReceiptItemDiscountBuilder;
import ru.clevertec.checksystem.core.entity.receipt.ReceiptItem;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Collection;

import static ru.clevertec.checksystem.core.Constants.Entities;

@Entity
@Table(name = Entities.Mapping.Table.SIMPLE_CONSTANT_RECEIPT_ITEM_DISCOUNT)
@DiscriminatorValue("SimpleConstantReceiptItemDiscount")
public final class SimpleConstantReceiptItemDiscount extends ConstantReceiptItemDiscount {

    @JsonCreator(mode = JsonCreator.Mode.DISABLED)
    public SimpleConstantReceiptItemDiscount() {
    }

    @JsonCreator(mode = JsonCreator.Mode.DISABLED)
    public SimpleConstantReceiptItemDiscount(String description, BigDecimal constant) {
        super(description, constant);
    }

    @JsonCreator(mode = JsonCreator.Mode.DISABLED)
    public SimpleConstantReceiptItemDiscount(String description, BigDecimal constant, ReceiptItemDiscount dependentDiscount) {
        super(description, constant, dependentDiscount);
    }

    @JsonCreator
    public SimpleConstantReceiptItemDiscount(
            @JsonProperty("id") Long id,
            @JsonProperty("description") String description,
            @JsonProperty("constant") BigDecimal constant,
            @JsonProperty("dependentDiscount") ReceiptItemDiscount dependentDiscount) {
        super(description, constant, dependentDiscount);
        setId(id);
    }

    public static class Builder implements IConstantReceiptItemDiscountBuilder {

        private final SimpleConstantReceiptItemDiscount discount = new SimpleConstantReceiptItemDiscount();

        @Override
        public IConstantReceiptItemDiscountBuilder setId(Long id) {
            discount.setId(id);
            return this;
        }

        @Override
        public IConstantReceiptItemDiscountBuilder setDescription(String description) {
            discount.setDescription(description);
            return this;
        }

        @Override
        public IConstantReceiptItemDiscountBuilder setDependentDiscount(ReceiptItemDiscount receiptItemDiscount) {
            discount.setDependentDiscount(receiptItemDiscount);
            return this;
        }

        @Override
        public IConstantReceiptItemDiscountBuilder setReceiptItems(Collection<ReceiptItem> receiptItems) {
            discount.setReceiptItems(receiptItems);
            return this;
        }

        @Override
        public IConstantReceiptItemDiscountBuilder setConstant(BigDecimal constant) {
            discount.setConstant(constant);
            return this;
        }

        @Override
        public SimpleConstantReceiptItemDiscount build() {
            throwIfInvalid();
            return discount;
        }

        private void throwIfInvalid() {
            if (discount.getDescription() == null)
                throw new IllegalArgumentException("Description required to build SimpleConstantReceiptItemDiscount");
            if (discount.getConstant() == null)
                throw new IllegalArgumentException("Constant required to build SimpleConstantReceiptItemDiscount");
        }
    }
}
