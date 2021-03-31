package ru.clevertec.checksystem.core.util.mapper.convert;

import org.modelmapper.AbstractConverter;
import org.modelmapper.ModelMapper;
import ru.clevertec.checksystem.core.dto.discount.receiptitem.SimpleConstantReceiptItemDiscountDto;
import ru.clevertec.checksystem.core.entity.discount.receiptitem.ReceiptItemDiscount;
import ru.clevertec.checksystem.core.entity.discount.receiptitem.SimpleConstantReceiptItemDiscount;

public class SimpleConstantReceiptItemDiscountFromDtoToEntityConverter extends AbstractConverter<SimpleConstantReceiptItemDiscountDto, ReceiptItemDiscount> {

    private final ModelMapper modelMapper;

    public SimpleConstantReceiptItemDiscountFromDtoToEntityConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    protected ReceiptItemDiscount convert(SimpleConstantReceiptItemDiscountDto source) {
        return modelMapper.map(source, SimpleConstantReceiptItemDiscount.class);
    }
}
