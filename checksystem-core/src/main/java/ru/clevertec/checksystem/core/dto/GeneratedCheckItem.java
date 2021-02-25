package ru.clevertec.checksystem.core.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Collection;

public class GeneratedCheckItem {

    private int productId;
    private int quantity;
    private Collection<Integer> discountIds;

    public GeneratedCheckItem() {
    }

    public GeneratedCheckItem(int productId, int quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    @JsonCreator
    public GeneratedCheckItem(
            @JsonProperty("productId") int productId,
            @JsonProperty("quantity") int quantity,
            @JsonProperty("discountIds") Collection<Integer> discountIds) {
        this.productId = productId;
        this.quantity = quantity;
        this.discountIds = discountIds;
    }

    public int getProductId() {
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

    public Collection<Integer> getDiscountIds() {
        return discountIds;
    }

    public void setDiscountIds(Collection<Integer> discountIds) {
        this.discountIds = discountIds;
    }
}
