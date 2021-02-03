package ru.clevertec.checksystem.core.entity.discount.checkitem;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import ru.clevertec.checksystem.core.common.builder.discount.check.item.ISimplePercentageCheckItemDiscountBuilder;
import ru.clevertec.checksystem.core.entity.check.CheckItem;
import ru.clevertec.checksystem.core.exception.ArgumentNullException;

public final class SimplePercentageCheckItemDiscount extends PercentageCheckItemDiscount {

    private SimplePercentageCheckItemDiscount() {
    }

    public SimplePercentageCheckItemDiscount(String description, double percent)
            throws IllegalArgumentException {
        super(description, percent);
    }

    public SimplePercentageCheckItemDiscount(int id, String description, double percent)
            throws IllegalArgumentException {
        super(id, description, percent);
    }

    @JsonCreator
    public SimplePercentageCheckItemDiscount(
            @JsonProperty("id") int id,
            @JsonProperty("description") String description,
            @JsonProperty("percent") double percent,
            @JsonProperty("dependentDiscount") CheckItemDiscount dependentDiscount)
            throws IllegalArgumentException {
        super(id, description, percent, dependentDiscount);
    }

    public static class BuilderSimple implements ISimplePercentageCheckItemDiscountBuilder {

        private final SimplePercentageCheckItemDiscount discount = new SimplePercentageCheckItemDiscount();

        @Override
        public ISimplePercentageCheckItemDiscountBuilder setId(int id)
                throws IllegalArgumentException {
            discount.setId(id);
            return this;
        }

        @Override
        public ISimplePercentageCheckItemDiscountBuilder setDescription(String description) throws ArgumentNullException {
            discount.setDescription(description);
            return this;
        }

        @Override
        public ISimplePercentageCheckItemDiscountBuilder setDependentDiscount(CheckItemDiscount discount) {
            this.discount.setDependentDiscount(discount);
            return this;
        }

        @Override
        public ISimplePercentageCheckItemDiscountBuilder setCheckItem(CheckItem checkItem) throws ArgumentNullException {
            discount.setCheckItem(checkItem);
            return this;
        }

        @Override
        public ISimplePercentageCheckItemDiscountBuilder setPercent(double percent) throws ArgumentNullException {
            discount.setPercent(percent);
            return this;
        }

        @Override
        public SimplePercentageCheckItemDiscount build() throws IllegalArgumentException {
            throwIfInvalid();
            return discount;
        }

        private void throwIfInvalid() throws IllegalArgumentException {
            if (discount.getDescription() == null) {
                throw new IllegalArgumentException(
                        "Description required to build SimplePercentageCheckItemDiscount");
            }
        }
    }
}
