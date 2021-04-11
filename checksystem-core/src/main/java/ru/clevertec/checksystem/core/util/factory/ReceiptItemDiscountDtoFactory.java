package ru.clevertec.checksystem.core.util.factory;

import org.springframework.stereotype.Component;
import ru.clevertec.checksystem.core.constant.Entities;
import ru.clevertec.checksystem.core.dto.discount.receiptitem.ConstantReceiptItemDiscountDto;
import ru.clevertec.checksystem.core.dto.discount.receiptitem.PercentageReceiptItemDiscountDto;
import ru.clevertec.checksystem.core.dto.discount.receiptitem.ReceiptItemDiscountDto;
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

        ReceiptItemDiscountDto receiptItemDiscountDto;

        switch (type) {
            case Entities.DiscriminatorValues.CONSTANT_RECEIPT_ITEM_DISCOUNT -> {
                var discountDto = (ConstantReceiptItemDiscountDto) (receiptItemDiscountDto = new ConstantReceiptItemDiscountDto());
                discountDto.setConstant(new BigDecimal(map.get("constant")));
            }
            case Entities.DiscriminatorValues.PERCENTAGE_RECEIPT_ITEM_DISCOUNT -> {
                var discountDto = (PercentageReceiptItemDiscountDto) (receiptItemDiscountDto = new PercentageReceiptItemDiscountDto());
                discountDto.setPercent(Double.parseDouble(map.get("percent")));
            }
            case Entities.DiscriminatorValues.THRESHOLD_PERCENTAGE_RECEIPT_ITEM_DISCOUNT -> {
                var discountDto = (ThresholdPercentageReceiptItemDiscountDto) (receiptItemDiscountDto = new ThresholdPercentageReceiptItemDiscountDto());
                discountDto.setPercent(Double.parseDouble(map.get("percent")));
                discountDto.setThreshold(Long.parseLong(map.get("threshold")));
            }
            default -> throw new IllegalArgumentException("map");
        }

        receiptItemDiscountDto.setId(Long.parseLong(map.getOrDefault("id", "0")));
        receiptItemDiscountDto.setDescription(map.get("description"));

        try {
            receiptItemDiscountDto.setDependentDiscountId(Long.parseLong(map.get("dependentDiscountId")));
        } catch (Exception ignored) {
        }

        validate(receiptItemDiscountDto, validator);

        return receiptItemDiscountDto;
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
