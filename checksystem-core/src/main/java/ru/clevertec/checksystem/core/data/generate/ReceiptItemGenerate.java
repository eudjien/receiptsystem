package ru.clevertec.checksystem.core.data.generate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;

import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@Jacksonized
@AllArgsConstructor
@NoArgsConstructor
public class ReceiptItemGenerate {

    private Long productId;
    private Long quantity;

    @Builder.Default
    private Set<Long> discountIds = new HashSet<>();
}
