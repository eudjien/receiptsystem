package ru.clevertec.checksystem.core.entity.receipt;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import ru.clevertec.checksystem.core.constant.Entities;
import ru.clevertec.checksystem.core.entity.BaseEntity;
import ru.clevertec.checksystem.core.entity.discount.receipt.ReceiptDiscount;
import ru.clevertec.checksystem.core.util.ThrowUtils;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Jacksonized
@SuperBuilder
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(
        name = Entities.Table.RECEIPTS,
        indexes = @Index(columnList = Entities.Column.NAME, unique = true)
)
public class Receipt extends BaseEntity {

    @Column(name = Entities.Column.NAME, nullable = false)
    private String name;

    @Column(name = Entities.Column.DESCRIPTION, nullable = false)
    private String description;

    @Column(name = Entities.Column.ADDRESS, nullable = false)
    private String address;

    @Column(name = Entities.Column.PHONE_NUMBER)
    private String phoneNumber;

    @Column(name = Entities.Column.CASHIER, nullable = false)
    private String cashier;

    @Column(name = Entities.Column.DATE, nullable = false)
    private Date date;

    @Builder.Default
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "receipt", fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<ReceiptItem> receiptItems = new HashSet<>();

    @Builder.Default
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @JoinTable(
            name = Entities.Table.RECEIPT__RECEIPT_DISCOUNT,
            joinColumns = @JoinColumn(name = Entities.JoinColumn.RECEIPT_ID, referencedColumnName = Entities.Column.ID),
            inverseJoinColumns = @JoinColumn(name = Entities.JoinColumn.RECEIPT_DISCOUNT_ID, referencedColumnName = Entities.Column.ID)
    )
    private Set<ReceiptDiscount> discounts = new HashSet<>();

    public void addDiscount(ReceiptDiscount receiptDiscount) {
        ThrowUtils.Argument.nullValue("receiptDiscount", receiptDiscount);
        getDiscounts().add(receiptDiscount);
        receiptDiscount.getReceipts().add(this);
    }

    public void removeDiscount(ReceiptDiscount receiptDiscount) {
        ThrowUtils.Argument.nullValue("receiptDiscount", receiptDiscount);
        getDiscounts().remove(receiptDiscount);
        receiptDiscount.getReceipts().remove(this);
    }

    public void clearDiscounts() {
        getDiscounts().forEach(discount -> discount.removeReceipt(this));
        getDiscounts().clear();
    }

    public void addReceiptItem(ReceiptItem receiptItem) {
        ThrowUtils.Argument.nullValue("receiptItem", receiptItem);
        getReceiptItems().add(receiptItem);
        receiptItem.setReceipt(this);
    }

    public void removeReceiptItem(ReceiptItem receiptItem) {
        ThrowUtils.Argument.nullValue("receiptItem", receiptItem);
        getReceiptItems().remove(receiptItem);
        //receiptItem.setReceipt(null);
    }
}
