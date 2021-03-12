package ru.clevertec.checksystem.core.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import ru.clevertec.checksystem.core.entity.receipt.Receipt;

@Repository
public interface ReceiptRepository extends PagingAndSortingRepository<Receipt, Long> {
}
