package ru.clevertec.checksystem.core.util.mapper.convert;

import org.modelmapper.AbstractConverter;
import org.modelmapper.ModelMapper;
import ru.clevertec.checksystem.core.dto.discount.receiptitem.ConstantReceiptItemDiscountDto;
import ru.clevertec.checksystem.core.entity.discount.receiptitem.ConstantReceiptItemDiscount;
import ru.clevertec.checksystem.core.entity.discount.receiptitem.ReceiptItemDiscount;

public class ConstantReceiptItemDiscountFromDtoToEntityConverter extends AbstractConverter<ConstantReceiptItemDiscountDto, ReceiptItemDiscount> {

    private final ModelMapper modelMapper;

    public ConstantReceiptItemDiscountFromDtoToEntityConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    protected ReceiptItemDiscount convert(ConstantReceiptItemDiscountDto source) {
        return modelMapper.map(source, ConstantReceiptItemDiscount.class);
    }
}
