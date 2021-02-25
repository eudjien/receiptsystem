package ru.clevertec.checksystem.core.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import ru.clevertec.checksystem.core.entity.Product;

@Repository
public interface ProductRepository extends PagingAndSortingRepository<Product, Long> {
}
