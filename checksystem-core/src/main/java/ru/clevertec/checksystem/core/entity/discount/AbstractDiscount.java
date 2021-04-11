package ru.clevertec.checksystem.core.entity.discount;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import ru.clevertec.checksystem.core.constant.Entities;
import ru.clevertec.checksystem.core.entity.BaseEntity;

import javax.persistence.*;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@MappedSuperclass
public abstract class AbstractDiscount<T extends AbstractDiscount<T>> extends BaseEntity {

    @Column(name = Entities.Column.DESCRIPTION, nullable = false)
    private String description;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = Entities.JoinColumn.DEPENDENT_DISCOUNT_ID)
    private T dependentDiscount;

    @Column(name = Entities.JoinColumn.DEPENDENT_DISCOUNT_ID, insertable = false, updatable = false)
    private Long dependentDiscountId;
}
