package ru.clevertec.checksystem.core.util.mapper.convert;

import org.modelmapper.AbstractConverter;
import org.modelmapper.ModelMapper;
import ru.clevertec.checksystem.core.dto.discount.receiptitem.ConstantReceiptItemDiscountDto;
import ru.clevertec.checksystem.core.dto.discount.receiptitem.ReceiptItemDiscountDto;
import ru.clevertec.checksystem.core.entity.discount.receiptitem.ConstantReceiptItemDiscount;

public class ConstantReceiptItemDiscountFromEntityToDtoConverter extends AbstractConverter<ConstantReceiptItemDiscount, ReceiptItemDiscountDto> {

    private final ModelMapper modelMapper;

    public ConstantReceiptItemDiscountFromEntityToDtoConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    protected ConstantReceiptItemDiscountDto convert(ConstantReceiptItemDiscount source) {
        return modelMapper.map(source, ConstantReceiptItemDiscountDto.class);
    }
}
