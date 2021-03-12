package ru.clevertec.checksystem.core.configuration;

import org.modelmapper.AbstractConverter;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;
import ru.clevertec.checksystem.core.dto.discount.receipt.ReceiptDiscountDto;
import ru.clevertec.checksystem.core.dto.discount.receipt.SimpleConstantReceiptDiscountDto;
import ru.clevertec.checksystem.core.dto.discount.receipt.SimplePercentageReceiptDiscountDto;
import ru.clevertec.checksystem.core.dto.discount.receiptitem.ReceiptItemDiscountDto;
import ru.clevertec.checksystem.core.dto.discount.receiptitem.SimpleConstantReceiptItemDiscountDto;
import ru.clevertec.checksystem.core.dto.discount.receiptitem.SimplePercentageReceiptItemDiscountDto;
import ru.clevertec.checksystem.core.dto.discount.receiptitem.ThresholdPercentageReceiptItemDiscountDto;
import ru.clevertec.checksystem.core.entity.discount.receipt.SimpleConstantReceiptDiscount;
import ru.clevertec.checksystem.core.entity.discount.receipt.SimplePercentageReceiptDiscount;
import ru.clevertec.checksystem.core.entity.discount.receiptitem.SimpleConstantReceiptItemDiscount;
import ru.clevertec.checksystem.core.entity.discount.receiptitem.SimplePercentageReceiptItemDiscount;
import ru.clevertec.checksystem.core.entity.discount.receiptitem.ThresholdPercentageReceiptItemDiscount;

@Component
public class ApplicationModelMapper extends ModelMapper {

    public ApplicationModelMapper() {
        getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        addReceiptDiscountConverters(this);
        addReceiptItemDiscountConverters(this);
    }

    private static void addReceiptDiscountConverters(ModelMapper modelMapper) {
        modelMapper.addConverter(new AbstractConverter<SimplePercentageReceiptDiscount, ReceiptDiscountDto>() {
            @Override
            protected SimplePercentageReceiptDiscountDto convert(SimplePercentageReceiptDiscount source) {
                return modelMapper.map(source, SimplePercentageReceiptDiscountDto.class);
            }
        });
        modelMapper.addConverter(new AbstractConverter<SimpleConstantReceiptDiscount, ReceiptDiscountDto>() {
            @Override
            protected SimpleConstantReceiptDiscountDto convert(SimpleConstantReceiptDiscount source) {
                return modelMapper.map(source, SimpleConstantReceiptDiscountDto.class);
            }
        });
    }

    private static void addReceiptItemDiscountConverters(ModelMapper modelMapper) {
        modelMapper.addConverter(new AbstractConverter<SimplePercentageReceiptItemDiscount, ReceiptItemDiscountDto>() {
            @Override
            protected SimplePercentageReceiptItemDiscountDto convert(SimplePercentageReceiptItemDiscount source) {
                return modelMapper.map(source, SimplePercentageReceiptItemDiscountDto.class);
            }
        });
        modelMapper.addConverter(new AbstractConverter<ThresholdPercentageReceiptItemDiscount, ReceiptItemDiscountDto>() {
            @Override
            protected ThresholdPercentageReceiptItemDiscountDto convert(ThresholdPercentageReceiptItemDiscount source) {
                return modelMapper.map(source, ThresholdPercentageReceiptItemDiscountDto.class);
            }
        });
        modelMapper.addConverter(new AbstractConverter<SimpleConstantReceiptItemDiscount, ReceiptItemDiscountDto>() {
            @Override
            protected SimpleConstantReceiptItemDiscountDto convert(SimpleConstantReceiptItemDiscount source) {
                return modelMapper.map(source, SimpleConstantReceiptItemDiscountDto.class);
            }
        });
    }
}
