package ru.clevertec.checksystem.core.util.mapper.convert;

import org.modelmapper.AbstractConverter;
import org.modelmapper.ModelMapper;
import ru.clevertec.checksystem.core.dto.discount.receipt.ConstantReceiptDiscountDto;
import ru.clevertec.checksystem.core.entity.discount.receipt.ConstantReceiptDiscount;
import ru.clevertec.checksystem.core.entity.discount.receipt.ReceiptDiscount;

public class ConstantReceiptDiscountFromDtoToEntityConverter extends AbstractConverter<ConstantReceiptDiscountDto, ReceiptDiscount> {

    private final ModelMapper modelMapper;

    public ConstantReceiptDiscountFromDtoToEntityConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    protected ReceiptDiscount convert(ConstantReceiptDiscountDto source) {
        return modelMapper.map(source, ConstantReceiptDiscount.class);
    }
}
