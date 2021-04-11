package ru.clevertec.checksystem.core.data.generate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@Jacksonized
@AllArgsConstructor
@NoArgsConstructor
public class ReceiptGenerate {

    private Long id;
    private String name;
    private String description;
    private String address;
    private String cashier;
    private String phoneNumber;
    private Date date;

    @Builder.Default
    private Set<Long> discountIds = new HashSet<>();

    @Builder.Default
    private Set<ReceiptItemGenerate> receiptItemGenerates = new HashSet<>();
}
