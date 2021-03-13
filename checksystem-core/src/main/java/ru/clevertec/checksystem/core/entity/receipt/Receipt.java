package ru.clevertec.checksystem.core.entity.receipt;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import ru.clevertec.checksystem.core.common.builder.IReceiptBuilder;
import ru.clevertec.checksystem.core.common.discount.IDiscountable;
import ru.clevertec.checksystem.core.common.receipt.IReceiptItemAggregable;
import ru.clevertec.checksystem.core.entity.BaseEntity;
import ru.clevertec.checksystem.core.entity.discount.receipt.ReceiptDiscount;
import ru.clevertec.checksystem.core.util.ThrowUtils;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static ru.clevertec.checksystem.core.Constants.Entities;

@Entity
@Table(
        name = Entities.Mapping.Table.RECEIPTS,
        indexes = @Index(columnList = Entities.Mapping.Column.NAME, unique = true)
)
public class Receipt extends BaseEntity implements IDiscountable<ReceiptDiscount>, IReceiptItemAggregable {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "receipt", fetch = FetchType.EAGER)
    private final Set<ReceiptItem> receiptItems = new HashSet<>();

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = Entities.Mapping.Table.RECEIPT__RECEIPT_DISCOUNT,
            joinColumns = @JoinColumn(
                    name = Entities.Mapping.JoinColumn.RECEIPT_ID,
                    referencedColumnName = Entities.Mapping.Column.ID),
            inverseJoinColumns = @JoinColumn(
                    name = Entities.Mapping.JoinColumn.RECEIPT_DISCOUNT_ID,
                    referencedColumnName = Entities.Mapping.Column.ID)
    )
    private final Set<ReceiptDiscount> discounts = new HashSet<>();

    @Column(name = Entities.Mapping.Column.NAME, nullable = false)
    private String name;

    @Column(name = Entities.Mapping.Column.DESCRIPTION, nullable = false)
    private String description;

    @Column(name = Entities.Mapping.Column.ADDRESS, nullable = false)
    private String address;

    @Column(name = Entities.Mapping.Column.PHONE_NUMBER)
    private String phoneNumber;

    @Column(name = Entities.Mapping.Column.CASHIER, nullable = false)
    private String cashier;

    @Column(name = Entities.Mapping.Column.DATE, nullable = false)
    private Date date;

    //@JsonCreator(mode = JsonCreator.Mode.DISABLED)
    public Receipt() {
    }

    //@JsonCreator(mode = JsonCreator.Mode.DISABLED)
    public Receipt(
            String name,
            String description,
            String address,
            String phoneNumber,
            String cashier,
            Date date) {
        setName(name);
        setDescription(description);
        setAddress(address);
        setPhoneNumber(phoneNumber);
        setCashier(cashier);
        setDate(date);
    }

    @JsonCreator
    public Receipt(
            @JsonProperty("id") Long id,
            @JsonProperty("name") String name,
            @JsonProperty("description") String description,
            @JsonProperty("address") String address,
            @JsonProperty("phoneNumber") String phoneNumber,
            @JsonProperty("cashier") String cashier,
            @JsonProperty("date") Date date,
            @JsonProperty("receiptItems") Collection<ReceiptItem> receiptItems,
            @JsonProperty("discounts") Collection<ReceiptDiscount> discounts) {
        this(name, description, address, phoneNumber, cashier, date);
        setId(id);
        setReceiptItems(receiptItems);
        setDiscounts(discounts);
    }

    public String getCashier() {
        return cashier;
    }

    public void setCashier(String cashier) {
        this.cashier = cashier;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

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
        receiptItem.setReceipt(this);
        getReceiptItems().add(receiptItem);
    }

    @Override
    public void addReceiptItems(Collection<ReceiptItem> receiptItems) {
        receiptItems.forEach(this::addReceiptItem);
    }

    @Override
    public void removeReceiptItem(ReceiptItem receiptItem) {
        ThrowUtils.Argument.nullValue("receiptItem", receiptItem);
        receiptItem.setReceipt(null);
        getReceiptItems().remove(receiptItem);
    }

    @Override
    public void removeReceiptItems(Collection<ReceiptItem> receiptItems) {
        ThrowUtils.Argument.nullValue("receiptItem", receiptItems);
        receiptItems.forEach(this::removeReceiptItem);
    }

    @Override
    public void clearReceiptItems() {
        getReceiptItems().forEach(receiptItem -> receiptItem.setReceipt(null));
        getReceiptItems().clear();
    }

    @Override
    public Collection<ReceiptDiscount> getDiscounts() {
        return discounts;
    }

    @Override
    public void setDiscounts(Collection<ReceiptDiscount> discounts) {
        clearDiscounts();
        if (discounts != null)
            addDiscounts(discounts);
    }

    @Override
    public void addDiscount(ReceiptDiscount discount) {
        ThrowUtils.Argument.nullValue("discount", discount);
        discount.getReceipts().add(this);
        getDiscounts().add(discount);
    }

    @Override
    public void addDiscounts(Collection<ReceiptDiscount> discounts) {
        ThrowUtils.Argument.nullValue("discounts", discounts);
        discounts.forEach(this::addDiscount);
    }

    @Override
    public void removeDiscount(ReceiptDiscount discount) {
        ThrowUtils.Argument.nullValue("discount", discount);
        discount.getReceipts().remove(this);
        getDiscounts().remove(discount);
    }

    @Override
    public void removeDiscounts(Collection<ReceiptDiscount> discounts) {
        ThrowUtils.Argument.nullValue("discounts", discounts);
        discounts.forEach(this::removeDiscount);
    }

    @Override
    public void clearDiscounts() {
        getDiscounts().forEach(discount -> discount.getReceipts().remove(this));
        getDiscounts().clear();
    }

    public BigDecimal subTotalAmount() {
        return receiptItems.stream()
                .map(a -> a.getProduct().getPrice().multiply(BigDecimal.valueOf(a.getQuantity())))
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
    }

    public BigDecimal totalAmount() {
        return subTotalAmount().subtract(discountsAmount());
    }

    @Override
    public BigDecimal discountsAmount() {
        return discountWithoutItemsAmount().add(itemsDiscountSum());
    }

    public BigDecimal discountWithoutItemsAmount() {
        return getDiscounts().stream()
                .map(discount -> discount.discountAmount(this))
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
    }

    public BigDecimal itemsDiscountSum() {
        return getReceiptItems().stream()
                .map(ReceiptItem::discountsAmount)
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
    }

    public static class Builder implements IReceiptBuilder {

        private final Receipt receipt = new Receipt();

        @Override
        public IReceiptBuilder setId(Long id) {
            receipt.setId(id);
            return this;
        }

        @Override
        public IReceiptBuilder setName(String name) {
            receipt.setName(name);
            return this;
        }

        @Override
        public IReceiptBuilder setDescription(String description) {
            receipt.setDescription(description);
            return this;
        }

        @Override
        public IReceiptBuilder setAddress(String address) {
            receipt.setAddress(address);
            return this;
        }

        @Override
        public IReceiptBuilder setPhoneNumber(String phoneNumber) {
            receipt.setPhoneNumber(phoneNumber);
            return this;
        }

        @Override
        public IReceiptBuilder setCashier(String cashier) {
            receipt.setCashier(cashier);
            return this;
        }

        @Override
        public IReceiptBuilder setDate(Date date) {
            receipt.setDate(date);
            return this;
        }

        @Override
        public IReceiptBuilder setItems(Collection<ReceiptItem> receiptItems) {
            receipt.setReceiptItems(receiptItems);
            return this;
        }

        @Override
        public IReceiptBuilder addItem(ReceiptItem receiptItem) {
            receipt.addReceiptItem(receiptItem);
            return this;
        }

        @Override
        public IReceiptBuilder addItems(Collection<ReceiptItem> receiptItems) {
            receipt.addReceiptItems(receiptItems);
            return this;
        }

        @Override
        public IReceiptBuilder setDiscounts(Collection<ReceiptDiscount> receiptDiscounts) {
            receipt.addDiscounts(receiptDiscounts);
            return this;
        }

        @Override
        public IReceiptBuilder addDiscount(ReceiptDiscount checkDiscount) {
            receipt.addDiscount(checkDiscount);
            return this;
        }

        @Override
        public IReceiptBuilder addDiscounts(Collection<ReceiptDiscount> receiptDiscounts) {
            receipt.addDiscounts(receiptDiscounts);
            return this;
        }

        @Override
        public Receipt build() {
            return this.receipt;
        }
    }
}
