package ru.clevertec.checksystem.core.entity.discount;

import ru.clevertec.checksystem.core.entity.BaseEntity;
import ru.clevertec.checksystem.core.util.ThrowUtils;

import javax.persistence.*;

import static ru.clevertec.checksystem.core.Constants.Entities;

@MappedSuperclass
public abstract class AbstractDiscount<T extends AbstractDiscount<T>> extends BaseEntity {

    @Column(name = Entities.Mapping.Column.DESCRIPTION, nullable = false)
    private String description;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = Entities.Mapping.JoinColumn.DEPENDENT_DISCOUNT_ID)
    private T dependentDiscount;

    @Column(name = Entities.Mapping.JoinColumn.DEPENDENT_DISCOUNT_ID, insertable = false, updatable = false)
    private Long dependentDiscountId;

    protected AbstractDiscount() {
    }

    protected AbstractDiscount(String description) {
        setDescription(description);
    }

    protected AbstractDiscount(String description, T dependentDiscount) {
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

    public Long getDependentDiscountId() {
        return dependentDiscountId;
    }

    public void setDependentDiscountId(Long dependentDiscountId) {
        this.dependentDiscountId = dependentDiscountId;
    }
}
