package ru.clevertec.checksystem.core.entity.discount.check.item;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import ru.clevertec.checksystem.core.entity.check.CheckItem;
import ru.clevertec.checksystem.core.common.builder.discount.check.item.ISimplePercentageCheckItemDiscountBuilder;

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
            @JsonProperty("childDiscount") CheckItemDiscount checkItemDiscount)
            throws IllegalArgumentException {
        super(id, description, percent, checkItemDiscount);
    }

    public static class BuilderSimple implements ISimplePercentageCheckItemDiscountBuilder {

        private final SimplePercentageCheckItemDiscount simplePercentageCheckItemDiscount
                = new SimplePercentageCheckItemDiscount();

        @Override
        public ISimplePercentageCheckItemDiscountBuilder setId(int id)
                throws IllegalArgumentException {
            simplePercentageCheckItemDiscount.setId(id);
            return this;
        }

        @Override
        public ISimplePercentageCheckItemDiscountBuilder setDescription(String description)
                throws IllegalArgumentException {
            simplePercentageCheckItemDiscount.setDescription(description);
            return this;
        }

        @Override
        public ISimplePercentageCheckItemDiscountBuilder setChildDiscount(CheckItemDiscount checkItemDiscount)
                throws IllegalArgumentException {
            this.simplePercentageCheckItemDiscount.setChildDiscount(checkItemDiscount);
            return this;
        }

        @Override
        public ISimplePercentageCheckItemDiscountBuilder setCheckItem(CheckItem checkItem) {
            simplePercentageCheckItemDiscount.setCheckItem(checkItem);
            return this;
        }

        @Override
        public ISimplePercentageCheckItemDiscountBuilder setPercent(double percent) {
            simplePercentageCheckItemDiscount.setPercent(percent);
            return this;
        }

        @Override
        public SimplePercentageCheckItemDiscount build() throws IllegalArgumentException {
            throwIfInvalid();
            return simplePercentageCheckItemDiscount;
        }

        private void throwIfInvalid() throws IllegalArgumentException {
            if (simplePercentageCheckItemDiscount.getDescription() == null) {
                throw new IllegalArgumentException(
                        "Description required to build SimplePercentageCheckItemDiscount");
            }
        }
    }
}
