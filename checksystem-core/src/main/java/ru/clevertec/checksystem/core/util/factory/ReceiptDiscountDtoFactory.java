package ru.clevertec.checksystem.core.util.factory;

import org.springframework.stereotype.Component;
import ru.clevertec.checksystem.core.constant.Entities;
import ru.clevertec.checksystem.core.dto.discount.receipt.ReceiptDiscountDto;
import ru.clevertec.checksystem.core.dto.discount.receipt.SimpleConstantReceiptDiscountDto;
import ru.clevertec.checksystem.core.dto.discount.receipt.SimplePercentageReceiptDiscountDto;

import java.math.BigDecimal;
import java.util.Map;

@Component
public class ReceiptDiscountDtoFactory {

    public ReceiptDiscountDto create(Map<String, String> map) {

        var type = map.get(Entities.DiscriminatorNames.RECEIPT_DISCOUNT);

        ReceiptDiscountDto receiptDiscount;

        switch (type) {
            case Entities.DiscriminatorValues.SIMPLE_CONSTANT_RECEIPT_DISCOUNT -> {
                receiptDiscount = new SimpleConstantReceiptDiscountDto();
                ((SimpleConstantReceiptDiscountDto) receiptDiscount).setConstant(new BigDecimal(map.get("constant")));
            }
            case Entities.DiscriminatorValues.SIMPLE_PERCENTAGE_RECEIPT_DISCOUNT -> {
                receiptDiscount = new SimplePercentageReceiptDiscountDto();
                ((SimplePercentageReceiptDiscountDto) receiptDiscount).setPercent(Double.parseDouble(map.get("percent")));
            }
            default -> throw new IllegalArgumentException("map");
        }

        receiptDiscount.setId(Long.parseLong(map.getOrDefault("id", "0")));
        receiptDiscount.setDescription(map.get("description"));

        try {
            receiptDiscount.setDependentDiscountId(Long.parseLong(map.get("dependentDiscountId")));
        } catch (Exception ignored) {
        }

        return receiptDiscount;
    }
}
