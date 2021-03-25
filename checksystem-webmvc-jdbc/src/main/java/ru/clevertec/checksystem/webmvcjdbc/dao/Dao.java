package ru.clevertec.checksystem.webmvcjdbc.dao;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.Collection;
import java.util.List;

public abstract class Dao<T, K> {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public Dao(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public NamedParameterJdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public abstract T findById(K id);

    public abstract List<T> findAll();

    public abstract List<T> findAllById(Collection<K> ids);

    public abstract int delete(T entity);

    public abstract int deleteById(K id);

    public abstract <S extends T> int deleteAll(Collection<S> entities);

    public abstract int deleteAll();

    public abstract T update(T entity);

    public abstract <S extends T> List<T> updateAll(Collection<S> entities);

    public abstract T add(T entity);

    public abstract <S extends T> List<T> addAll(Collection<S> entities);
}
