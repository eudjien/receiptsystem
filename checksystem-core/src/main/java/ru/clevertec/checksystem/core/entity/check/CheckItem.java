package ru.clevertec.checksystem.core.entity.check;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import ru.clevertec.checksystem.core.common.builder.ICheckItemBuilder;
import ru.clevertec.checksystem.core.common.discount.IDiscountable;
import ru.clevertec.checksystem.core.entity.BaseEntity;
import ru.clevertec.checksystem.core.entity.Product;
import ru.clevertec.checksystem.core.entity.discount.Discount;
import ru.clevertec.checksystem.core.entity.discount.check.item.CheckItemDiscount;
import ru.clevertec.checksystem.core.exception.ArgumentNullException;
import ru.clevertec.checksystem.core.exception.ArgumentOutOfRangeException;
import ru.clevertec.checksystem.core.util.CollectionUtils;
import ru.clevertec.checksystem.core.util.ThrowUtils;
import ru.clevertec.normalino.list.NormalinoList;

import java.math.BigDecimal;
import java.util.*;

public class CheckItem extends BaseEntity implements IDiscountable<CheckItemDiscount> {

    private final static int MIN_QUANTITY = 1;

    private final List<CheckItemDiscount> discounts = new NormalinoList<>();
    private Product product;
    private int quantity;

    private CheckItem() {
    }

    public CheckItem(Product product, int quantity) throws ArgumentNullException {
        setProduct(product);
        setQuantity(quantity);
    }

    public CheckItem(int id, Product product, int quantity) throws ArgumentNullException {
        super(id);
        setProduct(product);
        setQuantity(quantity);
    }

    public CheckItem(Product product, int quantity, Collection<CheckItemDiscount> discounts)
            throws ArgumentNullException {
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

    public void setProduct(Product product) throws ArgumentNullException {
        ThrowUtils.Argument.theNull("product", product);
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) throws ArgumentOutOfRangeException {
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
    public void setDiscounts(Collection<CheckItemDiscount> discounts) throws ArgumentNullException {

        ThrowUtils.Argument.theNull("discounts", discounts);

        this.discounts.clear();
        this.discounts.addAll(discounts);
        for (var discount : discounts) {
            discount.setCheckItem(this);
        }
    }

    @Override
    public void putDiscounts(Collection<CheckItemDiscount> discounts) throws ArgumentNullException {

        ThrowUtils.Argument.theNull("discounts", discounts);

        CollectionUtils.putAll(this.discounts, discounts, Comparator.comparingInt(BaseEntity::getId));

        for (var discount : discounts) {
            discount.setCheckItem(this);
        }
    }

    @Override
    public void putDiscount(CheckItemDiscount discount) throws ArgumentNullException {

        ThrowUtils.Argument.theNull("discount", discount);

        discount.setCheckItem(this);
        CollectionUtils.put(discounts, discount, Comparator.comparingInt(BaseEntity::getId));
    }

    @Override
    public void removeDiscounts(Collection<CheckItemDiscount> discounts) throws ArgumentNullException {

        ThrowUtils.Argument.theNull("discounts", discounts);

        for (var discount : discounts) {
            removeDiscount(discount);
        }
    }

    @Override
    public void removeDiscount(CheckItemDiscount discount) throws ArgumentNullException {

        ThrowUtils.Argument.theNull("discount", discount);

        var index = Collections.binarySearch(
                discounts, discount, Comparator.comparingInt(BaseEntity::getId));
        if (index > -1) {
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
        public ICheckItemBuilder setProduct(Product product) throws ArgumentNullException {
            checkItem.setProduct(product);
            return this;
        }

        @Override
        public ICheckItemBuilder setQuantity(int quantity) throws ArgumentOutOfRangeException {
            checkItem.setQuantity(quantity);
            return this;
        }

        @Override
        public ICheckItemBuilder setDiscounts(CheckItemDiscount... discounts) throws ArgumentNullException {
            checkItem.setDiscounts(Arrays.asList(discounts));
            return this;
        }

        @Override
        public CheckItem build() throws IllegalArgumentException {
            throwIfInvalid();
            return checkItem;
        }

        private void throwIfInvalid() throws IllegalArgumentException {
            if (checkItem.getProduct() == null) {
                throw new IllegalArgumentException("Product is required");
            }
        }
    }
}
