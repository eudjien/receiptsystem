package ru.clevertec.checksystem.core.entity.discount.check;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import ru.clevertec.checksystem.core.common.builder.discount.check.IConstantCheckDiscountBuilder;
import ru.clevertec.checksystem.core.entity.check.Check;

import java.math.BigDecimal;

public final class SimpleConstantCheckDiscount extends ConstantCheckDiscount {

    protected SimpleConstantCheckDiscount() {
        super();
    }

    public SimpleConstantCheckDiscount(String description, BigDecimal constant)
            throws IllegalArgumentException {
        super(description, constant);
    }

    public SimpleConstantCheckDiscount(int id, String description, BigDecimal constant)
            throws IllegalArgumentException {
        super(id, description, constant);
    }

    @JsonCreator
    public SimpleConstantCheckDiscount(
            @JsonProperty("id") int id,
            @JsonProperty("description") String description,
            @JsonProperty("constant") BigDecimal constant,
            @JsonProperty("dependentDiscount") CheckDiscount dependentDiscount)
            throws IllegalArgumentException {
        super(id, description, constant, dependentDiscount);
    }

    public static class Builder implements IConstantCheckDiscountBuilder {

        private final SimpleConstantCheckDiscount simpleConstantCheckDiscount = new SimpleConstantCheckDiscount();

        @Override
        public IConstantCheckDiscountBuilder setId(int id)
                throws IllegalArgumentException {
            simpleConstantCheckDiscount.setId(id);
            return this;
        }

        @Override
        public IConstantCheckDiscountBuilder setDescription(String description)
                throws IllegalArgumentException {
            simpleConstantCheckDiscount.setDescription(description);
            return this;
        }

        @Override
        public IConstantCheckDiscountBuilder setDependentDiscount(CheckDiscount discount)
                throws IllegalArgumentException {
            this.simpleConstantCheckDiscount.setDependentDiscount(discount);
            return this;
        }

        @Override
        public IConstantCheckDiscountBuilder setCheck(Check check) {
            simpleConstantCheckDiscount.setCheck(check);
            return this;
        }

        @Override
        public IConstantCheckDiscountBuilder setConstant(BigDecimal constant) {
            simpleConstantCheckDiscount.setConstant(constant);
            return this;
        }

        @Override
        public SimpleConstantCheckDiscount build() throws IllegalArgumentException {
            throwIfInvalid();
            return simpleConstantCheckDiscount;
        }

        private void throwIfInvalid() throws IllegalArgumentException {
            if (simpleConstantCheckDiscount.getDescription() == null) {
                throw new IllegalArgumentException("Description required to build SimpleConstantCheckDiscount");
            }
            if (simpleConstantCheckDiscount.getConstant() == null) {
                throw new IllegalArgumentException("Constant required to build SimpleConstantCheckDiscount");
            }
        }
    }
}
