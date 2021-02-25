package ru.clevertec.checksystem.core.entity.check;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import ru.clevertec.checksystem.core.Constants;
import ru.clevertec.checksystem.core.common.builder.ICheckItemBuilder;
import ru.clevertec.checksystem.core.common.check.ICheckComposable;
import ru.clevertec.checksystem.core.common.discount.IDiscountable;
import ru.clevertec.checksystem.core.entity.BaseEntity;
import ru.clevertec.checksystem.core.entity.Product;
import ru.clevertec.checksystem.core.entity.discount.checkitem.CheckItemDiscount;
import ru.clevertec.checksystem.core.util.ThrowUtils;
import ru.clevertec.custom.json.StringifyIgnore;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(
        name = Constants.Entities.Mapping.Table.CHECK_ITEMS,
        indexes = @Index(columnList =
                Constants.Entities.Mapping.JoinColumn.PRODUCT_ID + "," +
                        Constants.Entities.Mapping.JoinColumn.CHECK_ID,
                unique = true)
)
public class CheckItem extends BaseEntity implements IDiscountable<CheckItemDiscount>, ICheckComposable {

    private final static int MIN_QUANTITY = 1;

    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinTable(
            name = Constants.Entities.Mapping.Table.CHECK_ITEM__CHECK_ITEM_DISCOUNT,
            joinColumns = @JoinColumn(
                    name = Constants.Entities.Mapping.JoinColumn.CHECK_ITEM_ID,
                    referencedColumnName = Constants.Entities.Mapping.Column.ID),
            inverseJoinColumns = @JoinColumn(
                    name = Constants.Entities.Mapping.JoinColumn.CHECK_ITEM_DISCOUNT_ID,
                    referencedColumnName = Constants.Entities.Mapping.Column.ID)
    )
    private final Set<CheckItemDiscount> discounts = new HashSet<>();

    @ManyToOne(cascade = {CascadeType.PERSIST}, optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = Constants.Entities.Mapping.JoinColumn.PRODUCT_ID)
    private Product product;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = Constants.Entities.Mapping.JoinColumn.CHECK_ID)
    @JsonIgnore
    private Check check;

    @Column(name = Constants.Entities.Mapping.Column.QUANTITY, nullable = false)
    private int quantity;

    @Column(name = Constants.Entities.Mapping.JoinColumn.PRODUCT_ID, insertable = false, updatable = false)
    private Long productId;

    @Column(name = Constants.Entities.Mapping.JoinColumn.CHECK_ID, insertable = false, updatable = false)
    private Long checkId;

    @JsonCreator(mode = JsonCreator.Mode.DISABLED)
    public CheckItem() {
    }

    @JsonCreator(mode = JsonCreator.Mode.DISABLED)
    public CheckItem(Product product, int quantity) {
        setProduct(product);
        setQuantity(quantity);
    }

    @JsonCreator(mode = JsonCreator.Mode.DISABLED)
    public CheckItem(Long id, Product product, int quantity) {
        super(id);
        setProduct(product);
        setQuantity(quantity);
    }

    @JsonCreator(mode = JsonCreator.Mode.DISABLED)
    public CheckItem(Product product, int quantity, Collection<CheckItemDiscount> checkItemDiscounts) {
        setProduct(product);
        setQuantity(quantity);
        setDiscounts(checkItemDiscounts);
    }

    @JsonCreator
    public CheckItem(@JsonProperty("id") Long id,
                     @JsonProperty("product") Product product,
                     @JsonProperty("quantity") int quantity,
                     @JsonProperty("discounts") Collection<CheckItemDiscount> checkItemDiscounts) {
        super(id);
        setProduct(product);
        setQuantity(quantity);
        setDiscounts(checkItemDiscounts);
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        ThrowUtils.Argument.lessThan("quantity", quantity, MIN_QUANTITY);
        this.quantity = quantity;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getCheckId() {
        return checkId;
    }

    public void setCheckId(Long checkId) {
        this.checkId = checkId;
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
                .map(discount -> discount.discountAmount(this))
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
    }

    @Override
    public Collection<CheckItemDiscount> getDiscounts() {
        return discounts;
    }

    @Override
    public void setDiscounts(Collection<CheckItemDiscount> checkItemDiscounts) {
        clearDiscounts();
        if (checkItemDiscounts != null)
            addDiscounts(checkItemDiscounts);
    }

    @Override
    public void addDiscount(CheckItemDiscount checkItemDiscount) {
        ThrowUtils.Argument.nullValue("checkItemDiscounts", checkItemDiscount);
        getDiscounts().add(checkItemDiscount);
        checkItemDiscount.getCheckItems().add(this);
    }

    @Override
    public void addDiscounts(Collection<CheckItemDiscount> checkItemDiscounts) {
        ThrowUtils.Argument.nullValue("checkItemDiscounts", checkItemDiscounts);
        checkItemDiscounts.forEach(this::addDiscount);
    }

    @Override
    public void removeDiscount(CheckItemDiscount checkItemDiscount) {
        ThrowUtils.Argument.nullValue("checkItemDiscount", checkItemDiscount);
        getDiscounts().remove(checkItemDiscount);
        checkItemDiscount.getCheckItems().remove(this);
    }

    @Override
    public void removeDiscounts(Collection<CheckItemDiscount> discounts) {
        discounts.forEach(this::removeDiscount);
    }

    @Override
    public void clearDiscounts() {
        getDiscounts().forEach(discount -> discount.getCheckItems().remove(this));
        getDiscounts().clear();
    }

    @StringifyIgnore
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
        public ICheckItemBuilder setId(Long id) {
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
        public ICheckItemBuilder setDiscounts(Collection<CheckItemDiscount> checkItemDiscounts) {
            checkItem.setDiscounts(checkItemDiscounts);
            return this;
        }

        @Override
        public ICheckItemBuilder addDiscount(CheckItemDiscount checkItemDiscount) {
            checkItem.addDiscount(checkItemDiscount);
            return this;
        }

        @Override
        public ICheckItemBuilder addDiscounts(Collection<CheckItemDiscount> checkItemDiscounts) {
            checkItem.addDiscounts(checkItemDiscounts);
            return this;
        }

        @Override
        public CheckItem build() {
            throwIfInvalid();
            return checkItem;
        }

        private void throwIfInvalid() {
            if (checkItem.getProduct() == null)
                throw new IllegalArgumentException("Product is required");
        }
    }
}
