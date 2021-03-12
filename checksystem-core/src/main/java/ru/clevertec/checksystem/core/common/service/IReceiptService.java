package ru.clevertec.checksystem.core.common.service;

import ru.clevertec.checksystem.core.repository.ReceiptItemRepository;
import ru.clevertec.checksystem.core.repository.ReceiptRepository;

public interface IReceiptService extends IService {
    ReceiptRepository getReceiptRepository();

    ReceiptItemRepository getReceiptItemRepository();
}
