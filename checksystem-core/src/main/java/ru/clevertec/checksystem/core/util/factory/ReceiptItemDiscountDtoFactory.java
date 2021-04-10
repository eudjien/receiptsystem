package ru.clevertec.checksystem.core.util.factory;

import org.springframework.stereotype.Component;
import ru.clevertec.checksystem.core.constant.Entities;
import ru.clevertec.checksystem.core.dto.discount.receipt.ReceiptDiscountDto;
import ru.clevertec.checksystem.core.dto.discount.receiptitem.ReceiptItemDiscountDto;
import ru.clevertec.checksystem.core.dto.discount.receiptitem.SimpleConstantReceiptItemDiscountDto;
import ru.clevertec.checksystem.core.dto.discount.receiptitem.SimplePercentageReceiptItemDiscountDto;
import ru.clevertec.checksystem.core.dto.discount.receiptitem.ThresholdPercentageReceiptItemDiscountDto;
import ru.clevertec.checksystem.core.exception.ValidationException;

import javax.validation.Validator;
import java.math.BigDecimal;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class ReceiptItemDiscountDtoFactory {

    public ReceiptItemDiscountDto create(Map<String, String> map, Validator validator) {

        var type = map.get(Entities.DiscriminatorNames.RECEIPT_ITEM_DISCOUNT);

        ReceiptItemDiscountDto receiptDiscount;

        switch (type) {
            case Entities.DiscriminatorValues.SIMPLE_CONSTANT_RECEIPT_ITEM_DISCOUNT -> {
                var discountDto = (SimpleConstantReceiptItemDiscountDto) (receiptDiscount = new SimpleConstantReceiptItemDiscountDto());
                discountDto.setConstant(new BigDecimal(map.get("constant")));
            }
            case Entities.DiscriminatorValues.SIMPLE_PERCENTAGE_RECEIPT_ITEM_DISCOUNT -> {
                var discountDto = (SimplePercentageReceiptItemDiscountDto) (receiptDiscount = new SimplePercentageReceiptItemDiscountDto());
                discountDto.setPercent(Double.parseDouble(map.get("percent")));
            }
            case Entities.DiscriminatorValues.THRESHOLD_PERCENTAGE_RECEIPT_ITEM_DISCOUNT -> {
                var discountDto = (ThresholdPercentageReceiptItemDiscountDto) (receiptDiscount = new ThresholdPercentageReceiptItemDiscountDto());
                discountDto.setPercent(Double.parseDouble(map.get("percent")));
                discountDto.setThreshold(Long.parseLong(map.get("threshold")));
            }
            default -> throw new IllegalArgumentException("map");
        }

        receiptDiscount.setId(Long.parseLong(map.getOrDefault("id", "0")));
        receiptDiscount.setDescription(map.get("description"));

        try {
            receiptDiscount.setDependentDiscountId(Long.parseLong(map.get("dependentDiscountId")));
        } catch (Exception ignored) {
        }

        validate(receiptDiscount, validator);

        return receiptDiscount;
    }

    private static void validate(ReceiptItemDiscountDto receiptItemDiscountDto, Validator validator) {

        var constraintViolations = validator.validate(receiptItemDiscountDto);

        if (!constraintViolations.isEmpty()) {
            throw new ValidationException(constraintViolations.stream()
                    .map(a -> new ValidationException.Error(a.getPropertyPath().toString(), a.getMessage()))
                    .collect(Collectors.toSet()));
        }
    }
}
