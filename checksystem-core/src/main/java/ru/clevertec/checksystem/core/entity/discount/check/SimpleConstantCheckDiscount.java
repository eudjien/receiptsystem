package ru.clevertec.checksystem.core.entity.discount.check;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import ru.clevertec.checksystem.core.common.builder.discount.check.IConstantCheckDiscountBuilder;
import ru.clevertec.checksystem.core.entity.check.Check;

import java.math.BigDecimal;

public final class SimpleConstantCheckDiscount extends ConstantCheckDiscount {

    public SimpleConstantCheckDiscount() {
    }

    public SimpleConstantCheckDiscount(String description, BigDecimal constant) {
        super(description, constant);
    }

    public SimpleConstantCheckDiscount(int id, String description, BigDecimal constant) {
        super(id, description, constant);
    }

    @JsonCreator
    public SimpleConstantCheckDiscount(
            @JsonProperty("id") int id,
            @JsonProperty("description") String description,
            @JsonProperty("constant") BigDecimal constant,
            @JsonProperty("dependentDiscount") CheckDiscount dependentDiscount) {
        super(id, description, constant, dependentDiscount);
    }

    public static class Builder implements IConstantCheckDiscountBuilder {

        private final SimpleConstantCheckDiscount discount = new SimpleConstantCheckDiscount();

        @Override
        public IConstantCheckDiscountBuilder setId(int id) {
            discount.setId(id);
            return this;
        }

        @Override
        public IConstantCheckDiscountBuilder setDescription(String description) {
            discount.setDescription(description);
            return this;
        }

        @Override
        public IConstantCheckDiscountBuilder setDependentDiscount(CheckDiscount discount) {
            this.discount.setDependentDiscount(discount);
            return this;
        }

        @Override
        public IConstantCheckDiscountBuilder setCheck(Check check) {
            discount.setCheck(check);
            return this;
        }

        @Override
        public IConstantCheckDiscountBuilder setConstant(BigDecimal constant) {
            discount.setConstant(constant);
            return this;
        }

        @Override
        public SimpleConstantCheckDiscount build() {
            throwIfInvalid();
            return discount;
        }

        private void throwIfInvalid() {
            if (discount.getDescription() == null) {
                throw new IllegalArgumentException("Description required to build SimpleConstantCheckDiscount");
            }
            if (discount.getConstant() == null) {
                throw new IllegalArgumentException("Constant required to build SimpleConstantCheckDiscount");
            }
        }
    }
}
