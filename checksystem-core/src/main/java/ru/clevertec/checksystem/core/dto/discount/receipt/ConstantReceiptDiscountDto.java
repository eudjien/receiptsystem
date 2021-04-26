package ru.clevertec.checksystem.core.dto.discount.receipt;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Data
@SuperBuilder
@Jacksonized
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ConstantReceiptDiscountDto extends ReceiptDiscountDto {
    @NotNull
    @Positive
    private BigDecimal constant;
}
