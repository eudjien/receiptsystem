package ru.clevertec.checksystem.core.entity.discount.receiptitem;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import ru.clevertec.checksystem.core.common.receipt.IReceiptItemAggregable;
import ru.clevertec.checksystem.core.entity.discount.AbstractDiscount;
import ru.clevertec.checksystem.core.entity.receipt.ReceiptItem;
import ru.clevertec.checksystem.core.util.ThrowUtils;
import ru.clevertec.custom.json.StringifyIgnore;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static ru.clevertec.checksystem.core.Constants.Entities;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = Entities.Mapping.Table.RECEIPT_ITEM_DISCOUNTS)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING, length = 86)
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS)
public abstract class ReceiptItemDiscount extends AbstractDiscount<ReceiptItemDiscount> implements IReceiptItemAggregable {

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "discounts")
    @JsonIgnore
    private final Set<ReceiptItem> receiptItems = new HashSet<>();

    protected ReceiptItemDiscount() {
    }

    protected ReceiptItemDiscount(String description) {
        super(description);
    }

    protected ReceiptItemDiscount(String description, ReceiptItemDiscount dependentDiscount) {
        super(description, dependentDiscount);
    }

    @StringifyIgnore
    @Override
    public Collection<ReceiptItem> getReceiptItems() {
        return receiptItems;
    }

    @Override
    public void setReceiptItems(Collection<ReceiptItem> receiptItems) {
        clearReceiptItems();
        if (receiptItems != null)
            addReceiptItems(receiptItems);
    }

    @Override
    public void addReceiptItem(ReceiptItem receiptItem) {
        ThrowUtils.Argument.nullValue("receiptItem", receiptItem);
        receiptItem.getDiscounts().add(this);
        getReceiptItems().add(receiptItem);
    }

    @Override
    public void addReceiptItems(Collection<ReceiptItem> receiptItems) {
        ThrowUtils.Argument.nullValue("receiptItems", receiptItems);
        receiptItems.forEach(this::addReceiptItem);
    }

    @Override
    public void removeReceiptItem(ReceiptItem receiptItem) {
        ThrowUtils.Argument.nullValue("receiptItem", receiptItem);
        receiptItem.getDiscounts().remove(this);
        getReceiptItems().remove(receiptItem);
    }

    @Override
    public void removeReceiptItems(Collection<ReceiptItem> receiptItems) {
        ThrowUtils.Argument.nullValue("receiptItems", receiptItems);
        receiptItems.forEach(this::removeReceiptItem);
    }

    @Override
    public void clearReceiptItems() {
        getReceiptItems().forEach(receiptItem -> receiptItem.getDiscounts().remove(this));
        getReceiptItems().clear();
    }

    public abstract BigDecimal discountAmount(ReceiptItem receiptItem);
}
