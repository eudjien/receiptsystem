package ru.clevertec.checksystem.core.entity.receipt;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import ru.clevertec.checksystem.core.constant.Entities;
import ru.clevertec.checksystem.core.entity.BaseEntity;
import ru.clevertec.checksystem.core.entity.Product;
import ru.clevertec.checksystem.core.entity.discount.receiptitem.ReceiptItemDiscount;
import ru.clevertec.checksystem.core.util.ThrowUtils;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@SuperBuilder
@Jacksonized
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true, exclude = {"receipt"})
@Entity
@Table(
        name = Entities.Table.RECEIPT_ITEMS,
        indexes = @Index(columnList = Entities.JoinColumn.PRODUCT_ID + "," + Entities.JoinColumn.RECEIPT_ID, unique = true)
)
public class ReceiptItem extends BaseEntity {

    @JsonIgnore
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH}, fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = Entities.JoinColumn.RECEIPT_ID)
    private Receipt receipt;

    @Column(name = Entities.Column.QUANTITY, nullable = false)
    private Long quantity;

    @Column(name = Entities.JoinColumn.PRODUCT_ID, insertable = false, updatable = false, nullable = false)
    private Long productId;

    @Column(name = Entities.JoinColumn.RECEIPT_ID, insertable = false, updatable = false, nullable = false)
    private Long receiptId;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH}, fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = Entities.JoinColumn.PRODUCT_ID)
    private Product product;

    @Builder.Default
    @ManyToMany(cascade = {CascadeType.PERSIST}, fetch = FetchType.EAGER)
    @JoinTable(
            name = Entities.Table.RECEIPT_ITEM__RECEIPT_ITEM_DISCOUNT,
            joinColumns = @JoinColumn(name = Entities.JoinColumn.RECEIPT_ITEM_ID, referencedColumnName = Entities.Column.ID),
            inverseJoinColumns = @JoinColumn(name = Entities.JoinColumn.RECEIPT_ITEM_DISCOUNT_ID, referencedColumnName = Entities.Column.ID)
    )
    private Set<ReceiptItemDiscount> discounts = new HashSet<>();

    public void addDiscount(ReceiptItemDiscount receiptItemDiscount) {
        ThrowUtils.Argument.nullValue("receiptItemDiscount", receiptItemDiscount);
        getDiscounts().add(receiptItemDiscount);
        receiptItemDiscount.getReceiptItems().add(this);
    }

    public void removeDiscount(ReceiptItemDiscount receiptItemDiscount) {
        ThrowUtils.Argument.nullValue("receiptItemDiscount", receiptItemDiscount);
        getDiscounts().remove(receiptItemDiscount);
        receiptItemDiscount.getReceiptItems().remove(this);
    }

    public void clearDiscounts() {
        getDiscounts().forEach(discount -> discount.removeReceiptItem(this));
        getDiscounts().clear();
    }

    public void removeReceipt() {
        getReceipt().removeReceiptItem(this);
        setReceipt(null);
    }
}
