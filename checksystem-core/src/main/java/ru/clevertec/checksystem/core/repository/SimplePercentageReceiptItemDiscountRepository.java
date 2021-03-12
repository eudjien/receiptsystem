package ru.clevertec.checksystem.core.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import ru.clevertec.checksystem.core.entity.discount.receiptitem.SimplePercentageReceiptItemDiscount;

@Repository
public interface SimplePercentageReceiptItemDiscountRepository extends PagingAndSortingRepository<SimplePercentageReceiptItemDiscount, Long> {
}
