package ru.clevertec.checksystem.core.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.clevertec.checksystem.core.entity.discount.receipt.ReceiptDiscount;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReceiptDiscountRepository extends ApplicationRepository<ReceiptDiscount, Long> {

    @Query("SELECT d FROM Receipt r JOIN r.discounts d WHERE r.id = ?1")
    List<ReceiptDiscount> findAllByReceiptId(Long receiptId);

    @Query("SELECT d FROM Receipt r JOIN r.discounts d WHERE r.id = ?1")
    List<ReceiptDiscount> findAllByReceiptId(Long receiptId, Sort sort);

    @Query("SELECT d FROM Receipt r JOIN r.discounts d WHERE r.id = ?1")
    Page<ReceiptDiscount> findAllByReceiptId(Long receiptId, Pageable pageable);

    @Query("SELECT d FROM Receipt r JOIN r.discounts d WHERE d.id = ?1 AND r.id = ?2 ")
    Optional<ReceiptDiscount> findByIdAndReceiptId(Long id, Long receiptId);

    @Query("SELECT CASE WHEN (COUNT(d) > 0) THEN true ELSE false END FROM Receipt r JOIN r.discounts d WHERE d.id = ?1 AND r.id = ?2 ")
    boolean existsByIdAndReceiptId(Long id, Long receiptId);
}
