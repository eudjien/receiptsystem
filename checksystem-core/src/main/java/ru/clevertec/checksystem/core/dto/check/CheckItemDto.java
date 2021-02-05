package ru.clevertec.checksystem.core.dto.check;

import ru.clevertec.checksystem.core.entity.Product;

public class CheckItemDto {

    private Product product;
    private int quantity;
    private Integer productId;
    private Integer checkId;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getCheckId() {
        return checkId;
    }

    public void setCheckId(int checkId) {
        this.checkId = checkId;
    }
}
