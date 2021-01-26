package ru.clevertec.checksystem.core.entity.discount;

import ru.clevertec.checksystem.core.entity.BaseEntity;

import java.math.BigDecimal;

public abstract class Discount<T extends Discount<T>> extends BaseEntity {

    private String description;
    private T childDiscount;

    protected Discount() {
    }

    public Discount(String description) throws IllegalArgumentException {
        setDescription(description);
    }

    public Discount(int id, String description) throws IllegalArgumentException {
        setId(id);
        setDescription(description);
    }

    public Discount(String description, T childDiscount) throws IllegalArgumentException {
        setDescription(description);
        setChildDiscount(childDiscount);
    }

    public Discount(int id, String description, T childDiscount) throws IllegalArgumentException {
        super(id);
        setDescription(description);
        setChildDiscount(childDiscount);
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

    public T getChildDiscount() {
        return childDiscount;
    }

    public void setChildDiscount(T discount) {
        childDiscount = discount;
    }

    public abstract BigDecimal discountSum();
}
