package ru.clevertec.checksystem.core.util.mapper.convert;

import org.modelmapper.AbstractConverter;
import org.modelmapper.ModelMapper;
import ru.clevertec.checksystem.core.dto.discount.receiptitem.PercentageReceiptItemDiscountDto;
import ru.clevertec.checksystem.core.dto.discount.receiptitem.ReceiptItemDiscountDto;
import ru.clevertec.checksystem.core.entity.discount.receiptitem.PercentageReceiptItemDiscount;

public class PercentageReceiptItemDiscountFromEntityToDtoConverter extends AbstractConverter<PercentageReceiptItemDiscount, ReceiptItemDiscountDto> {

    private final ModelMapper modelMapper;

    public PercentageReceiptItemDiscountFromEntityToDtoConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    protected PercentageReceiptItemDiscountDto convert(PercentageReceiptItemDiscount source) {
        return modelMapper.map(source, PercentageReceiptItemDiscountDto.class);
    }
}
