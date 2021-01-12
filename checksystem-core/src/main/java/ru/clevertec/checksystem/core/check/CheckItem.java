package ru.clevertec.checksystem.core.check;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import ru.clevertec.checksystem.core.BaseEntity;
import ru.clevertec.checksystem.core.Product;
import ru.clevertec.checksystem.core.discount.Discount;
import ru.clevertec.checksystem.core.discount.item.base.CheckItemDiscount;
import ru.clevertec.checksystem.normalino.list.NormalinoList;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

public class CheckItem extends BaseEntity {

    private final List<CheckItemDiscount> discounts = new NormalinoList<>();
    private Product product;
    private int quantity;

    public CheckItem(Product product, int quantity) throws Exception {
        setProduct(product);
        setQuantity(quantity);
    }

    public CheckItem(int id, Product product, int quantity) throws Exception {
        super(id);
        setProduct(product);
        setQuantity(quantity);
    }

    public CheckItem(Product product, int quantity, List<CheckItemDiscount> discounts) throws Exception {
        setProduct(product);
        setQuantity(quantity);
        setDiscounts(discounts);
    }

    @JsonCreator
    public CheckItem(@JsonProperty("id") int id,
                     @JsonProperty("product") Product product,
                     @JsonProperty("quantity") int quantity,
                     @JsonProperty("discounts") List<CheckItemDiscount> discounts) throws Exception {
        super(id);
        setProduct(product);
        setQuantity(quantity);
        setDiscounts(discounts);
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) throws Exception {
        if (product == null) {
            throw new Exception("Product cannot be null");
        }
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) throws Exception {
        if (quantity < 1) {
            throw new Exception("quantity cannot be less than 1");
        }
        this.quantity = quantity;
    }

    // Сумма продукта с учетом скидок
    public BigDecimal total() {
        return subTotal().subtract(discountsSum());
    }

    // Сумма продукта без скидок
    public BigDecimal subTotal() {
        return getProduct().getPrice().multiply(BigDecimal.valueOf(getQuantity()));
    }

    // Сумма всех скидок на продукт
    public BigDecimal discountsSum() {
        return getDiscounts().stream().map(Discount::discountSum).reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
    }

    public List<CheckItemDiscount> getDiscounts() {
        return Collections.unmodifiableList(discounts);
    }

    public void setDiscounts(List<CheckItemDiscount> discounts) throws Exception {
        if (discounts != null) {
            for (var discount : discounts) {
                addDiscount(discount);
            }
        }
    }

    public void addDiscount(CheckItemDiscount discount) throws Exception {
        discount.setCheckItem(this);
        this.discounts.add(discount);
    }

    public void deleteDiscount(CheckItemDiscount discount) {
        var index = discountIndex(discount);
        if (index != -1) {
            this.discounts.remove(index);
        }
    }

    private int discountIndex(CheckItemDiscount discount) {
        for (int i = 0; i < discounts.size(); i++)
            if (discounts.get(i).getId() == discount.getId())
                return i;
        return -1;
    }
}
