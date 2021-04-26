package ru.clevertec.checksystem.core.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import ru.clevertec.checksystem.core.constant.Entities;
import ru.clevertec.checksystem.core.entity.receipt.ReceiptItem;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Data
@SuperBuilder
@Jacksonized
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true, exclude = {"receiptItems"})
@Entity
@Table(
        name = Entities.Table.PRODUCTS,
        indexes = @Index(columnList = Entities.Column.NAME, unique = true)
)
public class Product extends BaseEntity {

    @Column(name = Entities.Column.NAME, nullable = false)
    private String name;

    @Column(name = Entities.Column.PRICE, nullable = false)
    private BigDecimal price;

    @Builder.Default
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "product")
    @JsonIgnore
    private Set<ReceiptItem> receiptItems = new HashSet<>();
}
