package ru.clevertec.checksystem.core.discount;

import ru.clevertec.checksystem.core.BaseEntity;

import java.math.BigDecimal;

public abstract class Discount<T extends Discount<T>> extends BaseEntity {

    private String description;
    private T childDiscount;

    public Discount(String description) throws Exception {
        setDescription(description);
    }

    public Discount(int id, String description) throws Exception {
        setId(id);
        setDescription(description);
    }

    public Discount(String description, T childDiscount) throws Exception {
        setDescription(description);
        setChildDiscount(childDiscount);
    }

    public Discount(int id, String description, T childDiscount) throws Exception {
        super(id);
        setDescription(description);
        setChildDiscount(childDiscount);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) throws Exception {
        if (description == null || description.isBlank()) {
            throw new Exception("Description cannot be null or empty");
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
