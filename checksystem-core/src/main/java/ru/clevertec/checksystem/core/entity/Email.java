package ru.clevertec.checksystem.core.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ru.clevertec.checksystem.core.constant.Entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = Entities.Table.EMAILS, indexes = @Index(columnList = Entities.Column.ADDRESS, unique = true))
public class Email extends BaseEntity {

    @Column(name = Entities.Column.ADDRESS, nullable = false)
    private String address;
}
