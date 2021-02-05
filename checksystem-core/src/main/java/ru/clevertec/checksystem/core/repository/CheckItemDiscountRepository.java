package ru.clevertec.checksystem.core.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import ru.clevertec.checksystem.core.entity.discount.checkitem.CheckItemDiscount;

@Repository
public interface CheckItemDiscountRepository extends PagingAndSortingRepository<CheckItemDiscount, Long> {
}
