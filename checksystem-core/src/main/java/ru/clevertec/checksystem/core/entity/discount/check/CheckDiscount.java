package ru.clevertec.checksystem.core.entity.discount.check;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import ru.clevertec.checksystem.core.common.check.ICheckComposable;
import ru.clevertec.checksystem.core.entity.check.Check;
import ru.clevertec.checksystem.core.entity.discount.Discount;
import ru.clevertec.normalino.json.NormalinoIgnore;

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS)
public abstract class CheckDiscount extends Discount<CheckDiscount> implements ICheckComposable {

    @JsonIgnore
    private Check check;

    protected CheckDiscount() {
    }

    public CheckDiscount(String description) throws IllegalArgumentException {
        super(description);
    }

    public CheckDiscount(int id, String description) throws IllegalArgumentException {
        super(id, description);
    }

    public CheckDiscount(int id, String description, CheckDiscount childCheckDiscount)
            throws IllegalArgumentException {
        super(id, description, childCheckDiscount);
    }

    @NormalinoIgnore
    public Check getCheck() {
        return check;
    }

    public void setCheck(Check check) throws IllegalArgumentException {
        if (check == null) {
            throw new IllegalArgumentException("Argument 'check' cannot be null");
        }
        this.check = check;
        if (getDependentDiscount() != null) {
            getDependentDiscount().setCheck(check);
        }
    }
}
