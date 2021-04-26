package ru.clevertec.checksystem.core.util.mapper.convert;

import org.modelmapper.AbstractConverter;
import org.modelmapper.ModelMapper;
import ru.clevertec.checksystem.core.dto.discount.receipt.ConstantReceiptDiscountDto;
import ru.clevertec.checksystem.core.dto.discount.receipt.ReceiptDiscountDto;
import ru.clevertec.checksystem.core.entity.discount.receipt.ConstantReceiptDiscount;

public class ConstantReceiptDiscountFromEntityToDtoConverter extends AbstractConverter<ConstantReceiptDiscount, ReceiptDiscountDto> {

    private final ModelMapper modelMapper;

    public ConstantReceiptDiscountFromEntityToDtoConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    protected ConstantReceiptDiscountDto convert(ConstantReceiptDiscount source) {
        return modelMapper.map(source, ConstantReceiptDiscountDto.class);
    }
}
