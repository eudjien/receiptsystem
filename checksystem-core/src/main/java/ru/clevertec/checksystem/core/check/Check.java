package ru.clevertec.checksystem.core.check;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import ru.clevertec.checksystem.core.BaseEntity;
import ru.clevertec.checksystem.core.discount.Discount;
import ru.clevertec.checksystem.core.discount.check.base.CheckDiscount;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class Check extends BaseEntity {

    private final List<CheckItem> items = new ArrayList<>();
    private final List<CheckDiscount> discounts = new ArrayList<>();
    private String name;
    private String description;
    private String address;
    private String phoneNumber;
    private String cashier;
    private Date date;

    public Check() {
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
            @JsonProperty("items") List<CheckItem> items,
            @JsonProperty("discounts") List<CheckDiscount> discounts) throws Exception {
        super(id);
        setName(name);
        setDescription(description);
        setAddress(address);
        setPhoneNumber(phoneNumber);
        setCashier(cashier);
        setDate(date);
        setItems(items);
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

    public List<CheckItem> getItems() {
        return Collections.unmodifiableList(items);
    }

    public void setItems(List<CheckItem> items) {
        if (items != null) {
            for (var item : items) {
                addItem(item);
            }
        }
    }

    public List<CheckDiscount> getDiscounts() {
        return Collections.unmodifiableList(discounts);
    }

    public void setDiscounts(List<CheckDiscount> discounts) throws Exception {
        if (discounts != null) {
            for (var discount : discounts) {
                addDiscount(discount);
            }
        }
    }

    // Add or update item
    public void addItem(CheckItem item) {
        var index = itemIndex(item);
        if (index != -1) {
            items.set(index, item);
        } else {
            this.items.add(item);
        }
    }

    public void deleteItem(CheckItem item) {
        var index = itemIndex(item);
        if (index != -1) {
            this.items.remove(index);
        }
    }

    public void addDiscount(CheckDiscount discount) throws Exception {
        discount.setCheck(this);
        this.discounts.add(discount);
    }

    public void deleteDiscount(CheckDiscount discount) {
        var index = discountIndex(discount);
        if (index != -1) {
            this.discounts.remove(index);
        }
    }

    // Сумма всех покупок без скидок
    public BigDecimal subTotal() {
        return items.stream()
                .map(a -> a.getProduct().getPrice().multiply(BigDecimal.valueOf(a.getQuantity())))
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
    }

    // Сумма всех покупок с учетом скидок
    public BigDecimal total() {
        return subTotal().subtract(allDiscountsSum());
    }

    // Сумма скидок чека + сумма скидок каждого продукта
    public BigDecimal allDiscountsSum() {
        return checkDiscountsSum().add(itemsDiscountsSum());
    }

    // Сумма скидок чека
    public BigDecimal checkDiscountsSum() {
        return getDiscounts().stream().map(Discount::discountSum)
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
    }

    // Сумма скидок каждого продукта
    public BigDecimal itemsDiscountsSum() {
        return getItems().stream()
                .map(CheckItem::discountsSum)
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
    }

    private int itemIndex(CheckItem item) {
        for (int i = 0; i < items.size(); i++)
            if (items.get(i).getId() == item.getId())
                return i;
        return -1;
    }

    private int discountIndex(CheckDiscount discount) {
        for (int i = 0; i < discounts.size(); i++)
            if (discounts.get(i).getId() == discount.getId())
                return i;
        return -1;
    }
}
