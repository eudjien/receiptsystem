package ru.clevertec.checksystem.core.entity.discount.receiptitem;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import ru.clevertec.checksystem.core.constant.Entities;
import ru.clevertec.checksystem.core.entity.discount.AbstractDiscount;
import ru.clevertec.checksystem.core.entity.receipt.ReceiptItem;
import ru.clevertec.checksystem.core.util.ThrowUtils;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = true, exclude = {"receiptItems"})
@NoArgsConstructor
@SuperBuilder
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = Entities.Table.RECEIPT_ITEM_DISCOUNTS)
@DiscriminatorColumn(name = Entities.DiscriminatorNames.RECEIPT_ITEM_DISCOUNT, discriminatorType = DiscriminatorType.STRING, length = 86)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME)
public abstract class ReceiptItemDiscount extends AbstractDiscount<ReceiptItemDiscount> {

    @Builder.Default
    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "discounts")
    private Set<ReceiptItem> receiptItems = new HashSet<>();

    public void addReceiptItem(ReceiptItem receiptItem) {
        ThrowUtils.Argument.nullValue("receiptItem", receiptItem);
        getReceiptItems().add(receiptItem);
        receiptItem.getDiscounts().add(this);
    }

    public void removeReceiptItem(ReceiptItem receiptItem) {
        ThrowUtils.Argument.nullValue("receiptItem", receiptItem);
        getReceiptItems().remove(receiptItem);
        receiptItem.getDiscounts().remove(this);
    }
}
