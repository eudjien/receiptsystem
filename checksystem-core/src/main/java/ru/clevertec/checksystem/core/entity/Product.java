package ru.clevertec.checksystem.core.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import ru.clevertec.checksystem.core.common.builder.IProductBuilder;

import java.math.BigDecimal;

public class Product extends BaseEntity {

    private String name;
    private BigDecimal price;

    private Product() {
    }

    public Product(String name, BigDecimal price) {
        this.name = name;
        this.price = price;
    }

    @JsonCreator
    public Product(
            @JsonProperty("id") int id,
            @JsonProperty("name") String name,
            @JsonProperty("price") BigDecimal price) {
        super(id);
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public static class Builder implements IProductBuilder {

        private final Product product = new Product();

        @Override
        public IProductBuilder setId(int id) {
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

        private void throwIfNotValid() throws IllegalArgumentException {
            if (product.getName() == null) {
                throw new IllegalArgumentException("Name required to build Product");
            }
            if (product.getPrice() == null) {
                throw new IllegalArgumentException("Price required to build Product");
            }
        }
    }
}
