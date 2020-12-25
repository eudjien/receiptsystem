package ru.clevertec.checksystem.core;

public class CheckItemDiscountPair {

    private int productId;
    private String discountKey;

    public CheckItemDiscountPair(int productId, String discountKey) {
        this.productId = productId;
        this.discountKey = discountKey;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getDiscountKey() {
        return discountKey;
    }

    public void setDiscountKey(String discountKey) {
        this.discountKey = discountKey;
    }
}
