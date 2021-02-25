package ru.clevertec.checksystem.core.entity.discount;

import ru.clevertec.checksystem.core.entity.BaseEntity;
import ru.clevertec.checksystem.core.util.ThrowUtils;

import java.math.BigDecimal;

public abstract class Discount<T extends Discount<T>> extends BaseEntity {

    private String description;
    private T dependentDiscount;

    protected Discount() {
    }

    public Discount(String description) {
        setDescription(description);
    }

    public Discount(int id, String description) {
        setId(id);
        setDescription(description);
    }

    public Discount(String description, T dependentDiscount) {
        setDescription(description);
        setDependentDiscount(dependentDiscount);
    }

    public Discount(int id, String description, T dependentDiscount) {
        super(id);
        setDescription(description);
        setDependentDiscount(dependentDiscount);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        ThrowUtils.Argument.nullOrBlank("description", description);
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
