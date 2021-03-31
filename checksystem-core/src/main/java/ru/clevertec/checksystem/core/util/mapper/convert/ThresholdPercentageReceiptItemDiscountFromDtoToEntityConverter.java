package ru.clevertec.checksystem.core.util.mapper.convert;

import org.modelmapper.AbstractConverter;
import org.modelmapper.ModelMapper;
import ru.clevertec.checksystem.core.dto.discount.receiptitem.ThresholdPercentageReceiptItemDiscountDto;
import ru.clevertec.checksystem.core.entity.discount.receiptitem.ReceiptItemDiscount;
import ru.clevertec.checksystem.core.entity.discount.receiptitem.ThresholdPercentageReceiptItemDiscount;

public class ThresholdPercentageReceiptItemDiscountFromDtoToEntityConverter extends AbstractConverter<ThresholdPercentageReceiptItemDiscountDto, ReceiptItemDiscount> {

    private final ModelMapper modelMapper;

    public ThresholdPercentageReceiptItemDiscountFromDtoToEntityConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    protected ReceiptItemDiscount convert(ThresholdPercentageReceiptItemDiscountDto source) {
        return modelMapper.map(source, ThresholdPercentageReceiptItemDiscount.class);
    }
}
