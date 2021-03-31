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

        addConverter(new SimpleConstantReceiptDiscountFromEntityToDtoConverter(this));
        addConverter(new SimplePercentageReceiptDiscountFromEntityToDtoConverter(this));

        addConverter(new SimpleConstantReceiptItemDiscountFromEntityToDtoConverter(this));
        addConverter(new SimplePercentageReceiptItemDiscountFromEntityToDtoConverter(this));
        addConverter(new ThresholdPercentageReceiptItemDiscountFromEntityToDtoConverter(this));

        addConverter(new SimpleConstantReceiptDiscountFromDtoToEntityConverter(this));
        addConverter(new SimplePercentageReceiptDiscountFromDtoToEntityConverter(this));

        addConverter(new SimpleConstantReceiptItemDiscountFromDtoToEntityConverter(this));
        addConverter(new SimplePercentageReceiptItemDiscountFromDtoToEntityConverter(this));
        addConverter(new ThresholdPercentageReceiptItemDiscountFromDtoToEntityConverter(this));
    }

    public <D, S> List<D> mapToList(Iterable<S> iterable) {
        return super.map(iterable, TypeToken.of(iterable.getClass()).getType());
    }
}
