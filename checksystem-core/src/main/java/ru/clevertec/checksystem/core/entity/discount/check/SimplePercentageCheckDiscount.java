package ru.clevertec.checksystem.core.entity.discount.check;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import ru.clevertec.checksystem.core.Constants;
import ru.clevertec.checksystem.core.common.builder.discount.check.IPercentageCheckDiscountBuilder;
import ru.clevertec.checksystem.core.entity.check.Check;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Collection;

@Entity
@Table(name = Constants.Entities.Mapping.Table.SIMPLE_PERCENTAGE_CHECK_DISCOUNT)
@DiscriminatorValue("SimplePercentageCheckDiscount")
public final class SimplePercentageCheckDiscount extends PercentageCheckDiscount {

    @JsonCreator(mode = JsonCreator.Mode.DISABLED)
    public SimplePercentageCheckDiscount() {
    }

    @JsonCreator(mode = JsonCreator.Mode.DISABLED)
    public SimplePercentageCheckDiscount(String description, Double percent) {
        super(description, percent);
    }

    @JsonCreator(mode = JsonCreator.Mode.DISABLED)
    public SimplePercentageCheckDiscount(String description, Double percent, CheckDiscount dependentDiscount) {
        super(description, percent, dependentDiscount);
    }

    @JsonCreator
    public SimplePercentageCheckDiscount(
            @JsonProperty("id") Long id,
            @JsonProperty("description") String description,
            @JsonProperty("percent") Double percent,
            @JsonProperty("dependentDiscount") CheckDiscount dependentDiscount) {
        super(description, percent, dependentDiscount);
        setId(id);
    }

    public static class Builder implements IPercentageCheckDiscountBuilder {

        private final SimplePercentageCheckDiscount discount = new SimplePercentageCheckDiscount();

        @Override
        public IPercentageCheckDiscountBuilder setId(Long id)
                throws IllegalArgumentException {
            discount.setId(id);
            return this;
        }

        @Override
        public IPercentageCheckDiscountBuilder setDescription(String description) {
            discount.setDescription(description);
            return this;
        }

        @Override
        public IPercentageCheckDiscountBuilder setDependentDiscount(CheckDiscount discount) {
            this.discount.setDependentDiscount(discount);
            return this;
        }

        @Override
        public IPercentageCheckDiscountBuilder setChecks(Collection<Check> checks) {
            discount.setChecks(checks);
            return this;
        }

        @Override
        public IPercentageCheckDiscountBuilder setPercent(Double percent) {
            discount.setPercent(percent);
            return this;
        }

        @Override
        public SimplePercentageCheckDiscount build() {
            throwIfInvalid();
            return discount;
        }

        private void throwIfInvalid() {
            if (discount.getDescription() == null)
                throw new IllegalArgumentException("Description is required");
        }
    }
}
