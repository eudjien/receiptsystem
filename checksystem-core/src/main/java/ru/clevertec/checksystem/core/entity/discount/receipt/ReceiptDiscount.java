package ru.clevertec.checksystem.core.entity.discount.receipt;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import ru.clevertec.checksystem.core.common.receipt.IReceiptAggregable;
import ru.clevertec.checksystem.core.entity.discount.AbstractDiscount;
import ru.clevertec.checksystem.core.entity.receipt.Receipt;
import ru.clevertec.checksystem.core.util.ThrowUtils;
import ru.clevertec.custom.json.StringifyIgnore;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static ru.clevertec.checksystem.core.Constants.Entities;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = Entities.Mapping.Table.RECEIPT_DISCOUNTS)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING, length = 124)
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS)
public abstract class ReceiptDiscount extends AbstractDiscount<ReceiptDiscount> implements IReceiptAggregable {

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "discounts")
    private final Set<Receipt> receipts = new HashSet<>();

    protected ReceiptDiscount() {
    }

    protected ReceiptDiscount(String description) {
        super(description);
    }

    protected ReceiptDiscount(String description, ReceiptDiscount dependentDiscount) {
        super(description, dependentDiscount);
    }

    @StringifyIgnore
    @Override
    public Collection<Receipt> getReceipts() {
        return receipts;
    }

    @Override
    public void setReceipts(Collection<Receipt> receipts) {
        clearReceipts();
        if (receipts != null)
            addReceipts(receipts);
    }

    @Override
    public void addReceipt(Receipt receipt) {
        ThrowUtils.Argument.nullValue("receipt", receipt);
        receipt.getDiscounts().add(this);
        getReceipts().add(receipt);
    }

    @Override
    public void addReceipts(Collection<Receipt> receipts) {
        ThrowUtils.Argument.nullValue("receipts", receipts);
        receipts.forEach(this::addReceipt);
    }

    @Override
    public void removeReceipt(Receipt receipt) {
        ThrowUtils.Argument.nullValue("receipt", receipt);
        receipt.getDiscounts().remove(this);
        getReceipts().remove(receipt);
    }

    @Override
    public void removeReceipts(Collection<Receipt> receipts) {
        ThrowUtils.Argument.nullValue("receipts", receipts);
        receipts.forEach(this::removeReceipt);
    }

    @Override
    public void clearReceipts() {
        getReceipts().forEach(receipt -> receipt.getDiscounts().remove(this));
        getReceipts().clear();
    }

    public abstract BigDecimal discountAmount(Receipt receipt);
}
