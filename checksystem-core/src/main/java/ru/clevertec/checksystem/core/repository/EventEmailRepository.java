package ru.clevertec.checksystem.core.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import ru.clevertec.checksystem.core.entity.EventEmail;

@Repository
public interface EventEmailRepository extends PagingAndSortingRepository<EventEmail, Long> {
    Iterable<EventEmail> findAllByEventType(String eventType);
}
