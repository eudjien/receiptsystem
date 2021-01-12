package ru.clevertec.checksystem.core.discount.check.base;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import ru.clevertec.checksystem.core.check.Check;
import ru.clevertec.checksystem.core.discount.Discount;
import ru.clevertec.checksystem.normalino.json.NormalinoIgnore;

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS)
public abstract class CheckDiscount extends Discount<CheckDiscount> {

    @JsonIgnore
    private Check check;

    public CheckDiscount(String description) throws Exception {
        super(description);
    }

    public CheckDiscount(int id, String description) throws Exception {
        super(id, description);
    }

    public CheckDiscount(int id, String description, CheckDiscount childDiscount) throws Exception {
        super(id, description, childDiscount);
    }

    @NormalinoIgnore
    public Check getCheck() {
        return check;
    }

    public void setCheck(Check check) throws Exception {
        if (check == null) {
            throw new Exception("Check cannot be null");
        }
        this.check = check;
        if (getChildDiscount() != null) {
            getChildDiscount().setCheck(check);
        }
    }
}
