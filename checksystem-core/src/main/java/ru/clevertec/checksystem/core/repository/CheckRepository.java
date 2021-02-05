package ru.clevertec.checksystem.core.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import ru.clevertec.checksystem.core.entity.check.Check;

@Repository
public interface CheckRepository extends PagingAndSortingRepository<Check, Long> {
}
