package ru.clevertec.checksystem.core.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ru.clevertec.checksystem.core.constant.Entities;

import javax.persistence.*;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(
        name = Entities.Table.EVENT_EMAILS,
        indexes = @Index(columnList = Entities.JoinColumn.EMAIL_ID + "," + Entities.Column.EVENT_TYPE, unique = true)
)
public class EventEmail extends BaseEntity {

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = Entities.JoinColumn.EMAIL_ID, referencedColumnName = Entities.Column.ID, nullable = false)
    private Email email;

    @Column(name = Entities.Column.EVENT_TYPE, nullable = false)
    private String eventType;

    @Column(name = Entities.JoinColumn.EMAIL_ID, insertable = false, updatable = false)
    private Long emailId;
}
