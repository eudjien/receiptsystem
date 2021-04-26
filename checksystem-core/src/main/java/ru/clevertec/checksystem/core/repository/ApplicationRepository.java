package ru.clevertec.checksystem.core.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean
public interface ApplicationRepository<T, ID> extends JpaRepository<T, ID> {

    List<T> findAllByIdIn(Sort sort, Iterable<ID> ids);

    Page<T> findAllByIdIn(Pageable pageable, Iterable<ID> ids);
}
