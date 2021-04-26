package ru.clevertec.checksystem.core.util.mapper.convert;

import org.modelmapper.AbstractConverter;
import org.modelmapper.ModelMapper;
import ru.clevertec.checksystem.core.dto.discount.receiptitem.PercentageReceiptItemDiscountDto;
import ru.clevertec.checksystem.core.entity.discount.receiptitem.PercentageReceiptItemDiscount;
import ru.clevertec.checksystem.core.entity.discount.receiptitem.ReceiptItemDiscount;

public class PercentageReceiptItemDiscountFromDtoToEntityConverter extends AbstractConverter<PercentageReceiptItemDiscountDto, ReceiptItemDiscount> {

    private final ModelMapper modelMapper;

    public PercentageReceiptItemDiscountFromDtoToEntityConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    protected ReceiptItemDiscount convert(PercentageReceiptItemDiscountDto source) {
        return modelMapper.map(source, PercentageReceiptItemDiscount.class);
    }
}
