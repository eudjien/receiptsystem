package ru.clevertec.checksystem.core.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Collection;
import java.util.Date;

public class GeneratedCheck {

    private int id;
    private String name;
    private String description;
    private String address;
    private String cashier;
    private String phoneNumber;
    private Date date;
    private Collection<Integer> discountIds;
    private Collection<GeneratedCheckItem> items;

    public GeneratedCheck() {
    }

    public GeneratedCheck(int id, String name, String description, String address, String cashier, String phoneNumber, Date date) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.address = address;
        this.cashier = cashier;
        this.phoneNumber = phoneNumber;
        this.date = date;
    }

    @JsonCreator
    public GeneratedCheck(
            @JsonProperty("id") int id,
            @JsonProperty("name") String name,
            @JsonProperty("description") String description,
            @JsonProperty("address") String address,
            @JsonProperty("cashier") String cashier,
            @JsonProperty("phoneNumber") String phoneNumber,
            @JsonProperty("date") Date date,
            @JsonProperty("discountIds") Collection<Integer> discountIds,
            @JsonProperty("items") Collection<GeneratedCheckItem> items) {
        this(id, name, description, address, cashier, phoneNumber, date);
        this.discountIds = discountIds;
        this.items = items;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public Collection<Integer> getDiscountIds() {
        return discountIds;
    }

    public void setDiscountIds(Collection<Integer> discountIds) {
        this.discountIds = discountIds;
    }

    public Collection<GeneratedCheckItem> getItems() {
        return items;
    }

    public void setItems(Collection<GeneratedCheckItem> items) {
        this.items = items;
    }
}
