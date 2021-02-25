package ru.clevertec.checksystem.core.entity.discount.checkitem;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import ru.clevertec.checksystem.core.common.builder.discount.checkitem.ISimplePercentageCheckItemDiscountBuilder;
import ru.clevertec.checksystem.core.entity.check.CheckItem;

public final class SimplePercentageCheckItemDiscount extends PercentageCheckItemDiscount {

    public SimplePercentageCheckItemDiscount() {
    }

    public SimplePercentageCheckItemDiscount(String description, double percent) {
        super(description, percent);
    }

    public SimplePercentageCheckItemDiscount(int id, String description, double percent) {
        super(id, description, percent);
    }

    @JsonCreator
    public SimplePercentageCheckItemDiscount(
            @JsonProperty("id") int id,
            @JsonProperty("description") String description,
            @JsonProperty("percent") double percent,
            @JsonProperty("dependentDiscount") CheckItemDiscount dependentDiscount) {
        super(id, description, percent, dependentDiscount);
    }

    public static class BuilderSimple implements ISimplePercentageCheckItemDiscountBuilder {

        private final SimplePercentageCheckItemDiscount discount = new SimplePercentageCheckItemDiscount();

        @Override
        public ISimplePercentageCheckItemDiscountBuilder setId(int id) {
            discount.setId(id);
            return this;
        }

        @Override
        public ISimplePercentageCheckItemDiscountBuilder setDescription(String description) {
            discount.setDescription(description);
            return this;
        }

        @Override
        public ISimplePercentageCheckItemDiscountBuilder setDependentDiscount(CheckItemDiscount discount) {
            this.discount.setDependentDiscount(discount);
            return this;
        }

        @Override
        public ISimplePercentageCheckItemDiscountBuilder setCheckItem(CheckItem checkItem) {
            discount.setCheckItem(checkItem);
            return this;
        }

        @Override
        public ISimplePercentageCheckItemDiscountBuilder setPercent(double percent) {
            discount.setPercent(percent);
            return this;
        }

        @Override
        public SimplePercentageCheckItemDiscount build() {
            throwIfInvalid();
            return discount;
        }

        private void throwIfInvalid() {
            if (discount.getDescription() == null) {
                throw new IllegalArgumentException(
                        "Description required to build SimplePercentageCheckItemDiscount");
            }
        }
    }
}
