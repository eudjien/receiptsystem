package ru.clevertec.checksystem.core.entity;

import javax.persistence.*;

import static ru.clevertec.checksystem.core.Constants.Entities;

@MappedSuperclass
public abstract class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = Entities.Mapping.Column.ID)
    private Long id;

    public BaseEntity() {
    }

    public BaseEntity(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
