package ru.clevertec.checksystem.core.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import ru.clevertec.checksystem.core.entity.discount.receiptitem.ReceiptItemDiscount;

@Repository
public interface ReceiptItemDiscountRepository extends PagingAndSortingRepository<ReceiptItemDiscount, Long> {
}
