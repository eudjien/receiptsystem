package ru.clevertec.checksystem.core.dto.discount.receipt;

public class ReceiptDiscountDto {

    private Integer id;
    private String description;
    private Long dependentDiscountId = 0L;

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
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
