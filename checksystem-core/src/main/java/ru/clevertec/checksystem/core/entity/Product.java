package ru.clevertec.checksystem.core.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import ru.clevertec.checksystem.core.common.builder.IProductBuilder;
import ru.clevertec.checksystem.core.common.receipt.IReceiptItemAggregable;
import ru.clevertec.checksystem.core.constant.Entities;
import ru.clevertec.checksystem.core.entity.receipt.ReceiptItem;
import ru.clevertec.checksystem.core.util.ThrowUtils;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(
        name = Entities.Table.PRODUCTS,
        indexes = @Index(columnList = Entities.Column.NAME, unique = true)
)
public class Product extends BaseEntity implements IReceiptItemAggregable {

    @Column(name = Entities.Column.NAME, nullable = false)
    private String name;

    @Column(name = Entities.Column.PRICE, nullable = false)
    private BigDecimal price;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "product")
    @JsonIgnore
    private final Set<ReceiptItem> receiptItems = new HashSet<>();

    @JsonCreator(mode = JsonCreator.Mode.DISABLED)
    public Product() {
    }

    @JsonCreator(mode = JsonCreator.Mode.DISABLED)
    public Product(String name, BigDecimal price) {
        setName(name);
        setPrice(price);
    }

    @JsonCreator
    public Product(
            @JsonProperty("id") Long id,
            @JsonProperty("name") String name,
            @JsonProperty("price") BigDecimal price) {
        super(id);
        setName(name);
        setPrice(price);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        ThrowUtils.Argument.nullOrBlank("name", name);
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        var parameterName = "price";
        ThrowUtils.Argument.nullValue(parameterName, price);
        ThrowUtils.Argument.lessThan(parameterName, price, BigDecimal.ZERO);
        this.price = price;
    }

    @Override
    public Collection<ReceiptItem> getReceiptItems() {
        return receiptItems;
    }

    @Override
    public void setReceiptItems(Collection<ReceiptItem> receiptItems) {
        clearReceiptItems();
        addReceiptItems(receiptItems);
    }

    @Override
    public void addReceiptItem(ReceiptItem receiptItem) {
        ThrowUtils.Argument.nullValue("receiptItem", receiptItem);
        receiptItem.setProduct(this);
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

    public static class Builder implements IProductBuilder {

        private final Product product = new Product();

        @Override
        public IProductBuilder setId(Long id) {
            product.setId(id);
            return this;
        }

        @Override
        public IProductBuilder setName(String name) {
            product.setName(name);
            return this;
        }

        @Override
        public IProductBuilder setPrice(BigDecimal price) {
            product.setPrice(price);
            return this;
        }

        @Override
        public Product build() {
            throwIfNotValid();
            return product;
        }

        private void throwIfNotValid() {
            if (product.getName() == null)
                throw new IllegalArgumentException("Name required to build Product");
            if (product.getPrice() == null)
                throw new IllegalArgumentException("Price required to build Product");
        }
    }
}
