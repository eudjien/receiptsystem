package ru.clevertec.checksystem.core.util.mapper.convert;

import org.modelmapper.AbstractConverter;
import org.modelmapper.ModelMapper;
import ru.clevertec.checksystem.core.dto.discount.receiptitem.SimplePercentageReceiptItemDiscountDto;
import ru.clevertec.checksystem.core.entity.discount.receiptitem.ReceiptItemDiscount;
import ru.clevertec.checksystem.core.entity.discount.receiptitem.SimplePercentageReceiptItemDiscount;

public class SimplePercentageReceiptItemDiscountFromDtoToEntityConverter extends AbstractConverter<SimplePercentageReceiptItemDiscountDto, ReceiptItemDiscount> {

    private final ModelMapper modelMapper;

    public SimplePercentageReceiptItemDiscountFromDtoToEntityConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    protected ReceiptItemDiscount convert(SimplePercentageReceiptItemDiscountDto source) {
        return modelMapper.map(source, SimplePercentageReceiptItemDiscount.class);
    }
}
