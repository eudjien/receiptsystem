package ru.clevertec.checksystem.core.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.clevertec.checksystem.core.entity.discount.receiptitem.ReceiptItemDiscount;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReceiptItemDiscountRepository extends ApplicationRepository<ReceiptItemDiscount, Long> {

    @Query("SELECT d FROM ReceiptItem ri JOIN ri.discounts d WHERE ri.id = ?1")
    List<ReceiptItemDiscount> findAllByReceiptItemId(Long receiptItemId);

    @Query("SELECT d FROM ReceiptItem ri JOIN ri.discounts d WHERE ri.id = ?1")
    Page<ReceiptItemDiscount> findAllByReceiptItemId(Long receiptItemId, Pageable pageable);

    @Query("SELECT d FROM ReceiptItem ri JOIN ri.discounts d WHERE d.id = ?1 AND ri.id = ?2")
    Optional<ReceiptItemDiscount> findByIdAndReceiptItemId(Long id, Long receiptItemId);
}
