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
import java.math.BigDecimal;

@Jacksonized
@SuperBuilder
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = Entities.Table.CONSTANT_RECEIPT_DISCOUNT)
@DiscriminatorValue(Entities.DiscriminatorValues.CONSTANT_RECEIPT_DISCOUNT)
public final class ConstantReceiptDiscount extends ReceiptDiscount {

    @Column(name = Entities.Column.CONSTANT, nullable = false)
    private BigDecimal constant;
}
