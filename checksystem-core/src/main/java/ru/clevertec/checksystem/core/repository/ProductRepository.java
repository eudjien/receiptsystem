package ru.clevertec.checksystem.core.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.clevertec.checksystem.core.entity.Product;

import java.util.Optional;

@Repository
public interface ProductRepository extends ApplicationRepository<Product, Long> {

    @Query("SELECT p FROM ReceiptItem i JOIN i.product p WHERE i.id = ?1")
    Optional<Product> findByReceiptItemId(Long receiptItemId);

    @Query("SELECT p FROM Receipt r JOIN r.receiptItems i JOIN i.product p WHERE r.id = ?1 AND i.id = ?2")
    Optional<Product> findByReceiptIdAndReceiptItemId(Long receiptId, Long receiptItemId);
}
