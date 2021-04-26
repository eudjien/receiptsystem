package ru.clevertec.checksystem.core.util.mapper.convert;

import org.modelmapper.AbstractConverter;
import org.modelmapper.ModelMapper;
import ru.clevertec.checksystem.core.dto.discount.receipt.PercentageReceiptDiscountDto;
import ru.clevertec.checksystem.core.entity.discount.receipt.PercentageReceiptDiscount;
import ru.clevertec.checksystem.core.entity.discount.receipt.ReceiptDiscount;

public class PercentageReceiptDiscountFromDtoToEntityConverter extends AbstractConverter<PercentageReceiptDiscountDto, ReceiptDiscount> {

    private final ModelMapper modelMapper;

    public PercentageReceiptDiscountFromDtoToEntityConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    protected ReceiptDiscount convert(PercentageReceiptDiscountDto source) {
        return modelMapper.map(source, PercentageReceiptDiscount.class);
    }
}
