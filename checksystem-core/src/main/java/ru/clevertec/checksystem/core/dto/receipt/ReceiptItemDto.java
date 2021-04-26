package ru.clevertec.checksystem.core.dto.receipt;

import lombok.*;
import lombok.extern.jackson.Jacksonized;
import ru.clevertec.checksystem.core.dto.ProductDto;
import ru.clevertec.checksystem.core.dto.discount.receiptitem.ReceiptItemDiscountDto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.Set;

@Data
@Builder
@Jacksonized
@NoArgsConstructor
@AllArgsConstructor
public class ReceiptItemDto {

    private Long id;

    @NotNull
    @Positive
    private Long quantity;

    @NotNull
    private Long productId;

    @NotNull
    private Long receiptId;

    private ProductDto product;

    @Singular
    private Set<ReceiptItemDiscountDto> discounts;
}
