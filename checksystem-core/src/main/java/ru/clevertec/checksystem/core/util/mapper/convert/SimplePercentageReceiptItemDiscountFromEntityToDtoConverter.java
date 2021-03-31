package ru.clevertec.checksystem.core.util.mapper.convert;

import org.modelmapper.AbstractConverter;
import org.modelmapper.ModelMapper;
import ru.clevertec.checksystem.core.dto.discount.receiptitem.ReceiptItemDiscountDto;
import ru.clevertec.checksystem.core.dto.discount.receiptitem.SimplePercentageReceiptItemDiscountDto;
import ru.clevertec.checksystem.core.entity.discount.receiptitem.SimplePercentageReceiptItemDiscount;

public class SimplePercentageReceiptItemDiscountFromEntityToDtoConverter extends AbstractConverter<SimplePercentageReceiptItemDiscount, ReceiptItemDiscountDto> {

    private final ModelMapper modelMapper;

    public SimplePercentageReceiptItemDiscountFromEntityToDtoConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    protected SimplePercentageReceiptItemDiscountDto convert(SimplePercentageReceiptItemDiscount source) {
        return modelMapper.map(source, SimplePercentageReceiptItemDiscountDto.class);
    }
}
