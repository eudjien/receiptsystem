package ru.clevertec.checksystem.core.util.mapper.convert;

import org.modelmapper.AbstractConverter;
import org.modelmapper.ModelMapper;
import ru.clevertec.checksystem.core.dto.discount.receiptitem.ReceiptItemDiscountDto;
import ru.clevertec.checksystem.core.dto.discount.receiptitem.SimpleConstantReceiptItemDiscountDto;
import ru.clevertec.checksystem.core.entity.discount.receiptitem.SimpleConstantReceiptItemDiscount;

public class SimpleConstantReceiptItemDiscountFromEntityToDtoConverter extends AbstractConverter<SimpleConstantReceiptItemDiscount, ReceiptItemDiscountDto> {

    private final ModelMapper modelMapper;

    public SimpleConstantReceiptItemDiscountFromEntityToDtoConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    protected SimpleConstantReceiptItemDiscountDto convert(SimpleConstantReceiptItemDiscount source) {
        return modelMapper.map(source, SimpleConstantReceiptItemDiscountDto.class);
    }
}
