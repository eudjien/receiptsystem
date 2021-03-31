package ru.clevertec.checksystem.core.util.mapper.convert;

import org.modelmapper.AbstractConverter;
import org.modelmapper.ModelMapper;
import ru.clevertec.checksystem.core.dto.discount.receipt.SimpleConstantReceiptDiscountDto;
import ru.clevertec.checksystem.core.entity.discount.receipt.ReceiptDiscount;
import ru.clevertec.checksystem.core.entity.discount.receipt.SimpleConstantReceiptDiscount;

public class SimpleConstantReceiptDiscountFromDtoToEntityConverter extends AbstractConverter<SimpleConstantReceiptDiscountDto, ReceiptDiscount> {

    private final ModelMapper modelMapper;

    public SimpleConstantReceiptDiscountFromDtoToEntityConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    protected ReceiptDiscount convert(SimpleConstantReceiptDiscountDto source) {
        return modelMapper.map(source, SimpleConstantReceiptDiscount.class);
    }
}
