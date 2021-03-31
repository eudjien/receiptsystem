package ru.clevertec.checksystem.core.util.mapper.convert;

import org.modelmapper.AbstractConverter;
import org.modelmapper.ModelMapper;
import ru.clevertec.checksystem.core.dto.discount.receipt.ReceiptDiscountDto;
import ru.clevertec.checksystem.core.dto.discount.receipt.SimplePercentageReceiptDiscountDto;
import ru.clevertec.checksystem.core.entity.discount.receipt.SimplePercentageReceiptDiscount;

public class SimplePercentageReceiptDiscountFromEntityToDtoConverter extends AbstractConverter<SimplePercentageReceiptDiscount, ReceiptDiscountDto> {

    private final ModelMapper modelMapper;

    public SimplePercentageReceiptDiscountFromEntityToDtoConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    protected SimplePercentageReceiptDiscountDto convert(SimplePercentageReceiptDiscount source) {
        return modelMapper.map(source, SimplePercentageReceiptDiscountDto.class);
    }
}
