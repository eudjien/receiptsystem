package ru.clevertec.checksystem.core.entity.discount.check;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import ru.clevertec.checksystem.core.common.check.ICheckComposable;
import ru.clevertec.checksystem.core.entity.check.Check;
import ru.clevertec.checksystem.core.entity.discount.Discount;
import ru.clevertec.checksystem.core.util.ThrowUtils;
import ru.clevertec.customlib.json.StringifyIgnore;

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS)
public abstract class CheckDiscount extends Discount<CheckDiscount> implements ICheckComposable {

    @JsonIgnore
    private Check check;

    protected CheckDiscount() {
    }

    public CheckDiscount(String description) {
        super(description);
    }

    public CheckDiscount(int id, String description) {
        super(id, description);
    }

    public CheckDiscount(int id, String description, CheckDiscount dependentDiscount) {
        super(id, description, dependentDiscount);
    }

    @StringifyIgnore
    public Check getCheck() {
        return check;
    }

    public void setCheck(Check check) {

        ThrowUtils.Argument.nullValue("check", check);

        this.check = check;
        if (getDependentDiscount() != null) {
            getDependentDiscount().setCheck(check);
        }
    }
}
