package ru.clevertec.checksystem.core.entity.discount.check;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import ru.clevertec.checksystem.core.common.builder.discount.check.IPercentageCheckDiscountBuilder;
import ru.clevertec.checksystem.core.entity.check.Check;
import ru.clevertec.checksystem.core.exception.ArgumentNullException;
import ru.clevertec.checksystem.core.exception.ArgumentOutOfRangeException;

public final class SimplePercentageCheckDiscount extends PercentageCheckDiscount {

    private SimplePercentageCheckDiscount() throws IllegalArgumentException {
    }

    public SimplePercentageCheckDiscount(String description, double percent)
            throws IllegalArgumentException {
        super(description, percent);
    }

    public SimplePercentageCheckDiscount(int id, String description, double percent)
            throws IllegalArgumentException {
        super(id, description, percent);
    }

    @JsonCreator
    public SimplePercentageCheckDiscount(
            @JsonProperty("id") int id,
            @JsonProperty("description") String description,
            @JsonProperty("percent") double percent,
            @JsonProperty("dependentDiscount") CheckDiscount dependentDiscount)
            throws IllegalArgumentException {
        super(id, description, percent, dependentDiscount);
    }

    public static class Builder implements IPercentageCheckDiscountBuilder {

        private final SimplePercentageCheckDiscount discount = new SimplePercentageCheckDiscount();

        @Override
        public IPercentageCheckDiscountBuilder setId(int id)
                throws IllegalArgumentException {
            discount.setId(id);
            return this;
        }

        @Override
        public IPercentageCheckDiscountBuilder setDescription(String description) throws ArgumentNullException {
            discount.setDescription(description);
            return this;
        }

        @Override
        public IPercentageCheckDiscountBuilder setDependentDiscount(CheckDiscount discount) {
            this.discount.setDependentDiscount(discount);
            return this;
        }

        @Override
        public IPercentageCheckDiscountBuilder setCheck(Check check) throws ArgumentNullException {
            discount.setCheck(check);
            return this;
        }

        @Override
        public IPercentageCheckDiscountBuilder setPercent(double percent) throws ArgumentOutOfRangeException {
            discount.setPercent(percent);
            return this;
        }

        @Override
        public SimplePercentageCheckDiscount build() throws IllegalArgumentException {
            throwIfInvalid();
            return discount;
        }

        private void throwIfInvalid() throws IllegalArgumentException {
            if (discount.getDescription() == null) {
                throw new IllegalArgumentException("Description is required");
            }
        }
    }
}
