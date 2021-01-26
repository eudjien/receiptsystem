package ru.clevertec.checksystem.core.entity.check;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import ru.clevertec.checksystem.core.entity.BaseEntity;
import ru.clevertec.checksystem.core.entity.Product;
import ru.clevertec.checksystem.core.common.IDiscountable;
import ru.clevertec.checksystem.core.common.builder.ICheckItemBuilder;
import ru.clevertec.checksystem.core.entity.discount.Discount;
import ru.clevertec.checksystem.core.entity.discount.check.item.CheckItemDiscount;
import ru.clevertec.checksystem.core.util.CollectionUtils;
import ru.clevertec.normalino.list.NormalinoList;

import java.math.BigDecimal;
import java.util.*;

public class CheckItem extends BaseEntity implements IDiscountable<CheckItemDiscount> {

    private final List<CheckItemDiscount> discounts = new NormalinoList<>();
    private Product product;
    private int quantity;

    private CheckItem() {
    }

    public CheckItem(Product product, int quantity) throws IllegalArgumentException {
        setProduct(product);
        setQuantity(quantity);
    }

    public CheckItem(int id, Product product, int quantity) throws IllegalArgumentException {
        super(id);
        setProduct(product);
        setQuantity(quantity);
    }

    public CheckItem(Product product, int quantity, Collection<CheckItemDiscount> discounts)
            throws IllegalArgumentException {
        setProduct(product);
        setQuantity(quantity);
        setDiscounts(discounts);
    }

    @JsonCreator
    public CheckItem(@JsonProperty("id") int id,
                     @JsonProperty("product") Product product,
                     @JsonProperty("quantity") int quantity,
                     @JsonProperty("discounts") Collection<CheckItemDiscount> discounts)
            throws IllegalArgumentException {
        super(id);
        setProduct(product);
        setQuantity(quantity);
        setDiscounts(discounts);
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) throws IllegalArgumentException {
        if (product == null) {
            throw new IllegalArgumentException("Product cannot be null");
        }
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) throws IllegalArgumentException {
        if (quantity < 1) {
            throw new IllegalArgumentException("quantity cannot be less than 1");
        }
        this.quantity = quantity;
    }

    public BigDecimal total() {
        return subTotal().subtract(discountsSum());
    }

    public BigDecimal subTotal() {
        return getProduct().getPrice()
                .multiply(BigDecimal.valueOf(getQuantity()));
    }

    public BigDecimal discountsSum() {
        return getDiscounts().stream()
                .map(Discount::discountSum)
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
    }

    @Override
    public Collection<CheckItemDiscount> getDiscounts() {
        return Collections.unmodifiableList(discounts);
    }

    @Override
    public void setDiscounts(Collection<CheckItemDiscount> checkItemDiscounts) throws IllegalArgumentException {
        if (checkItemDiscounts == null) {
            throw new IllegalArgumentException("Argument 'checkItemDiscounts' cannot be null");
        }
        discounts.clear();
        discounts.addAll(checkItemDiscounts);
        for (var discount : checkItemDiscounts) {
            discount.setCheckItem(this);
        }
    }

    @Override
    public void putDiscounts(Collection<CheckItemDiscount> checkItemDiscounts) throws IllegalArgumentException {
        if (checkItemDiscounts == null) {
            throw new IllegalArgumentException("Argument 'checkItemDiscounts' cannot be null");
        }
        CollectionUtils.putAll(discounts, checkItemDiscounts, Comparator.comparingInt(BaseEntity::getId));
        for (var discount : checkItemDiscounts) {
            discount.setCheckItem(this);
        }
    }

    @Override
    public void putDiscount(CheckItemDiscount checkItemDiscount) throws IllegalArgumentException {
        checkItemDiscount.setCheckItem(this);
        CollectionUtils.put(discounts, checkItemDiscount, Comparator.comparingInt(BaseEntity::getId));
    }

    @Override
    public void removeDiscounts(Collection<CheckItemDiscount> checkItemDiscounts) {
        for (var checkItemDiscount : checkItemDiscounts) {
            removeDiscount(checkItemDiscount);
        }
    }

    @Override
    public void removeDiscount(CheckItemDiscount checkItemDiscount) {
        var index = Collections.binarySearch(
                discounts, checkItemDiscount, Comparator.comparingInt(BaseEntity::getId));
        if (index != -1) {
            this.discounts.remove(index);
        }
    }

    public static class Builder implements ICheckItemBuilder {

        protected final CheckItem checkItem = new CheckItem();

        @Override
        public ICheckItemBuilder setId(int id) {
            checkItem.setId(id);
            return this;
        }

        @Override
        public ICheckItemBuilder setProduct(Product product) {
            checkItem.setProduct(product);
            return this;
        }

        @Override
        public ICheckItemBuilder setQuantity(int quantity) {
            checkItem.setQuantity(quantity);
            return this;
        }

        @Override
        public ICheckItemBuilder setDiscounts(CheckItemDiscount... checkItemDiscounts) {
            checkItem.setDiscounts(Arrays.asList(checkItemDiscounts));
            return this;
        }

        @Override
        public CheckItem build() throws IllegalArgumentException {
            throwIfInvalid();
            return checkItem;
        }

        private void throwIfInvalid() throws IllegalArgumentException {
            if (checkItem.product == null) {
                throw new IllegalArgumentException("Product is required");
            }
        }
    }
}
