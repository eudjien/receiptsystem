package ru.clevertec.checksystem.core.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import ru.clevertec.checksystem.core.entity.discount.receiptitem.SimpleConstantReceiptItemDiscount;

@Repository
public interface SimpleConstantReceiptItemDiscountRepository extends PagingAndSortingRepository<SimpleConstantReceiptItemDiscount, Long> {
}
