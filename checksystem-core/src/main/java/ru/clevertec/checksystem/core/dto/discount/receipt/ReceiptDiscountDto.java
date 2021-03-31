package ru.clevertec.checksystem.core.dto.discount.receipt;

public class ReceiptDiscountDto {

    private Long id = 0L;
    private String description;
    private Long dependentDiscountId = 0L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getDependentDiscountId() {
        return dependentDiscountId;
    }

    public void setDependentDiscountId(Long dependentDiscountId) {
        this.dependentDiscountId = dependentDiscountId;
    }
}
