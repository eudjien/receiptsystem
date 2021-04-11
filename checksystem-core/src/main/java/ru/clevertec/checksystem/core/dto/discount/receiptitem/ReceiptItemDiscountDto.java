package ru.clevertec.checksystem.core.dto.discount.receiptitem;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, defaultImpl = ReceiptItemDiscountDto.class)
public class ReceiptItemDiscountDto {

    private Long id;

    @NotBlank
    private String description;

    private Long dependentDiscountId;
}
