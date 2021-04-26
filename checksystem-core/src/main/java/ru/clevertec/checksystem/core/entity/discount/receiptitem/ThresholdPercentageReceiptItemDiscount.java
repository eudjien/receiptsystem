package ru.clevertec.checksystem.core.entity.discount.receiptitem;

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
@Table(name = Entities.Table.THRESHOLD_PERCENTAGE_RECEIPT_ITEM_DISCOUNT)
@DiscriminatorValue(Entities.DiscriminatorValues.THRESHOLD_PERCENTAGE_RECEIPT_ITEM_DISCOUNT)
public final class ThresholdPercentageReceiptItemDiscount extends ReceiptItemDiscount {

    @Column(name = Entities.Column.PERCENT, nullable = false)
    private Double percent;

    @Column(name = Entities.Column.THRESHOLD, nullable = false)
    private Long threshold;
}
