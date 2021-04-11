package ru.clevertec.checksystem.core.util.mapper.convert;

import org.modelmapper.AbstractConverter;
import org.modelmapper.ModelMapper;
import ru.clevertec.checksystem.core.dto.discount.receipt.PercentageReceiptDiscountDto;
import ru.clevertec.checksystem.core.dto.discount.receipt.ReceiptDiscountDto;
import ru.clevertec.checksystem.core.entity.discount.receipt.PercentageReceiptDiscount;

public class PercentageReceiptDiscountFromEntityToDtoConverter extends AbstractConverter<PercentageReceiptDiscount, ReceiptDiscountDto> {

    private final ModelMapper modelMapper;

    public PercentageReceiptDiscountFromEntityToDtoConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    protected PercentageReceiptDiscountDto convert(PercentageReceiptDiscount source) {
        return modelMapper.map(source, PercentageReceiptDiscountDto.class);
    }
}
