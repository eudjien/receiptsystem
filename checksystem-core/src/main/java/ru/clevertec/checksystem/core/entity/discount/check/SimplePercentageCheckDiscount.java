package ru.clevertec.checksystem.core.entity.discount.check;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import ru.clevertec.checksystem.core.entity.check.Check;
import ru.clevertec.checksystem.core.common.builder.discount.check.IPercentageCheckDiscountBuilder;

public final class SimplePercentageCheckDiscount extends PercentageCheckDiscount {

    private SimplePercentageCheckDiscount()
            throws IllegalArgumentException {
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
            @JsonProperty("childDiscount") CheckDiscount childDiscount)
            throws IllegalArgumentException {
        super(id, description, percent, childDiscount);
    }

    public static class Builder implements IPercentageCheckDiscountBuilder {

        private final SimplePercentageCheckDiscount simplePercentageCheckDiscount;

        public Builder() {
            this.simplePercentageCheckDiscount = new SimplePercentageCheckDiscount();
        }

        @Override
        public IPercentageCheckDiscountBuilder setId(int id)
                throws IllegalArgumentException {
            simplePercentageCheckDiscount.setId(id);
            return this;
        }

        @Override
        public IPercentageCheckDiscountBuilder setDescription(String description)
                throws IllegalArgumentException {
            simplePercentageCheckDiscount.setDescription(description);
            return this;
        }

        @Override
        public IPercentageCheckDiscountBuilder setChildDiscount(CheckDiscount discount)
                throws IllegalArgumentException {
            this.simplePercentageCheckDiscount.setChildDiscount(discount);
            return this;
        }

        @Override
        public IPercentageCheckDiscountBuilder setCheck(Check check) {
            simplePercentageCheckDiscount.setCheck(check);
            return this;
        }

        @Override
        public IPercentageCheckDiscountBuilder setPercent(double percent) {
            simplePercentageCheckDiscount.setPercent(percent);
            return this;
        }

        @Override
        public SimplePercentageCheckDiscount build() throws IllegalArgumentException {
            throwIfInvalid();
            return simplePercentageCheckDiscount;
        }

        private void throwIfInvalid() throws IllegalArgumentException {
            if (simplePercentageCheckDiscount.getDescription() == null) {
                throw new IllegalArgumentException("Description is required");
            }
        }
    }
}
