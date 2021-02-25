package ru.clevertec.checksystem.core.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import ru.clevertec.checksystem.core.entity.discount.check.CheckDiscount;

@Repository
public interface CheckDiscountRepository extends PagingAndSortingRepository<CheckDiscount, Long> {
}
