package ru.clevertec.checksystem.core.repository;

import org.springframework.stereotype.Repository;
import ru.clevertec.checksystem.core.entity.Email;

import java.util.Optional;

@Repository
public interface EmailRepository extends ApplicationRepository<Email, Long> {
    Optional<Email> findByAddress(String address);
}
