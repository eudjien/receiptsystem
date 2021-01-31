package ru.clevertec.checksystem.core.entity.check;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import ru.clevertec.checksystem.core.common.IDiscountable;
import ru.clevertec.checksystem.core.common.builder.ICheckBuilder;
import ru.clevertec.checksystem.core.common.check.ICheckItemAggregable;
import ru.clevertec.checksystem.core.entity.BaseEntity;
import ru.clevertec.checksystem.core.entity.discount.Discount;
import ru.clevertec.checksystem.core.entity.discount.check.CheckDiscount;
import ru.clevertec.checksystem.core.util.CollectionUtils;
import ru.clevertec.normalino.list.NormalinoList;

import java.math.BigDecimal;
import java.util.*;

public class Check extends BaseEntity implements IDiscountable<CheckDiscount>, ICheckItemAggregable {

    private final List<CheckItem> checkItems = new NormalinoList<>();
    private final List<CheckDiscount> discounts = new NormalinoList<>();
    private String name;
    private String description;
    private String address;
    private String phoneNumber;
    private String cashier;
    private Date date;

    private Check() {
    }

    public Check(int id) {
        super(id);
    }

    public Check(int id,
                 String name, String description, String address, String phoneNumber, String cashier, Date date) {
        super(id);
        this.name = name;
        this.description = description;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.cashier = cashier;
        this.date = date;
    }

    @JsonCreator
    public Check(
            @JsonProperty("id") int id,
            @JsonProperty("name") String name,
            @JsonProperty("description") String description,
            @JsonProperty("address") String address,
            @JsonProperty("phoneNumber") String phoneNumber,
            @JsonProperty("cashier") String cashier,
            @JsonProperty("date") Date date,
            @JsonProperty("checkItems") Collection<CheckItem> checkItems,
            @JsonProperty("discounts") Collection<CheckDiscount> discounts)
            throws IllegalArgumentException {
        super(id);
        setName(name);
        setDescription(description);
        setAddress(address);
        setPhoneNumber(phoneNumber);
        setCashier(cashier);
        setDate(date);
        setCheckItems(checkItems);
        putDiscounts(discounts);
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

    public Collection<CheckItem> getCheckItems() {
        return Collections.unmodifiableList(checkItems);
    }

    public void setCheckItems(Collection<CheckItem> checkItems) {
        if (checkItems != null) {
            for (var item : checkItems) {
                putCheckItem(item);
            }
        }
    }

    @Override
    public Collection<CheckDiscount> getDiscounts() {
        return Collections.unmodifiableList(discounts);
    }

    @Override
    public void setDiscounts(Collection<CheckDiscount> discounts) throws IllegalArgumentException {
        if (discounts == null) {
            throw new IllegalArgumentException("Argument 'checkItemDiscounts' cannot be null");
        }
        this.discounts.clear();
        this.discounts.addAll(discounts);
        for (var discount : discounts) {
            discount.setCheck(this);
        }
    }

    @Override
    public void putDiscounts(Collection<CheckDiscount> discounts) throws IllegalArgumentException {
        if (discounts == null) {
            throw new IllegalArgumentException("Argument 'discounts' cannot be null");
        }
        CollectionUtils.putAll(this.discounts, discounts, Comparator.comparingInt(BaseEntity::getId));
        for (var discount : discounts) {
            discount.setCheck(this);
        }
    }

    @Override
    public void putDiscount(CheckDiscount discount) throws IllegalArgumentException {
        discount.setCheck(this);
        CollectionUtils.put(discounts, discount, Comparator.comparingInt(BaseEntity::getId));
    }

    @Override
    public void removeDiscounts(Collection<CheckDiscount> discounts) {
        for (var discount : discounts) {
            removeDiscount(discount);
        }
    }

    @Override
    public void removeDiscount(CheckDiscount discount) {
        var index = Collections.binarySearch(discounts, discount, Comparator.comparingInt(BaseEntity::getId));
        if (index != -1) {
            this.discounts.remove(index);
        }
    }

    public void putCheckItem(CheckItem checkItem) {
        CollectionUtils.put(checkItems, checkItem, Comparator.comparingInt(BaseEntity::getId));
    }

    public void putCheckItems(Collection<CheckItem> checkItems) {
        CollectionUtils.putAll(this.checkItems, checkItems, Comparator.comparingInt(BaseEntity::getId));
    }

    public void deleteCheckItem(CheckItem checkItem) {
        var index = Collections.binarySearch(checkItems, checkItem, Comparator.comparingInt(BaseEntity::getId));
        if (index != -1) {
            this.checkItems.remove(index);
        }
    }

    public void deleteCheckItems(Collection<CheckItem> checkItems) {
        for (var checkItem : checkItems) {
            deleteCheckItem(checkItem);
        }
    }

    public BigDecimal subTotal() {
        return checkItems.stream()
                .map(a -> a.getProduct().getPrice().multiply(BigDecimal.valueOf(a.getQuantity())))
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
    }

    public BigDecimal total() {
        return subTotal().subtract(discountSum());
    }

    public BigDecimal discountSum() {
        return checkDiscountsSum().add(itemsDiscountSum());
    }

    public BigDecimal checkDiscountsSum() {
        return getDiscounts().stream().map(Discount::discountAmount)
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
    }

    public BigDecimal itemsDiscountSum() {
        return getCheckItems().stream()
                .map(CheckItem::discountsSum)
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
    }

    public static class Builder implements ICheckBuilder {

        private final Check check = new Check();

        @Override
        public ICheckBuilder setId(int id) {
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
        public ICheckBuilder setItems(CheckItem... checkItems) {
            check.setCheckItems(Arrays.asList(checkItems.clone()));
            return this;
        }

        @Override
        public ICheckBuilder setDiscounts(CheckDiscount... checkDiscounts) {
            check.setDiscounts(Arrays.asList(checkDiscounts.clone()));
            return this;
        }

        @Override
        public Check build() {
            return this.check;
        }
    }
}
