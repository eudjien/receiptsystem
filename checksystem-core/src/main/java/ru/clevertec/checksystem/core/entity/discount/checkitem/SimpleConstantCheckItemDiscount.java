package ru.clevertec.checksystem.core.entity.discount.checkitem;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import ru.clevertec.checksystem.core.common.builder.discount.checkitem.IConstantCheckItemDiscountBuilder;
import ru.clevertec.checksystem.core.entity.check.CheckItem;

import java.math.BigDecimal;

public final class SimpleConstantCheckItemDiscount extends ConstantCheckItemDiscount {

    public SimpleConstantCheckItemDiscount() {
    }

    public SimpleConstantCheckItemDiscount(String description, BigDecimal constant) {
        super(description, constant);
    }

    public SimpleConstantCheckItemDiscount(int id, String description, BigDecimal constant) {
        super(id, description, constant);
    }

    @JsonCreator
    public SimpleConstantCheckItemDiscount(
            @JsonProperty("id") int id,
            @JsonProperty("description") String description,
            @JsonProperty("constant") BigDecimal constant,
            @JsonProperty("dependentDiscount") CheckItemDiscount dependentDiscount) {
        super(id, description, constant, dependentDiscount);
    }

    public static class Builder implements IConstantCheckItemDiscountBuilder {

        private final SimpleConstantCheckItemDiscount discount = new SimpleConstantCheckItemDiscount();

        @Override
        public IConstantCheckItemDiscountBuilder setId(int id) {
            discount.setId(id);
            return this;
        }

        @Override
        public IConstantCheckItemDiscountBuilder setDescription(String description) {
            discount.setDescription(description);
            return this;
        }

        @Override
        public IConstantCheckItemDiscountBuilder setDependentDiscount(CheckItemDiscount checkItemDiscount) {
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
        public SimpleConstantCheckItemDiscount build() {
            throwIfInvalid();
            return discount;
        }

        private void throwIfInvalid() {
            if (discount.getDescription() == null) {
                throw new IllegalArgumentException("Description required to build SimpleConstantCheckItemDiscount");
            }
            if (discount.getConstant() == null) {
                throw new IllegalArgumentException("Constant required to build SimpleConstantCheckItemDiscount");
            }
        }
    }
}
