package ru.clevertec.checksystem.core.entity.discount.receipt;

import ru.clevertec.checksystem.core.common.IPercentable;
import ru.clevertec.checksystem.core.constant.Entities;
import ru.clevertec.checksystem.core.entity.receipt.Receipt;
import ru.clevertec.checksystem.core.util.ThrowUtils;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.math.BigDecimal;
import java.math.RoundingMode;

import static ru.clevertec.checksystem.core.constant.Constants.Percent;

@MappedSuperclass
public abstract class PercentageReceiptDiscount extends ReceiptDiscount implements IPercentable {

    @Column(name = Entities.Column.PERCENT, nullable = false)
    private Double percent = 0D;

    protected PercentageReceiptDiscount() {
    }

    protected PercentageReceiptDiscount(String description, Double percent) {
        super(description);
        setPercent(percent);
    }

    protected PercentageReceiptDiscount(String description, Double percent, ReceiptDiscount dependentDiscount) {
        super(description, dependentDiscount);
        setPercent(percent);
    }

    @Override
    public double getPercent() {
        return percent;
    }

    @Override
    public void setPercent(Double percent) {
        ThrowUtils.Argument.nullValue("percent", percent);
        ThrowUtils.Argument.outOfRange("percent", percent, Percent.MIN, Percent.MAX);
        this.percent = percent;
    }

    @Override
    public BigDecimal discountAmount(Receipt receipt) {

        var subTotalAmount = receipt.subTotalAmount();
        var dependentDiscountAmount = BigDecimal.ZERO;
        var itemsDiscountAmount = receipt.itemsDiscountSum();

        if (getDependentDiscount() != null) {
            subTotalAmount = subTotalAmount.subtract(getDependentDiscount().discountAmount(receipt));
            dependentDiscountAmount = getDependentDiscount().discountAmount(receipt);
        }

        subTotalAmount = subTotalAmount.subtract(itemsDiscountAmount);

        var discount = subTotalAmount
                .divide(BigDecimal.valueOf(Percent.MAX), RoundingMode.HALF_EVEN)
                .multiply(BigDecimal.valueOf(getPercent()));

        return discount.add(dependentDiscountAmount).add(itemsDiscountAmount);
    }
}
