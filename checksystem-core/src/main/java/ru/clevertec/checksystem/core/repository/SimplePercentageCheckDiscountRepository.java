package ru.clevertec.checksystem.core.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import ru.clevertec.checksystem.core.entity.discount.check.SimplePercentageCheckDiscount;

@Repository
public interface SimplePercentageCheckDiscountRepository extends PagingAndSortingRepository<SimplePercentageCheckDiscount, Long> {
}
