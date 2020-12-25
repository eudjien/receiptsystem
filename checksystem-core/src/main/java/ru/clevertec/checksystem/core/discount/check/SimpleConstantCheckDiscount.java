package ru.clevertec.checksystem.core.discount.check;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import ru.clevertec.checksystem.core.discount.check.base.CheckDiscount;
import ru.clevertec.checksystem.core.discount.check.base.ConstantCheckDiscount;

import java.math.BigDecimal;

public final class SimpleConstantCheckDiscount extends ConstantCheckDiscount {

    private BigDecimal discountValue;

    public SimpleConstantCheckDiscount(String description, BigDecimal discountValue) throws Exception {
        super(description, discountValue);
    }

    public SimpleConstantCheckDiscount(int id, String description, BigDecimal discountValue) throws Exception {
        super(id, description, discountValue);
    }

    @JsonCreator
    public SimpleConstantCheckDiscount(
            @JsonProperty("id") int id,
            @JsonProperty("description") String description,
            @JsonProperty("discountValue") BigDecimal discountValue,
            @JsonProperty("childDiscount") CheckDiscount childDiscount) throws Exception {
        super(id, description, discountValue, childDiscount);
    }
}
