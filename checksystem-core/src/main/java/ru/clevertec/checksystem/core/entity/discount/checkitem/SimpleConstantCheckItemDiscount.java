package ru.clevertec.checksystem.core.entity.discount.checkitem;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import ru.clevertec.checksystem.core.Constants;
import ru.clevertec.checksystem.core.common.builder.discount.checkitem.IConstantCheckItemDiscountBuilder;
import ru.clevertec.checksystem.core.entity.check.CheckItem;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Collection;

@Entity
@Table(name = Constants.Entities.Mapping.Table.SIMPLE_CONSTANT_CHECK_ITEM_DISCOUNT)
@DiscriminatorValue("SimpleConstantCheckItemDiscount")
public final class SimpleConstantCheckItemDiscount extends ConstantCheckItemDiscount {

    @JsonCreator(mode = JsonCreator.Mode.DISABLED)
    public SimpleConstantCheckItemDiscount() {
    }

    @JsonCreator(mode = JsonCreator.Mode.DISABLED)
    public SimpleConstantCheckItemDiscount(String description, BigDecimal constant) {
        super(description, constant);
    }

    @JsonCreator(mode = JsonCreator.Mode.DISABLED)
    public SimpleConstantCheckItemDiscount(String description, BigDecimal constant, CheckItemDiscount dependentDiscount) {
        super(description, constant, dependentDiscount);
    }

    @JsonCreator
    public SimpleConstantCheckItemDiscount(
            @JsonProperty("id") Long id,
            @JsonProperty("description") String description,
            @JsonProperty("constant") BigDecimal constant,
            @JsonProperty("dependentDiscount") CheckItemDiscount dependentDiscount) {
        super(description, constant, dependentDiscount);
        setId(id);
    }

    public static class Builder implements IConstantCheckItemDiscountBuilder {

        private final SimpleConstantCheckItemDiscount discount = new SimpleConstantCheckItemDiscount();

        @Override
        public IConstantCheckItemDiscountBuilder setId(Long id) {
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
            discount.setDependentDiscount(checkItemDiscount);
            return this;
        }

        @Override
        public IConstantCheckItemDiscountBuilder setCheckItems(Collection<CheckItem> checkItems) {
            discount.setCheckItems(checkItems);
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
            if (discount.getDescription() == null)
                throw new IllegalArgumentException("Description required to build SimpleConstantCheckItemDiscount");
            if (discount.getConstant() == null)
                throw new IllegalArgumentException("Constant required to build SimpleConstantCheckItemDiscount");
        }
    }
}
