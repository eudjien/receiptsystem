package ru.clevertec.checksystem.core.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import ru.clevertec.checksystem.core.entity.receipt.ReceiptItem;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReceiptItemRepository extends ApplicationRepository<ReceiptItem, Long> {

    Page<ReceiptItem> findAllByProductId(Pageable pageable, Long productId);

    List<ReceiptItem> findAllByProductId(Long productId);

    List<ReceiptItem> findAllByProductId(Sort sort, Long productId);

    Page<ReceiptItem> findAllByReceiptId(Pageable pageable, Long receiptId);

    List<ReceiptItem> findAllByReceiptId(Long receiptId);

    List<ReceiptItem> findAllByReceiptId(Sort sort, Long receiptId);

    Optional<ReceiptItem> findByIdAndReceiptId(Long id, Long receiptId);
}
