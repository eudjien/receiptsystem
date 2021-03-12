package ru.clevertec.checksystem.core.common.receipt;

import ru.clevertec.checksystem.core.entity.receipt.Receipt;

public interface IReceiptComposable {

    Receipt getReceipt();

    void setReceipt(Receipt receipt);
}
