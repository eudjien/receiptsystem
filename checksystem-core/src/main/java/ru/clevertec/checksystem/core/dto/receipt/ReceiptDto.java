package ru.clevertec.checksystem.core.dto.receipt;

import lombok.*;
import lombok.extern.jackson.Jacksonized;
import ru.clevertec.checksystem.core.dto.discount.receipt.ReceiptDiscountDto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.util.Date;
import java.util.Set;

@Data
@Builder
@Jacksonized
@NoArgsConstructor
@AllArgsConstructor
public class ReceiptDto {

    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    private String description;

    @NotBlank
    private String address;

    @NotBlank
    private String phoneNumber;

    @NotBlank
    private String cashier;

    @NotNull
    @PastOrPresent
    private Date date;

    @Singular
    private Set<ReceiptItemDto> receiptItems;

    @Singular
    private Set<ReceiptDiscountDto> discounts;
}
