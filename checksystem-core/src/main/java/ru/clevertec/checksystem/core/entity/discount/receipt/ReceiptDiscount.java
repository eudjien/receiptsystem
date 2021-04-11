package ru.clevertec.checksystem.core.entity.discount.receipt;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.*;
import lombok.experimental.SuperBuilder;
import ru.clevertec.checksystem.core.constant.Entities;
import ru.clevertec.checksystem.core.entity.discount.AbstractDiscount;
import ru.clevertec.checksystem.core.entity.receipt.Receipt;
import ru.clevertec.checksystem.core.util.ThrowUtils;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = true, exclude = {"receipts"})
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = Entities.Table.RECEIPT_DISCOUNTS)
@DiscriminatorColumn(name = Entities.DiscriminatorNames.RECEIPT_DISCOUNT, discriminatorType = DiscriminatorType.STRING, length = 124)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME)
public abstract class ReceiptDiscount extends AbstractDiscount<ReceiptDiscount> {

    @Builder.Default
    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "discounts")
    private Set<Receipt> receipts = new HashSet<>();

    public void addReceipt(Receipt receipt) {
        ThrowUtils.Argument.nullValue("receipt", receipt);
        getReceipts().add(receipt);
        receipt.getDiscounts().add(this);
    }

    public void removeReceipt(Receipt receipt) {
        ThrowUtils.Argument.nullValue("receipt", receipt);
        getReceipts().remove(receipt);
        receipt.getDiscounts().remove(this);
    }
}
