package ru.clevertec.checksystem.core.entity.discount.check.item;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import ru.clevertec.checksystem.core.common.builder.discount.check.item.IConstantCheckItemDiscountBuilder;
import ru.clevertec.checksystem.core.entity.check.CheckItem;

import java.math.BigDecimal;

public final class SimpleConstantCheckItemDiscount extends ConstantCheckItemDiscount {

    private SimpleConstantCheckItemDiscount() {
    }

    public SimpleConstantCheckItemDiscount(String description, BigDecimal constant)
            throws IllegalArgumentException {
        super(description, constant);
    }

    public SimpleConstantCheckItemDiscount(int id, String description, BigDecimal constant)
            throws IllegalArgumentException {
        super(id, description, constant);
    }

    @JsonCreator
    public SimpleConstantCheckItemDiscount(
            @JsonProperty("id") int id,
            @JsonProperty("description") String description,
            @JsonProperty("constant") BigDecimal constant,
            @JsonProperty("dependentDiscount") CheckItemDiscount dependentDiscount)
            throws IllegalArgumentException {
        super(id, description, constant, dependentDiscount);
    }

    public static class Builder implements IConstantCheckItemDiscountBuilder {

        private final SimpleConstantCheckItemDiscount discount = new SimpleConstantCheckItemDiscount();

        @Override
        public IConstantCheckItemDiscountBuilder setId(int id)
                throws IllegalArgumentException {
            discount.setId(id);
            return this;
        }

        @Override
        public IConstantCheckItemDiscountBuilder setDescription(String description)
                throws IllegalArgumentException {
            discount.setDescription(description);
            return this;
        }

        @Override
        public IConstantCheckItemDiscountBuilder setDependentDiscount(CheckItemDiscount checkItemDiscount)
                throws IllegalArgumentException {
            this.discount.setDependentDiscount(checkItemDiscount);
            return this;
        }

        @Override
        public IConstantCheckItemDiscountBuilder setCheckItem(CheckItem checkItem) {
            discount.setCheckItem(checkItem);
            return this;
        }

        @Override
        public IConstantCheckItemDiscountBuilder setConstant(BigDecimal constant) {
            discount.setConstant(constant);
            return this;
        }

        @Override
        public SimpleConstantCheckItemDiscount build() throws IllegalArgumentException {
            throwIfInvalid();
            return discount;
        }

        private void throwIfInvalid() throws IllegalArgumentException {
            if (discount.getDescription() == null) {
                throw new IllegalArgumentException("Description required to build SimpleConstantCheckItemDiscount");
            }
            if (discount.getConstant() == null) {
                throw new IllegalArgumentException("Constant required to build SimpleConstantCheckItemDiscount");
            }
        }
    }
}
