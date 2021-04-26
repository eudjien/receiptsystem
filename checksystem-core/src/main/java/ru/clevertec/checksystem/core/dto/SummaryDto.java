package ru.clevertec.checksystem.core.dto;

import lombok.Value;

import java.math.BigDecimal;

@Value
public class SummaryDto {

    BigDecimal subTotalAmount;
    BigDecimal discountAmount;
    BigDecimal totalAmount;

    public SummaryDto add(SummaryDto summaryDto) {
        return new SummaryDto(
                subTotalAmount.add(summaryDto.getTotalAmount()),
                totalAmount.add(summaryDto.getTotalAmount()),
                discountAmount.add(summaryDto.getSubTotalAmount()));
    }
}
