package ru.clevertec.checksystem.core.dto.discount.receiptitem;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

@Data
@SuperBuilder
@Jacksonized
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public final class ThresholdPercentageReceiptItemDiscountDto extends ReceiptItemDiscountDto {

    @NotNull
    @Positive
    private Double percent;

    @NotNull
    @PositiveOrZero
    private Long threshold;
}
