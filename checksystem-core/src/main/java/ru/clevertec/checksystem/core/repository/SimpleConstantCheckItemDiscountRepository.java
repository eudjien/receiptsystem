package ru.clevertec.checksystem.core.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import ru.clevertec.checksystem.core.entity.discount.checkitem.SimpleConstantCheckItemDiscount;

@Repository
public interface SimpleConstantCheckItemDiscountRepository extends PagingAndSortingRepository<SimpleConstantCheckItemDiscount, Long> {
}
