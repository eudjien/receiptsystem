package ru.clevertec.checksystem.core.entity.check;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import ru.clevertec.checksystem.core.common.builder.ICheckItemBuilder;
import ru.clevertec.checksystem.core.common.check.ICheckComposable;
import ru.clevertec.checksystem.core.common.discount.IDiscountable;
import ru.clevertec.checksystem.core.entity.BaseEntity;
import ru.clevertec.checksystem.core.entity.Product;
import ru.clevertec.checksystem.core.entity.discount.Discount;
import ru.clevertec.checksystem.core.entity.discount.checkitem.CheckItemDiscount;
import ru.clevertec.checksystem.core.util.CollectionUtils;
import ru.clevertec.checksystem.core.util.ThrowUtils;
import ru.clevertec.customlib.list.SinglyLinkedList;

import java.math.BigDecimal;
import java.util.*;

public class CheckItem extends BaseEntity implements IDiscountable<CheckItemDiscount>, ICheckComposable {

    private final static int MIN_QUANTITY = 1;

    private final List<CheckItemDiscount> discounts = new SinglyLinkedList<>();
    private Product product;

    @JsonIgnore
    private Check check;

    private int quantity;

    public CheckItem() {
    }

    public CheckItem(Product product, int quantity) {
        setProduct(product);
        setQuantity(quantity);
    }

    public CheckItem(int id, Product product, int quantity) {
        super(id);
        setProduct(product);
        setQuantity(quantity);
    }

    public CheckItem(Product product, int quantity, Collection<CheckItemDiscount> discounts) {
        setProduct(product);
        setQuantity(quantity);
        setDiscounts(discounts);
    }

    @JsonCreator
    public CheckItem(@JsonProperty("id") int id,
                     @JsonProperty("product") Product product,
                     @JsonProperty("quantity") int quantity,
                     @JsonProperty("discounts") Collection<CheckItemDiscount> discounts) {
        super(id);
        setProduct(product);
        setQuantity(quantity);
        setDiscounts(discounts);
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        ThrowUtils.Argument.nullValue("product", product);
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        ThrowUtils.Argument.lessThan("quantity", quantity, MIN_QUANTITY);
        this.quantity = quantity;
    }

    public BigDecimal totalAmount() {
        return subTotalAmount().subtract(discountsAmount());
    }

    public BigDecimal subTotalAmount() {
        return getProduct().getPrice()
                .multiply(BigDecimal.valueOf(getQuantity()));
    }

    @Override
    public BigDecimal discountsAmount() {
        return getDiscounts().stream()
                .map(Discount::discountAmount)
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
    }

    @Override
    public Collection<CheckItemDiscount> getDiscounts() {
        return Collections.unmodifiableList(discounts);
    }

    @Override
    public void setDiscounts(Collection<CheckItemDiscount> discounts) {

        this.discounts.clear();

        if (discounts != null) {
            discounts.forEach(discount -> discount.setCheckItem(this));
            putDiscounts(discounts);
        }
    }

    @Override
    public void putDiscounts(Collection<CheckItemDiscount> discounts) {
        ThrowUtils.Argument.nullValue("discounts", discounts);
        discounts.forEach(this::putDiscount);
    }

    @Override
    public void putDiscount(CheckItemDiscount discount) {
        ThrowUtils.Argument.nullValue("discount", discount);
        discount.setCheckItem(this);
        CollectionUtils.put(discounts, discount, Comparator.comparingInt(BaseEntity::getId));
    }

    @Override
    public void removeDiscounts(Collection<CheckItemDiscount> discounts) {
        ThrowUtils.Argument.nullValue("discounts", discounts);
        discounts.forEach(this::removeDiscount);
    }

    @Override
    public void removeDiscount(CheckItemDiscount discount) {

        ThrowUtils.Argument.nullValue("discount", discount);

        var index = Collections.binarySearch(discounts, discount, Comparator.comparingInt(BaseEntity::getId));

        if (index > -1)
            this.discounts.remove(index);
    }

    @Override
    public Check getCheck() {
        return check;
    }

    @Override
    public void setCheck(Check check) {
        this.check = check;
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
        public ICheckItemBuilder setDiscounts(CheckItemDiscount... discounts) {
            checkItem.setDiscounts(Arrays.asList(discounts));
            return this;
        }

        @Override
        public CheckItem build() {
            throwIfInvalid();
            return checkItem;
        }

        private void throwIfInvalid() {
            if (checkItem.getProduct() == null) {
                throw new IllegalArgumentException("Product is required");
            }
        }
    }
}
