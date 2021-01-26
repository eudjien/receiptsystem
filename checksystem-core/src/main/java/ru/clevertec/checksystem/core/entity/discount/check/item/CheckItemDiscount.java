package ru.clevertec.checksystem.core.entity.discount.check.item;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import ru.clevertec.checksystem.core.entity.check.CheckItem;
import ru.clevertec.checksystem.core.common.check.ICheckItemComposable;
import ru.clevertec.checksystem.core.entity.discount.Discount;
import ru.clevertec.normalino.json.NormalinoIgnore;

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS)
public abstract class CheckItemDiscount extends Discount<CheckItemDiscount> implements ICheckItemComposable {

    @JsonIgnore
    private CheckItem checkItem;

    protected CheckItemDiscount() {
    }

    public CheckItemDiscount(String description) throws IllegalArgumentException {
        super(description);
    }

    public CheckItemDiscount(int id, String description) throws IllegalArgumentException {
        super(id, description);
    }

    public CheckItemDiscount(int id, String description, CheckItemDiscount childDiscount)
            throws IllegalArgumentException {
        super(id, description, childDiscount);
    }

    @NormalinoIgnore
    public CheckItem getCheckItem() {
        return checkItem;
    }

    public void setCheckItem(CheckItem checkItem) throws IllegalArgumentException {
        if (checkItem == null) {
            throw new IllegalArgumentException("Check item cannot be null");
        }
        this.checkItem = checkItem;
        if (getChildDiscount() != null) {
            getChildDiscount().setCheckItem(checkItem);
        }
    }
}
