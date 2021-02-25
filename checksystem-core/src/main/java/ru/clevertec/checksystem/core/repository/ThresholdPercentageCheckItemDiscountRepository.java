package ru.clevertec.checksystem.core.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import ru.clevertec.checksystem.core.entity.discount.checkitem.ThresholdPercentageCheckItemDiscount;

@Repository
public interface ThresholdPercentageCheckItemDiscountRepository extends PagingAndSortingRepository<ThresholdPercentageCheckItemDiscount, Long> {
}
