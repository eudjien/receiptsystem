package ru.clevertec.checksystem.core.discount.check;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import ru.clevertec.checksystem.core.discount.check.base.CheckDiscount;
import ru.clevertec.checksystem.core.discount.check.base.PercentageOffCheckDiscount;

public final class SimplePercentageOffCheckDiscount extends PercentageOffCheckDiscount {

    public SimplePercentageOffCheckDiscount(String description, double discountPercent) throws Exception {
        super(description, discountPercent);
    }

    public SimplePercentageOffCheckDiscount(int id, String description, double discountPercent) throws Exception {
        super(id, description, discountPercent);
    }

    @JsonCreator
    public SimplePercentageOffCheckDiscount(
            @JsonProperty("id") int id,
            @JsonProperty("description") String description,
            @JsonProperty("discountPercent") double discountPercent,
            @JsonProperty("childDiscount") CheckDiscount childDiscount) throws Exception {
        super(id, description, discountPercent, childDiscount);
    }
}
