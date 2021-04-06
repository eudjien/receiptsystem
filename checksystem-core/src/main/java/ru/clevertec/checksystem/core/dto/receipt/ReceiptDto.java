package ru.clevertec.checksystem.core.dto.receipt;

import ru.clevertec.checksystem.core.dto.discount.receipt.ReceiptDiscountDto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class ReceiptDto {

    private Long id = 0L;

    @NotBlank
    private String name;

    @NotBlank
    private String description;

    @NotBlank
    private String address;

    @NotBlank
    private String phoneNumber;

    @NotBlank
    private String cashier;

    @NotNull
    @PastOrPresent
    private Date date;

    private final Set<ReceiptItemDto> receiptItems = new HashSet<>();

    private final Set<ReceiptDiscountDto> discounts = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Collection<ReceiptItemDto> getReceiptItems() {
        return receiptItems;
    }

    public void setReceiptItems(Collection<ReceiptItemDto> receiptItems) {
        this.receiptItems.clear();
        if (receiptItems != null)
            this.receiptItems.addAll(receiptItems);
    }

    public Collection<ReceiptDiscountDto> getDiscounts() {
        return discounts;
    }

    public void setDiscounts(Collection<ReceiptDiscountDto> discounts) {
        this.discounts.clear();
        if (discounts != null)
            this.discounts.addAll(discounts);
    }
}
