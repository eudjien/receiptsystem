package ru.clevertec.checksystem.core.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Collection;

public class CheckItemGenerate {

    private long productId;
    private int quantity;
    private Collection<Long> discountIds;

    public CheckItemGenerate() {
    }

    public CheckItemGenerate(long productId, int quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    @JsonCreator
    public CheckItemGenerate(
            @JsonProperty("productId") long productId,
            @JsonProperty("quantity") int quantity,
            @JsonProperty("discountIds") Collection<Long> discountIds) {
        this.productId = productId;
        this.quantity = quantity;
        this.discountIds = discountIds;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Collection<Long> getDiscountIds() {
        return discountIds;
    }

    public void setDiscountIds(Collection<Long> discountIds) {
        this.discountIds = discountIds;
    }
}
