package ru.clevertec.checksystem.core.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import ru.clevertec.checksystem.core.entity.receipt.ReceiptItem;

@Repository
public interface ReceiptItemRepository extends PagingAndSortingRepository<ReceiptItem, Long> {
}
