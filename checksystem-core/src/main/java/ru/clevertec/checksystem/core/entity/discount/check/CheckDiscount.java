package ru.clevertec.checksystem.core.entity.discount.check;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import ru.clevertec.checksystem.core.Constants;
import ru.clevertec.checksystem.core.common.check.ICheckAggregable;
import ru.clevertec.checksystem.core.entity.check.Check;
import ru.clevertec.checksystem.core.entity.discount.AbstractDiscount;
import ru.clevertec.checksystem.core.util.ThrowUtils;
import ru.clevertec.custom.json.StringifyIgnore;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = Constants.Entities.Mapping.Table.CHECK_DISCOUNTS)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING, length = 124)
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS)
public abstract class CheckDiscount extends AbstractDiscount<CheckDiscount> implements ICheckAggregable {

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "discounts")
    private final Set<Check> checks = new HashSet<>();

    protected CheckDiscount() {
    }

    protected CheckDiscount(String description) {
        super(description);
    }

    protected CheckDiscount(String description, CheckDiscount dependentDiscount) {
        super(description, dependentDiscount);
    }

    @StringifyIgnore
    @Override
    public Collection<Check> getChecks() {
        return checks;
    }

    @Override
    public void setChecks(Collection<Check> checks) {
        clearChecks();
        if (checks != null)
            addChecks(checks);
    }

    @Override
    public void addCheck(Check check) {
        ThrowUtils.Argument.nullValue("check", check);
        check.getDiscounts().add(this);
        getChecks().add(check);
    }

    @Override
    public void addChecks(Collection<Check> checks) {
        ThrowUtils.Argument.nullValue("checks", checks);
        checks.forEach(this::addCheck);
    }

    @Override
    public void removeCheck(Check check) {
        ThrowUtils.Argument.nullValue("check", check);
        check.getDiscounts().remove(this);
        getChecks().remove(check);
    }

    @Override
    public void removeChecks(Collection<Check> checks) {
        ThrowUtils.Argument.nullValue("checks", checks);
        checks.forEach(this::removeCheck);
    }

    @Override
    public void clearChecks() {
        getChecks().forEach(check -> check.getDiscounts().remove(this));
        getChecks().clear();
    }

    public abstract BigDecimal discountAmount(Check check);
}
