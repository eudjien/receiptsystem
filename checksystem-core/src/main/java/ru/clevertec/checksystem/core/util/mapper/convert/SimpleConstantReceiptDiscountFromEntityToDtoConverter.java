package ru.clevertec.checksystem.core.util.mapper.convert;

import org.modelmapper.AbstractConverter;
import org.modelmapper.ModelMapper;
import ru.clevertec.checksystem.core.dto.discount.receipt.ReceiptDiscountDto;
import ru.clevertec.checksystem.core.dto.discount.receipt.SimpleConstantReceiptDiscountDto;
import ru.clevertec.checksystem.core.entity.discount.receipt.SimpleConstantReceiptDiscount;

public class SimpleConstantReceiptDiscountFromEntityToDtoConverter extends AbstractConverter<SimpleConstantReceiptDiscount, ReceiptDiscountDto> {

    private final ModelMapper modelMapper;

    public SimpleConstantReceiptDiscountFromEntityToDtoConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    protected SimpleConstantReceiptDiscountDto convert(SimpleConstantReceiptDiscount source) {
        return modelMapper.map(source, SimpleConstantReceiptDiscountDto.class);
    }
}
