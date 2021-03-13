package ru.clevertec.checksystem.core.data.generate;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import ru.clevertec.custom.list.SinglyLinkedList;

import java.util.Collection;
import java.util.Date;

public class ReceiptGenerate {

    private Long id;
    private String name;
    private String description;
    private String address;
    private String cashier;
    private String phoneNumber;
    private Date date;
    private Collection<Long> discountIds = new SinglyLinkedList<>();
    private Collection<ReceiptItemGenerate> receiptItemGenerates = new SinglyLinkedList<>();

    @JsonCreator(mode = JsonCreator.Mode.DISABLED)
    public ReceiptGenerate() {
    }

    @JsonCreator(mode = JsonCreator.Mode.DISABLED)
    public ReceiptGenerate(
            Long id,
            String name,
            String description,
            String address,
            String cashier,
            String phoneNumber,
            Date date) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.address = address;
        this.cashier = cashier;
        this.phoneNumber = phoneNumber;
        this.date = date;
    }

    @JsonCreator
    public ReceiptGenerate(
            @JsonProperty("id") Long id,
            @JsonProperty("name") String name,
            @JsonProperty("description") String description,
            @JsonProperty("address") String address,
            @JsonProperty("cashier") String cashier,
            @JsonProperty("phoneNumber") String phoneNumber,
            @JsonProperty("date") Date date,
            @JsonProperty("discountIds") Collection<Long> discountIds,
            @JsonProperty("receiptItemGenerates") Collection<ReceiptItemGenerate> receiptItemGenerates) {
        this(id, name, description, address, cashier, phoneNumber, date);
        this.discountIds = discountIds;
        this.receiptItemGenerates = receiptItemGenerates;
    }

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

    public String getCashier() {
        return cashier;
    }

    public void setCashier(String cashier) {
        this.cashier = cashier;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Collection<Long> getDiscountIds() {
        return discountIds;
    }

    public void setDiscountIds(Collection<Long> discountIds) {
        this.discountIds = discountIds != null ? discountIds : new SinglyLinkedList<>();
    }

    public Collection<ReceiptItemGenerate> getReceiptItemGenerates() {
        return receiptItemGenerates;
    }

    public void setReceiptItems(Collection<ReceiptItemGenerate> receiptItemGenerates) {
        this.receiptItemGenerates = receiptItemGenerates != null ? receiptItemGenerates : new SinglyLinkedList<>();
    }
}
