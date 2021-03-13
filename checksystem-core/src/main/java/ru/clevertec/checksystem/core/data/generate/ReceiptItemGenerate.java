package ru.clevertec.checksystem.core.data.generate;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Collection;

public class ReceiptItemGenerate {

    private long productId;
    private Long quantity;
    private Collection<Long> discountIds;

    public ReceiptItemGenerate() {
    }

    public ReceiptItemGenerate(long productId, Long quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    @JsonCreator
    public ReceiptItemGenerate(
            @JsonProperty("productId") long productId,
            @JsonProperty("quantity") Long quantity,
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

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public Collection<Long> getDiscountIds() {
        return discountIds;
    }

    public void setDiscountIds(Collection<Long> discountIds) {
        this.discountIds = discountIds;
    }
}
