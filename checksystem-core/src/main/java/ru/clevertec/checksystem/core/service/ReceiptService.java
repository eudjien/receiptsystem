package ru.clevertec.checksystem.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.clevertec.checksystem.core.common.service.IReceiptService;
import ru.clevertec.checksystem.core.event.EventEmitter;
import ru.clevertec.checksystem.core.repository.ReceiptItemRepository;
import ru.clevertec.checksystem.core.repository.ReceiptRepository;

@Service
public class ReceiptService extends EventEmitter<Object> implements IReceiptService {

    private final ReceiptRepository receiptRepository;
    private final ReceiptItemRepository receiptItemRepository;

    @Autowired
    public ReceiptService(ReceiptRepository receiptRepository, ReceiptItemRepository receiptItemRepository) {
        this.receiptRepository = receiptRepository;
        this.receiptItemRepository = receiptItemRepository;
    }

    public ReceiptRepository getReceiptRepository() {
        return receiptRepository;
    }

    public ReceiptItemRepository getReceiptItemRepository() {
        return receiptItemRepository;
    }
}
