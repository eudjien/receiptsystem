package ru.clevertec.checksystem.core.discount.item;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import ru.clevertec.checksystem.core.discount.item.base.CheckItemDiscount;
import ru.clevertec.checksystem.core.discount.item.base.PercentageOffCheckItemDiscount;

public final class SimplePercentageOffCheckItemDiscount extends PercentageOffCheckItemDiscount {
    public SimplePercentageOffCheckItemDiscount(String description, double discountPercent) throws Exception {
        super(description, discountPercent);
    }

    public SimplePercentageOffCheckItemDiscount(int id, String description, double discountPercent) throws Exception {
        super(id, description, discountPercent);
    }

    @JsonCreator
    public SimplePercentageOffCheckItemDiscount(
            @JsonProperty("id") int id,
            @JsonProperty("description") String description,
            @JsonProperty("discountPercent") double discountPercent,
            @JsonProperty("childDiscount") CheckItemDiscount childDiscount) throws Exception {
        super(id, description, discountPercent, childDiscount);
    }
}
