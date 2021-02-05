package ru.clevertec.checksystem.core.entity.discount.checkitem;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import ru.clevertec.checksystem.core.Constants;
import ru.clevertec.checksystem.core.common.builder.discount.checkitem.ISimplePercentageCheckItemDiscountBuilder;
import ru.clevertec.checksystem.core.entity.check.CheckItem;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Collection;

@Entity
@Table(name = Constants.Entities.Mapping.Table.SIMPLE_PERCENTAGE_CHECK_ITEM_DISCOUNT)
@DiscriminatorValue(value = "SimplePercentageCheckItemDiscount")
public final class SimplePercentageCheckItemDiscount extends PercentageCheckItemDiscount {

    @JsonCreator(mode = JsonCreator.Mode.DISABLED)
    public SimplePercentageCheckItemDiscount() {
    }

    @JsonCreator(mode = JsonCreator.Mode.DISABLED)
    public SimplePercentageCheckItemDiscount(String description, Double percent) {
        super(description, percent);
    }

    @JsonCreator(mode = JsonCreator.Mode.DISABLED)
    public SimplePercentageCheckItemDiscount(String description, Double percent, CheckItemDiscount dependentDiscount) {
        super(description, percent, dependentDiscount);
    }

    @JsonCreator
    public SimplePercentageCheckItemDiscount(
            @JsonProperty("id") Long id,
            @JsonProperty("description") String description,
            @JsonProperty("percent") double percent,
            @JsonProperty("dependentDiscount") CheckItemDiscount dependentDiscount) {
        super(description, percent, dependentDiscount);
        setId(id);
    }

    public static class BuilderSimple implements ISimplePercentageCheckItemDiscountBuilder {

        private final SimplePercentageCheckItemDiscount discount = new SimplePercentageCheckItemDiscount();

        @Override
        public ISimplePercentageCheckItemDiscountBuilder setId(Long id) {
            discount.setId(id);
            return this;
        }

        @Override
        public ISimplePercentageCheckItemDiscountBuilder setDescription(String description) {
            discount.setDescription(description);
            return this;
        }

        @Override
        public ISimplePercentageCheckItemDiscountBuilder setDependentDiscount(CheckItemDiscount checkItemDiscount) {
            discount.setDependentDiscount(checkItemDiscount);
            return this;
        }

        @Override
        public ISimplePercentageCheckItemDiscountBuilder setCheckItems(Collection<CheckItem> checkItems) {
            discount.setCheckItems(checkItems);
            return this;
        }

        @Override
        public ISimplePercentageCheckItemDiscountBuilder setPercent(Double percent) {
            discount.setPercent(percent);
            return this;
        }

        @Override
        public SimplePercentageCheckItemDiscount build() {
            throwIfInvalid();
            return discount;
        }

        private void throwIfInvalid() {
            if (discount.getDescription() == null)
                throw new IllegalArgumentException("Description required to build SimplePercentageCheckItemDiscount");
        }
    }
}
