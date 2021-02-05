package ru.clevertec.checksystem.core.entity.discount.check;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import ru.clevertec.checksystem.core.Constants;
import ru.clevertec.checksystem.core.common.builder.discount.check.IConstantCheckDiscountBuilder;
import ru.clevertec.checksystem.core.entity.check.Check;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Collection;

@Entity
@Table(name = Constants.Entities.Mapping.Table.SIMPLE_CONSTANT_CHECK_DISCOUNT)
@DiscriminatorValue("SimpleConstantCheckDiscount")
public final class SimpleConstantCheckDiscount extends ConstantCheckDiscount {

    @JsonCreator(mode = JsonCreator.Mode.DISABLED)
    public SimpleConstantCheckDiscount() {
    }

    @JsonCreator(mode = JsonCreator.Mode.DISABLED)
    public SimpleConstantCheckDiscount(String description, BigDecimal constant) {
        super(description, constant);
    }

    @JsonCreator(mode = JsonCreator.Mode.DISABLED)
    public SimpleConstantCheckDiscount(String description, BigDecimal constant, CheckDiscount dependentDiscount) {
        super(description, constant, dependentDiscount);
    }

    @JsonCreator
    public SimpleConstantCheckDiscount(
            @JsonProperty("id") Long id,
            @JsonProperty("description") String description,
            @JsonProperty("constant") BigDecimal constant,
            @JsonProperty("dependentDiscount") CheckDiscount dependentDiscount) {
        super(description, constant, dependentDiscount);
        setId(id);
    }

    public static class Builder implements IConstantCheckDiscountBuilder {

        private final SimpleConstantCheckDiscount discount = new SimpleConstantCheckDiscount();

        @Override
        public IConstantCheckDiscountBuilder setId(Long id) {
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
        public IConstantCheckDiscountBuilder setChecks(Collection<Check> checks) {
            discount.setChecks(checks);
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
            if (discount.getDescription() == null)
                throw new IllegalArgumentException("Description required to build SimpleConstantCheckDiscount");
            if (discount.getConstant() == null)
                throw new IllegalArgumentException("Constant required to build SimpleConstantCheckDiscount");
        }
    }
}
