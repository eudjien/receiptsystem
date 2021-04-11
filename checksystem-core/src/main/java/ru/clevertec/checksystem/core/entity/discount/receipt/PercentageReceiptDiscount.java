package ru.clevertec.checksystem.core.entity.discount.receipt;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import ru.clevertec.checksystem.core.constant.Entities;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Jacksonized
@SuperBuilder
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = Entities.Table.PERCENTAGE_RECEIPT_DISCOUNT)
@DiscriminatorValue(Entities.DiscriminatorValues.PERCENTAGE_RECEIPT_DISCOUNT)
public final class PercentageReceiptDiscount extends ReceiptDiscount {

    @Column(name = Entities.Column.PERCENT, nullable = false)
    private Double percent;
}
