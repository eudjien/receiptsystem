package ru.clevertec.checksystem.core.util.mapper.convert;

import org.modelmapper.AbstractConverter;
import org.modelmapper.ModelMapper;
import ru.clevertec.checksystem.core.dto.discount.receiptitem.ReceiptItemDiscountDto;
import ru.clevertec.checksystem.core.dto.discount.receiptitem.ThresholdPercentageReceiptItemDiscountDto;
import ru.clevertec.checksystem.core.entity.discount.receiptitem.ThresholdPercentageReceiptItemDiscount;

public class ThresholdPercentageReceiptItemDiscountFromEntityToDtoConverter extends AbstractConverter<ThresholdPercentageReceiptItemDiscount, ReceiptItemDiscountDto> {

    private final ModelMapper modelMapper;

    public ThresholdPercentageReceiptItemDiscountFromEntityToDtoConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    protected ThresholdPercentageReceiptItemDiscountDto convert(ThresholdPercentageReceiptItemDiscount source) {
        return modelMapper.map(source, ThresholdPercentageReceiptItemDiscountDto.class);
    }
}
