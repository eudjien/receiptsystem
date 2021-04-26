package ru.clevertec.checksystem.core.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import ru.clevertec.checksystem.core.entity.EventEmail;

import java.util.List;
import java.util.Optional;

@Repository
public interface EventEmailRepository extends ApplicationRepository<EventEmail, Long> {

    List<EventEmail> findAllByEventType(String eventType);

    Page<EventEmail> findAllByEmailId(Long emailId, Pageable pageable);

    Optional<EventEmail> findByEmailIdAndEventType(Long emailId, String eventType);
}
