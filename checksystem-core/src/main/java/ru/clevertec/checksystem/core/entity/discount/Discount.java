package ru.clevertec.checksystem.core.entity.discount;

import ru.clevertec.checksystem.core.entity.BaseEntity;

import java.math.BigDecimal;

public abstract class Discount<T extends Discount<T>> extends BaseEntity {

    private String description;
    private T dependentDiscount;

    protected Discount() {
    }

    public Discount(String description) throws IllegalArgumentException {
        setDescription(description);
    }

    public Discount(int id, String description) throws IllegalArgumentException {
        setId(id);
        setDescription(description);
    }

    public Discount(String description, T dependentDiscount) throws IllegalArgumentException {
        setDescription(description);
        setDependentDiscount(dependentDiscount);
    }

    public Discount(int id, String description, T dependentDiscount) throws IllegalArgumentException {
        super(id);
        setDescription(description);
        setDependentDiscount(dependentDiscount);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) throws IllegalArgumentException {
        if (description == null || description.isBlank()) {
            throw new IllegalArgumentException("Description cannot be null or empty");
        }
        this.description = description;
    }

    public T getDependentDiscount() {
        return dependentDiscount;
    }

    public void setDependentDiscount(T discount) {
        dependentDiscount = discount;
    }

    public abstract BigDecimal discountAmount();
}
