package ru.clevertec.checksystem.core.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import ru.clevertec.checksystem.core.Constants;
import ru.clevertec.checksystem.core.common.builder.IProductBuilder;
import ru.clevertec.checksystem.core.common.check.ICheckItemAggregable;
import ru.clevertec.checksystem.core.entity.check.CheckItem;
import ru.clevertec.checksystem.core.util.ThrowUtils;
import ru.clevertec.custom.json.StringifyIgnore;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(
        name = Constants.Entities.Mapping.Table.PRODUCTS,
        indexes = @Index(columnList = Constants.Entities.Mapping.Column.NAME, unique = true)
)
public class Product extends BaseEntity implements ICheckItemAggregable {

    @Column(name = Constants.Entities.Mapping.Column.NAME, nullable = false)
    private String name;

    @Column(name = Constants.Entities.Mapping.Column.PRICE, nullable = false)
    private BigDecimal price;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "product", orphanRemoval = true)
    @JsonIgnore
    private final Set<CheckItem> checkItems = new HashSet<>();

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

    @StringifyIgnore
    @Override
    public Collection<CheckItem> getCheckItems() {
        return checkItems;
    }

    @Override
    public void setCheckItems(Collection<CheckItem> checkItems) {
        clearCheckItems();
        addCheckItems(checkItems);
    }

    @Override
    public void addCheckItem(CheckItem checkItem) {
        ThrowUtils.Argument.nullValue("checkItem", checkItem);
        checkItem.setProduct(this);
        getCheckItems().add(checkItem);
    }

    @Override
    public void addCheckItems(Collection<CheckItem> checkItems) {
        ThrowUtils.Argument.nullValue("checkItems", checkItems);
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
        public Product build() throws IllegalArgumentException {
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
