package ru.clevertec.checksystem.core.dto.receipt;

import ru.clevertec.checksystem.core.dto.ProductDto;
import ru.clevertec.checksystem.core.dto.discount.receiptitem.ReceiptItemDiscountDto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class ReceiptItemDto {

    private Long id = 0L;

    @NotNull
    @Positive
    private Long quantity;

    @NotNull
    private Long productId;

    @NotNull
    private Long receiptId;

    private ProductDto product;

    private final Set<ReceiptItemDiscountDto> discounts = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getReceiptId() {
        return receiptId;
    }

    public void setReceiptId(Long receiptId) {
        this.receiptId = receiptId;
    }

    public ProductDto getProduct() {
        return product;
    }

    public void setProduct(ProductDto product) {
        this.product = product;
    }

    public Collection<ReceiptItemDiscountDto> getDiscounts() {
        return discounts;
    }

    public void setDiscounts(Collection<ReceiptItemDiscountDto> discounts) {
        this.discounts.clear();
        if (discounts != null)
            this.discounts.addAll(discounts);
    }
}
