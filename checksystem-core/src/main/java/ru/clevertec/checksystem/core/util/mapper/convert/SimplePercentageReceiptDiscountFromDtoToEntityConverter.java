package ru.clevertec.checksystem.core.util.mapper.convert;

import org.modelmapper.AbstractConverter;
import org.modelmapper.ModelMapper;
import ru.clevertec.checksystem.core.dto.discount.receipt.SimplePercentageReceiptDiscountDto;
import ru.clevertec.checksystem.core.entity.discount.receipt.ReceiptDiscount;
import ru.clevertec.checksystem.core.entity.discount.receipt.SimplePercentageReceiptDiscount;

public class SimplePercentageReceiptDiscountFromDtoToEntityConverter extends AbstractConverter<SimplePercentageReceiptDiscountDto, ReceiptDiscount> {

    private final ModelMapper modelMapper;

    public SimplePercentageReceiptDiscountFromDtoToEntityConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    protected ReceiptDiscount convert(SimplePercentageReceiptDiscountDto source) {
        return modelMapper.map(source, SimplePercentageReceiptDiscount.class);
    }
}
