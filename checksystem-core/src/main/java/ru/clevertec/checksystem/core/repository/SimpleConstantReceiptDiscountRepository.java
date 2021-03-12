package ru.clevertec.checksystem.core.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import ru.clevertec.checksystem.core.entity.discount.receipt.SimpleConstantReceiptDiscount;

@Repository
public interface SimpleConstantReceiptDiscountRepository extends PagingAndSortingRepository<SimpleConstantReceiptDiscount, Long> {
}
