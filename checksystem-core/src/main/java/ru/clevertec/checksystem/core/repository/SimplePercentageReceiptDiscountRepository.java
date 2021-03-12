package ru.clevertec.checksystem.core.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import ru.clevertec.checksystem.core.entity.discount.receipt.SimplePercentageReceiptDiscount;

@Repository
public interface SimplePercentageReceiptDiscountRepository extends PagingAndSortingRepository<SimplePercentageReceiptDiscount, Long> {
}
