package ru.clevertec.checksystem.core.entity.receipt;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import ru.clevertec.checksystem.core.common.builder.IReceiptItemBuilder;
import ru.clevertec.checksystem.core.common.receipt.IReceiptComposable;
import ru.clevertec.checksystem.core.common.discount.IDiscountable;
import ru.clevertec.checksystem.core.entity.BaseEntity;
import ru.clevertec.checksystem.core.entity.Product;
import ru.clevertec.checksystem.core.entity.discount.receiptitem.ReceiptItemDiscount;
import ru.clevertec.checksystem.core.util.ThrowUtils;
import ru.clevertec.custom.json.StringifyIgnore;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static ru.clevertec.checksystem.core.Constants.Entities;

@Entity
@Table(
        name = Entities.Mapping.Table.RECEIPT_ITEMS,
        indexes = @Index(columnList =
                Entities.Mapping.JoinColumn.PRODUCT_ID + "," +
                        Entities.Mapping.JoinColumn.RECEIPT_ID,
                unique = true)
)
public class ReceiptItem extends BaseEntity implements IDiscountable<ReceiptItemDiscount>, IReceiptComposable {

    private final static int MIN_QUANTITY = 1;

    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinTable(
            name = Entities.Mapping.Table.RECEIPT_ITEM__RECEIPT_ITEM_DISCOUNT,
            joinColumns = @JoinColumn(
                    name = Entities.Mapping.JoinColumn.RECEIPT_ITEM_ID,
                    referencedColumnName = Entities.Mapping.Column.ID),
            inverseJoinColumns = @JoinColumn(
                    name = Entities.Mapping.JoinColumn.RECEIPT_ITEM_DISCOUNT_ID,
                    referencedColumnName = Entities.Mapping.Column.ID)
    )
    private final Set<ReceiptItemDiscount> discounts = new HashSet<>();

    @ManyToOne(cascade = {CascadeType.PERSIST}, optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = Entities.Mapping.JoinColumn.PRODUCT_ID)
    private Product product;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = Entities.Mapping.JoinColumn.RECEIPT_ID)
    @JsonIgnore
    private Receipt receipt;

    @Column(name = Entities.Mapping.Column.QUANTITY, nullable = false)
    private Long quantity = 0L;

    @Column(name = Entities.Mapping.JoinColumn.PRODUCT_ID, insertable = false, updatable = false)
    private Long productId;

    @Column(name = Entities.Mapping.JoinColumn.RECEIPT_ID, insertable = false, updatable = false)
    private Long receiptId;

    @JsonCreator(mode = JsonCreator.Mode.DISABLED)
    public ReceiptItem() {
    }

    @JsonCreator(mode = JsonCreator.Mode.DISABLED)
    public ReceiptItem(Product product, Long quantity) {
        setProduct(product);
        setQuantity(quantity);
    }

    @JsonCreator(mode = JsonCreator.Mode.DISABLED)
    public ReceiptItem(Long id, Product product, Long quantity) {
        super(id);
        setProduct(product);
        setQuantity(quantity);
    }

    @JsonCreator(mode = JsonCreator.Mode.DISABLED)
    public ReceiptItem(Product product, Long quantity, Collection<ReceiptItemDiscount> receiptItemDiscounts) {
        setProduct(product);
        setQuantity(quantity);
        setDiscounts(receiptItemDiscounts);
    }

    @JsonCreator
    public ReceiptItem(@JsonProperty("id") Long id,
                       @JsonProperty("product") Product product,
                       @JsonProperty("quantity") Long quantity,
                       @JsonProperty("discounts") Collection<ReceiptItemDiscount> receiptItemDiscounts) {
        super(id);
        setProduct(product);
        setQuantity(quantity);
        setDiscounts(receiptItemDiscounts);
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        ThrowUtils.Argument.nullValue("quantity", quantity);
        ThrowUtils.Argument.lessThan("quantity", quantity, MIN_QUANTITY);
        this.quantity = quantity;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getReceiptId() {
        return receiptId;
    }

    public void setReceiptId(Long receiptId) {
        this.receiptId = receiptId;
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
    public Collection<ReceiptItemDiscount> getDiscounts() {
        return discounts;
    }

    @Override
    public void setDiscounts(Collection<ReceiptItemDiscount> receiptItemDiscounts) {
        clearDiscounts();
        if (receiptItemDiscounts != null)
            addDiscounts(receiptItemDiscounts);
    }

    @Override
    public void addDiscount(ReceiptItemDiscount receiptItemDiscount) {
        ThrowUtils.Argument.nullValue("receiptItemDiscounts", receiptItemDiscount);
        getDiscounts().add(receiptItemDiscount);
        receiptItemDiscount.getReceiptItems().add(this);
    }

    @Override
    public void addDiscounts(Collection<ReceiptItemDiscount> receiptItemDiscounts) {
        ThrowUtils.Argument.nullValue("receiptItemDiscounts", receiptItemDiscounts);
        receiptItemDiscounts.forEach(this::addDiscount);
    }

    @Override
    public void removeDiscount(ReceiptItemDiscount receiptItemDiscount) {
        ThrowUtils.Argument.nullValue("receiptItemDiscount", receiptItemDiscount);
        getDiscounts().remove(receiptItemDiscount);
        receiptItemDiscount.getReceiptItems().remove(this);
    }

    @Override
    public void removeDiscounts(Collection<ReceiptItemDiscount> discounts) {
        discounts.forEach(this::removeDiscount);
    }

    @Override
    public void clearDiscounts() {
        getDiscounts().forEach(discount -> discount.getReceiptItems().remove(this));
        getDiscounts().clear();
    }

    @StringifyIgnore
    @Override
    public Receipt getReceipt() {
        return receipt;
    }

    @Override
    public void setReceipt(Receipt receipt) {
        this.receipt = receipt;
    }

    public static class Builder implements IReceiptItemBuilder {

        protected final ReceiptItem receiptItem = new ReceiptItem();

        @Override
        public IReceiptItemBuilder setId(Long id) {
            receiptItem.setId(id);
            return this;
        }

        @Override
        public IReceiptItemBuilder setProduct(Product product) {
            receiptItem.setProduct(product);
            return this;
        }

        @Override
        public IReceiptItemBuilder setQuantity(Long quantity) {
            receiptItem.setQuantity(quantity);
            return this;
        }

        @Override
        public IReceiptItemBuilder setDiscounts(Collection<ReceiptItemDiscount> receiptItemDiscounts) {
            receiptItem.setDiscounts(receiptItemDiscounts);
            return this;
        }

        @Override
        public IReceiptItemBuilder addDiscount(ReceiptItemDiscount receiptItemDiscount) {
            receiptItem.addDiscount(receiptItemDiscount);
            return this;
        }

        @Override
        public IReceiptItemBuilder addDiscounts(Collection<ReceiptItemDiscount> receiptItemDiscounts) {
            receiptItem.addDiscounts(receiptItemDiscounts);
            return this;
        }

        @Override
        public ReceiptItem build() {
            throwIfInvalid();
            return receiptItem;
        }

        private void throwIfInvalid() {
            if (receiptItem.getProduct() == null)
                throw new IllegalArgumentException("Product is required");
        }
    }
}
