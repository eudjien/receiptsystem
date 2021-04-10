package ru.clevertec.checksystem.core.util.factory;

import org.springframework.stereotype.Component;
import ru.clevertec.checksystem.core.constant.Entities;
import ru.clevertec.checksystem.core.dto.discount.receipt.ReceiptDiscountDto;
import ru.clevertec.checksystem.core.dto.discount.receipt.SimpleConstantReceiptDiscountDto;
import ru.clevertec.checksystem.core.dto.discount.receipt.SimplePercentageReceiptDiscountDto;
import ru.clevertec.checksystem.core.exception.ValidationException;

import javax.validation.Validator;
import java.math.BigDecimal;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class ReceiptDiscountDtoFactory {

    public ReceiptDiscountDto create(Map<String, String> map, Validator validator) {

        var type = map.get(Entities.DiscriminatorNames.RECEIPT_DISCOUNT);

        ReceiptDiscountDto receiptDiscountDto;

        switch (type) {
            case Entities.DiscriminatorValues.SIMPLE_CONSTANT_RECEIPT_DISCOUNT -> {
                receiptDiscountDto = new SimpleConstantReceiptDiscountDto();
                ((SimpleConstantReceiptDiscountDto) receiptDiscountDto).setConstant(new BigDecimal(map.get("constant")));
            }
            case Entities.DiscriminatorValues.SIMPLE_PERCENTAGE_RECEIPT_DISCOUNT -> {
                receiptDiscountDto = new SimplePercentageReceiptDiscountDto();
                ((SimplePercentageReceiptDiscountDto) receiptDiscountDto).setPercent(Double.parseDouble(map.get("percent")));
            }
            default -> throw new IllegalArgumentException("map");
        }

        receiptDiscountDto.setId(Long.parseLong(map.getOrDefault("id", "0")));
        receiptDiscountDto.setDescription(map.get("description"));

        try {
            receiptDiscountDto.setDependentDiscountId(Long.parseLong(map.get("dependentDiscountId")));
        } catch (Exception ignored) {
        }

        validate(receiptDiscountDto, validator);

        return receiptDiscountDto;
    }

    private static void validate(ReceiptDiscountDto receiptDiscountDto, Validator validator) {

        var constraintViolations = validator.validate(receiptDiscountDto);

        if (!constraintViolations.isEmpty()) {
            throw new ValidationException(constraintViolations.stream()
                    .map(a -> new ValidationException.Error(a.getPropertyPath().toString(), a.getMessage()))
                    .collect(Collectors.toSet()));
        }
    }
}
