package ru.clevertec.checksystem.core.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import ru.clevertec.checksystem.core.entity.check.CheckItem;

@Repository
public interface CheckItemRepository extends PagingAndSortingRepository<CheckItem, Long> {
}
