package ru.clevertec.checksystem.webmvcjdbc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.clevertec.checksystem.core.entity.discount.receipt.ReceiptDiscount;
import ru.clevertec.checksystem.core.entity.discount.receiptitem.ReceiptItemDiscount;
import ru.clevertec.checksystem.core.entity.receipt.Receipt;
import ru.clevertec.checksystem.core.entity.receipt.ReceiptItem;
import ru.clevertec.checksystem.core.service.IReceiptService;
import ru.clevertec.checksystem.webmvcjdbc.dao.ProductDao;
import ru.clevertec.checksystem.webmvcjdbc.dao.ReceiptDao;
import ru.clevertec.checksystem.webmvcjdbc.dao.ReceiptItemDao;
import ru.clevertec.checksystem.webmvcjdbc.dao.discount.receipt.ReceiptDiscountDao;
import ru.clevertec.checksystem.webmvcjdbc.dao.discount.receiptItem.ReceiptItemDiscountDao;

import java.util.Collection;
import java.util.List;

@Service
public class ReceiptDaoSpecificService implements IReceiptService {

    private final ReceiptDao receiptDao;
    private final ReceiptItemDao receiptItemDao;
    private final ReceiptDiscountDao receiptDiscountDao;
    private final ReceiptItemDiscountDao receiptItemDiscountDao;
    private final ProductDao productDao;

    @Autowired
    public ReceiptDaoSpecificService(
            ReceiptDao receiptDao,
            ReceiptItemDao receiptItemDao,
            ReceiptDiscountDao receiptDiscountDao,
            ReceiptItemDiscountDao receiptItemDiscountDao,
            ProductDao productDao) {
        this.receiptDao = receiptDao;
        this.receiptItemDao = receiptItemDao;
        this.receiptDiscountDao = receiptDiscountDao;
        this.receiptItemDiscountDao = receiptItemDiscountDao;
        this.productDao = productDao;
    }

    @Override
    public Receipt findById(Long id) {
        var receipt = receiptDao.findById(id);
        includeAllToReceipt(receipt);
        return receipt;
    }

    @Override
    public List<Receipt> findAll() {
        var receipts = receiptDao.findAll();
        receipts.forEach(this::includeAllToReceipt);
        return receipts;
    }

    @Override
    public List<Receipt> findAllById(Collection<Long> receiptIds) {
        var receipts = receiptDao.findAllById(receiptIds);
        receipts.forEach(this::includeAllToReceipt);
        return receipts;
    }

    private void includeAllToReceipt(Receipt receipt) {

        var receiptDiscounts = receiptDiscountDao.findAllByReceiptId(receipt.getId());
        receiptDiscounts.forEach(this::includeAllDependentReceiptDiscounts);
        receipt.setDiscounts(receiptDiscounts);

        var receiptItems = receiptItemDao.findAllByReceiptId(receipt.getId());
        receiptItems.forEach(this::includeAllToReceiptItem);
        receipt.setReceiptItems(receiptItems);
    }

    private void includeAllToReceiptItem(ReceiptItem receiptItem) {

        var receiptItemDiscounts = receiptItemDiscountDao.findAllByReceiptItemId(receiptItem.getId());
        receiptItemDiscounts.forEach(this::includeAllDependentReceiptItemDiscounts);
        receiptItem.setDiscounts(receiptItemDiscounts);

        var product = productDao.findById(receiptItem.getProductId());
        receiptItem.setProduct(product);
    }

    private void includeAllDependentReceiptDiscounts(ReceiptDiscount receiptDiscount) {
        var currentDiscount = receiptDiscount;
        while (currentDiscount.getDependentDiscountId() != 0L) {
            var dependentDiscount = receiptDiscountDao.findById(currentDiscount.getDependentDiscountId());
            currentDiscount.setDependentDiscount(dependentDiscount);
            currentDiscount = dependentDiscount;
        }
    }

    private void includeAllDependentReceiptItemDiscounts(ReceiptItemDiscount receiptItemDiscount) {
        var currentDiscount = receiptItemDiscount;
        while (currentDiscount.getDependentDiscountId() != 0L) {
            var dependentDiscount = receiptItemDiscountDao.findById(currentDiscount.getDependentDiscountId());
            currentDiscount.setDependentDiscount(dependentDiscount);
            currentDiscount = dependentDiscount;
        }
    }
}
