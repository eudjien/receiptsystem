package ru.clevertec.checksystem.core.util.mapper;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;
import ru.clevertec.checksystem.core.util.mapper.convert.*;

import java.util.List;

@Component
public class ApplicationModelMapper extends ModelMapper {

    public ApplicationModelMapper() {

        getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        addConverter(new ConstantReceiptDiscountFromEntityToDtoConverter(this));
        addConverter(new PercentageReceiptDiscountFromEntityToDtoConverter(this));

        addConverter(new ConstantReceiptItemDiscountFromEntityToDtoConverter(this));
        addConverter(new PercentageReceiptItemDiscountFromEntityToDtoConverter(this));
        addConverter(new ThresholdPercentageReceiptItemDiscountFromEntityToDtoConverter(this));

        addConverter(new ConstantReceiptDiscountFromDtoToEntityConverter(this));
        addConverter(new PercentageReceiptDiscountFromDtoToEntityConverter(this));

        addConverter(new ConstantReceiptItemDiscountFromDtoToEntityConverter(this));
        addConverter(new PercentageReceiptItemDiscountFromDtoToEntityConverter(this));
        addConverter(new ThresholdPercentageReceiptItemDiscountFromDtoToEntityConverter(this));
    }

    public <D, S> List<D> mapToList(Iterable<S> iterable) {
        return super.map(iterable, TypeToken.of(iterable.getClass()).getType());
    }
}
