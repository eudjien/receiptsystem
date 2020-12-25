package ru.clevertec.checksystem.core.discount.item.base;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import ru.clevertec.checksystem.core.check.CheckItem;
import ru.clevertec.checksystem.core.discount.Discount;

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS)
public abstract class CheckItemDiscount extends Discount<CheckItemDiscount> {

    @JsonIgnore
    private CheckItem checkItem;

    public CheckItemDiscount(String description) throws Exception {
        super(description);
    }

    public CheckItemDiscount(int id, String description) throws Exception {
        super(id, description);
    }

    public CheckItemDiscount(int id, String description, CheckItemDiscount childDiscount) throws Exception {
        super(id, description, childDiscount);
    }

    public CheckItem getCheckItem() {
        return checkItem;
    }

    public void setCheckItem(CheckItem checkItem) throws Exception {
        if (checkItem == null) {
            throw new Exception("Check item cannot be null");
        }
        this.checkItem = checkItem;
        if (getChildDiscount() != null) {
            getChildDiscount().setCheckItem(checkItem);
        }
    }

}
