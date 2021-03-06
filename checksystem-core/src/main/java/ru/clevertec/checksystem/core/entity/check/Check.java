package ru.clevertec.checksystem.core.entity.check;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import ru.clevertec.checksystem.core.common.builder.ICheckBuilder;
import ru.clevertec.checksystem.core.common.check.ICheckItemAggregable;
import ru.clevertec.checksystem.core.common.discount.IDiscountable;
import ru.clevertec.checksystem.core.entity.BaseEntity;
import ru.clevertec.checksystem.core.entity.discount.check.CheckDiscount;
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
        name = Entities.Mapping.Table.CHECKS,
        indexes = @Index(columnList = Entities.Mapping.Column.NAME, unique = true)
)
public class Check extends BaseEntity implements IDiscountable<CheckDiscount>, ICheckItemAggregable {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "check", fetch = FetchType.EAGER, orphanRemoval = true)
    private final Set<CheckItem> checkItems = new HashSet<>();

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = Entities.Mapping.Table.CHECK__CHECK_DISCOUNT,
            joinColumns = @JoinColumn(
                    name = Entities.Mapping.JoinColumn.CHECK_ID,
                    referencedColumnName = Entities.Mapping.Column.ID),
            inverseJoinColumns = @JoinColumn(
                    name = Entities.Mapping.JoinColumn.CHECK_DISCOUNT_ID,
                    referencedColumnName = Entities.Mapping.Column.ID)
    )
    private final Set<CheckDiscount> discounts = new HashSet<>();

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
    public Check() {
    }

    //@JsonCreator(mode = JsonCreator.Mode.DISABLED)
    public Check(
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
    public Check(
            @JsonProperty("id") Long id,
            @JsonProperty("name") String name,
            @JsonProperty("description") String description,
            @JsonProperty("address") String address,
            @JsonProperty("phoneNumber") String phoneNumber,
            @JsonProperty("cashier") String cashier,
            @JsonProperty("date") Date date,
            @JsonProperty("checkItems") Collection<CheckItem> checkItems,
            @JsonProperty("discounts") Collection<CheckDiscount> discounts) {
        this(name, description, address, phoneNumber, cashier, date);
        setId(id);
        setCheckItems(checkItems);
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
    public Collection<CheckItem> getCheckItems() {
        return checkItems;
    }

    @Override
    public void setCheckItems(Collection<CheckItem> checkItems) {
        clearCheckItems();
        if (checkItems != null)
            addCheckItems(checkItems);
    }

    @Override
    public void addCheckItem(CheckItem checkItem) {
        ThrowUtils.Argument.nullValue("checkItem", checkItem);
        checkItem.setCheck(this);
        getCheckItems().add(checkItem);
    }

    @Override
    public void addCheckItems(Collection<CheckItem> checkItems) {
        checkItems.forEach(this::addCheckItem);
    }

    @Override
    public void removeCheckItem(CheckItem checkItem) {
        ThrowUtils.Argument.nullValue("checkItem", checkItem);
        checkItem.setCheck(null);
        getCheckItems().remove(checkItem);
    }

    @Override
    public void removeCheckItems(Collection<CheckItem> checkItems) {
        ThrowUtils.Argument.nullValue("checkItem", checkItems);
        checkItems.forEach(this::removeCheckItem);
    }

    @Override
    public void clearCheckItems() {
        getCheckItems().forEach(checkItem -> checkItem.setCheck(null));
        getCheckItems().clear();
    }

    @Override
    public Collection<CheckDiscount> getDiscounts() {
        return discounts;
    }

    @Override
    public void setDiscounts(Collection<CheckDiscount> discounts) {
        clearDiscounts();
        if (discounts != null)
            addDiscounts(discounts);
    }

    @Override
    public void addDiscount(CheckDiscount discount) {
        ThrowUtils.Argument.nullValue("discount", discount);
        discount.getChecks().add(this);
        getDiscounts().add(discount);
    }

    @Override
    public void addDiscounts(Collection<CheckDiscount> discounts) {
        ThrowUtils.Argument.nullValue("discounts", discounts);
        discounts.forEach(this::addDiscount);
    }

    @Override
    public void removeDiscount(CheckDiscount discount) {
        ThrowUtils.Argument.nullValue("discount", discount);
        discount.getChecks().remove(this);
        getDiscounts().remove(discount);
    }

    @Override
    public void removeDiscounts(Collection<CheckDiscount> discounts) {
        ThrowUtils.Argument.nullValue("discounts", discounts);
        discounts.forEach(this::removeDiscount);
    }

    @Override
    public void clearDiscounts() {
        getDiscounts().forEach(discount -> discount.getChecks().remove(this));
        getDiscounts().clear();
    }

    public BigDecimal subTotalAmount() {
        return checkItems.stream()
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
        return getCheckItems().stream()
                .map(CheckItem::discountsAmount)
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
    }

    public static class Builder implements ICheckBuilder {

        private final Check check = new Check();

        @Override
        public ICheckBuilder setId(Long id) {
            check.setId(id);
            return this;
        }

        @Override
        public ICheckBuilder setName(String name) {
            check.setName(name);
            return this;
        }

        @Override
        public ICheckBuilder setDescription(String description) {
            check.setDescription(description);
            return this;
        }

        @Override
        public ICheckBuilder setAddress(String address) {
            check.setAddress(address);
            return this;
        }

        @Override
        public ICheckBuilder setPhoneNumber(String phoneNumber) {
            check.setPhoneNumber(phoneNumber);
            return this;
        }

        @Override
        public ICheckBuilder setCashier(String cashier) {
            check.setCashier(cashier);
            return this;
        }

        @Override
        public ICheckBuilder setDate(Date date) {
            check.setDate(date);
            return this;
        }

        @Override
        public ICheckBuilder setItems(Collection<CheckItem> checkItems) {
            check.setCheckItems(checkItems);
            return this;
        }

        @Override
        public ICheckBuilder addItem(CheckItem checkItem) {
            check.addCheckItem(checkItem);
            return this;
        }

        @Override
        public ICheckBuilder addItems(Collection<CheckItem> checkItems) {
            check.addCheckItems(checkItems);
            return this;
        }

        @Override
        public ICheckBuilder setDiscounts(Collection<CheckDiscount> checkDiscounts) {
            check.addDiscounts(checkDiscounts);
            return this;
        }

        @Override
        public ICheckBuilder addDiscount(CheckDiscount checkDiscount) {
            check.addDiscount(checkDiscount);
            return this;
        }

        @Override
        public ICheckBuilder addDiscounts(Collection<CheckDiscount> checkDiscounts) {
            check.addDiscounts(checkDiscounts);
            return this;
        }

        @Override
        public Check build() {
            return this.check;
        }
    }
}
