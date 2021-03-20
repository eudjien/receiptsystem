package ru.clevertec.checksystem.core.service;

import ru.clevertec.checksystem.core.entity.receipt.Receipt;

import java.util.Collection;
import java.util.List;

public interface IReceiptService {
    Receipt findById(Long id);

    List<Receipt> findAll();

    List<Receipt> findAllById(Collection<Long> receiptIds);
}
