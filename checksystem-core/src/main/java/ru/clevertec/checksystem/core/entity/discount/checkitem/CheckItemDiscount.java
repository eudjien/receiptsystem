package ru.clevertec.checksystem.core.entity.discount.checkitem;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import ru.clevertec.checksystem.core.Constants;
import ru.clevertec.checksystem.core.common.check.ICheckItemAggregable;
import ru.clevertec.checksystem.core.entity.check.CheckItem;
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
@Table(name = Constants.Entities.Mapping.Table.CHECK_ITEM_DISCOUNTS)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING, length = 86)
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS)
public abstract class CheckItemDiscount extends AbstractDiscount<CheckItemDiscount> implements ICheckItemAggregable {

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "discounts")
    @JsonIgnore
    private final Set<CheckItem> checkItems = new HashSet<>();

    protected CheckItemDiscount() {
    }

    protected CheckItemDiscount(String description) {
        super(description);
    }

    protected CheckItemDiscount(String description, CheckItemDiscount dependentDiscount) {
        super(description, dependentDiscount);
    }

    @StringifyIgnore
    @Override
    public Collection<CheckItem> getCheckItems() {
        return checkItems;
    }

    @Override
    public void setCheckItems(Collection<CheckItem> checkItems) {
        clearCheckItems();
        if (checkItems != null)
            addCheckItems(checkItems);
    }

    @Override
    public void addCheckItem(CheckItem checkItem) {
        ThrowUtils.Argument.nullValue("checkItem", checkItem);
        checkItem.getDiscounts().add(this);
        getCheckItems().add(checkItem);
    }

    @Override
    public void addCheckItems(Collection<CheckItem> checkItems) {
        ThrowUtils.Argument.nullValue("checkItems", checkItems);
        checkItems.forEach(this::addCheckItem);
    }

    @Override
    public void removeCheckItem(CheckItem checkItem) {
        ThrowUtils.Argument.nullValue("checkItem", checkItem);
        checkItem.getDiscounts().remove(this);
        getCheckItems().remove(checkItem);
    }

    @Override
    public void removeCheckItems(Collection<CheckItem> checkItems) {
        ThrowUtils.Argument.nullValue("checkItems", checkItems);
        checkItems.forEach(this::removeCheckItem);
    }

    @Override
    public void clearCheckItems() {
        getCheckItems().forEach(check -> check.getDiscounts().remove(this));
        getCheckItems().clear();
    }

    public abstract BigDecimal discountAmount(CheckItem checkItem);
}
