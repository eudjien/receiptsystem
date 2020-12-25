package ru.clevertec.checksystem.core.discount.item;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import ru.clevertec.checksystem.core.discount.item.base.CheckItemDiscount;
import ru.clevertec.checksystem.core.discount.item.base.ConstantCheckItemDiscount;

import java.math.BigDecimal;

public final class SimpleConstantCheckItemDiscount extends ConstantCheckItemDiscount {
    public SimpleConstantCheckItemDiscount(String description, BigDecimal discountValue) throws Exception {
        super(description, discountValue);
    }

    public SimpleConstantCheckItemDiscount(int id, String description, BigDecimal discountValue) throws Exception {
        super(id, description, discountValue);
    }

    @JsonCreator
    public SimpleConstantCheckItemDiscount(
            @JsonProperty("id") int id,
            @JsonProperty("description") String description,
            @JsonProperty("discountValue") BigDecimal discountValue,
            @JsonProperty("childDiscount") CheckItemDiscount childDiscount) throws Exception {
        super(id, description, discountValue, childDiscount);
    }
}
