package ru.clevertec.checksystem.core.configuration;

import org.modelmapper.AbstractConverter;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;
import ru.clevertec.checksystem.core.dto.discount.check.CheckDiscountDto;
import ru.clevertec.checksystem.core.dto.discount.check.SimpleConstantCheckDiscountDto;
import ru.clevertec.checksystem.core.dto.discount.check.SimplePercentageCheckDiscountDto;
import ru.clevertec.checksystem.core.dto.discount.checkitem.CheckItemDiscountDto;
import ru.clevertec.checksystem.core.dto.discount.checkitem.SimpleConstantCheckItemDiscountDto;
import ru.clevertec.checksystem.core.dto.discount.checkitem.SimplePercentageCheckItemDiscountDto;
import ru.clevertec.checksystem.core.dto.discount.checkitem.ThresholdPercentageCheckItemDiscountDto;
import ru.clevertec.checksystem.core.entity.discount.check.SimpleConstantCheckDiscount;
import ru.clevertec.checksystem.core.entity.discount.check.SimplePercentageCheckDiscount;
import ru.clevertec.checksystem.core.entity.discount.checkitem.SimpleConstantCheckItemDiscount;
import ru.clevertec.checksystem.core.entity.discount.checkitem.SimplePercentageCheckItemDiscount;
import ru.clevertec.checksystem.core.entity.discount.checkitem.ThresholdPercentageCheckItemDiscount;

@Component
public class ApplicationModelMapper extends ModelMapper {

    public ApplicationModelMapper() {
        getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        addCheckDiscountConverters(this);
        addCheckItemDiscountConverters(this);
    }

    private static void addCheckDiscountConverters(ModelMapper modelMapper) {
        modelMapper.addConverter(new AbstractConverter<SimplePercentageCheckDiscount, CheckDiscountDto>() {
            @Override
            protected SimplePercentageCheckDiscountDto convert(SimplePercentageCheckDiscount source) {
                return modelMapper.map(source, SimplePercentageCheckDiscountDto.class);
            }
        });
        modelMapper.addConverter(new AbstractConverter<SimpleConstantCheckDiscount, CheckDiscountDto>() {
            @Override
            protected SimpleConstantCheckDiscountDto convert(SimpleConstantCheckDiscount source) {
                return modelMapper.map(source, SimpleConstantCheckDiscountDto.class);
            }
        });
    }

    private static void addCheckItemDiscountConverters(ModelMapper modelMapper) {
        modelMapper.addConverter(new AbstractConverter<SimplePercentageCheckItemDiscount, CheckItemDiscountDto>() {
            @Override
            protected SimplePercentageCheckItemDiscountDto convert(SimplePercentageCheckItemDiscount source) {
                return modelMapper.map(source, SimplePercentageCheckItemDiscountDto.class);
            }
        });
        modelMapper.addConverter(new AbstractConverter<ThresholdPercentageCheckItemDiscount, CheckItemDiscountDto>() {
            @Override
            protected ThresholdPercentageCheckItemDiscountDto convert(ThresholdPercentageCheckItemDiscount source) {
                return modelMapper.map(source, ThresholdPercentageCheckItemDiscountDto.class);
            }
        });
        modelMapper.addConverter(new AbstractConverter<SimpleConstantCheckItemDiscount, CheckItemDiscountDto>() {
            @Override
            protected SimpleConstantCheckItemDiscountDto convert(SimpleConstantCheckItemDiscount source) {
                return modelMapper.map(source, SimpleConstantCheckItemDiscountDto.class);
            }
        });
    }
}
