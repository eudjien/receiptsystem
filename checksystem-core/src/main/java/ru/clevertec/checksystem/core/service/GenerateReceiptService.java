package ru.clevertec.checksystem.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.clevertec.checksystem.core.common.builder.IReceiptBuilder;
import ru.clevertec.checksystem.core.common.service.IGenerateReceiptService;
import ru.clevertec.checksystem.core.data.generate.ReceiptGenerate;
import ru.clevertec.checksystem.core.data.generate.ReceiptItemGenerate;
import ru.clevertec.checksystem.core.entity.BaseEntity;
import ru.clevertec.checksystem.core.entity.discount.receipt.ReceiptDiscount;
import ru.clevertec.checksystem.core.entity.discount.receiptitem.ReceiptItemDiscount;
import ru.clevertec.checksystem.core.entity.receipt.Receipt;
import ru.clevertec.checksystem.core.entity.receipt.ReceiptItem;
import ru.clevertec.checksystem.core.event.EventEmitter;
import ru.clevertec.checksystem.core.factory.io.ReceiptGenerateReaderFactory;
import ru.clevertec.checksystem.core.factory.io.ReceiptGenerateWriterFactory;
import ru.clevertec.checksystem.core.repository.ProductRepository;
import ru.clevertec.checksystem.core.repository.ReceiptDiscountRepository;
import ru.clevertec.checksystem.core.repository.ReceiptItemDiscountRepository;
import ru.clevertec.checksystem.core.util.CollectionUtils;
import ru.clevertec.custom.list.SinglyLinkedList;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class GenerateReceiptService extends EventEmitter<Object> implements IGenerateReceiptService {

    private final ReceiptGenerateWriterFactory receiptGenerateWriterFactory;
    private final ReceiptGenerateReaderFactory receiptGenerateReaderFactory;

    private final ProductRepository productRepository;
    private final ReceiptDiscountRepository receiptDiscountRepository;
    private final ReceiptItemDiscountRepository receiptItemDiscountRepository;

    @Autowired
    public GenerateReceiptService(ReceiptGenerateWriterFactory receiptGenerateWriterFactory,
                                  ReceiptGenerateReaderFactory receiptGenerateReaderFactory,
                                  ProductRepository productRepository,
                                  ReceiptDiscountRepository receiptDiscountRepository,
                                  ReceiptItemDiscountRepository receiptItemDiscountRepository) {
        this.receiptGenerateWriterFactory = receiptGenerateWriterFactory;
        this.receiptGenerateReaderFactory = receiptGenerateReaderFactory;
        this.productRepository = productRepository;
        this.receiptDiscountRepository = receiptDiscountRepository;
        this.receiptItemDiscountRepository = receiptItemDiscountRepository;
    }

    public Collection<ReceiptGenerate> toGenerate(Collection<Receipt> receipts) {

        var receiptGenerateArray = new ArrayList<ReceiptGenerate>();

        for (var receipt : receipts) {

            var newReceiptGenerate = new ReceiptGenerate(
                    receipt.getId(),
                    receipt.getName(),
                    receipt.getDescription(),
                    receipt.getAddress(),
                    receipt.getPhoneNumber(),
                    receipt.getCashier(),
                    receipt.getDate());

            newReceiptGenerate.setDiscountIds(getReceiptDiscountIds(receipt.getDiscounts()));
            newReceiptGenerate.setReceiptItems(getReceiptItemGenerates(receipt.getReceiptItems()));

            receiptGenerateArray.add(newReceiptGenerate);
        }

        return receiptGenerateArray;
    }

    public void toGenerate(Collection<Receipt> receipts, File destinationFile, String format) throws IOException {
        var receiptGenerates = toGenerate(receipts);
        receiptGenerateWriterFactory.instance(format).write(receiptGenerates, destinationFile);
    }

    public Collection<Receipt> fromGenerate(File sourceFile, String format) throws IOException {
        var receiptGenerates = receiptGenerateReaderFactory.instance(format).read(sourceFile);
        return fromGenerate(receiptGenerates);
    }

    public Collection<Receipt> fromGenerate(byte[] bytes, String format) throws IOException {
        var receiptGenerates = receiptGenerateReaderFactory.instance(format).read(bytes);
        return fromGenerate(receiptGenerates);
    }

    public Collection<Receipt> fromGenerate(Collection<ReceiptGenerate> receiptGenerates) {

        var receipts = new SinglyLinkedList<Receipt>();

        for (var receiptGenerate : receiptGenerates) {

            var receiptBuilder = new Receipt.Builder()
                    .setId(receiptGenerate.getId())
                    .setName(receiptGenerate.getName())
                    .setDescription(receiptGenerate.getDescription())
                    .setAddress(receiptGenerate.getAddress())
                    .setPhoneNumber(receiptGenerate.getPhoneNumber())
                    .setCashier(receiptGenerate.getCashier())
                    .setDate(receiptGenerate.getDate());

            if (!receiptGenerate.getReceiptItemGenerates().isEmpty())
                addReceiptItemsToReceipt(receiptBuilder, receiptGenerate.getReceiptItemGenerates());

            if (!receiptGenerate.getDiscountIds().isEmpty())
                receiptBuilder.addDiscounts(CollectionUtils.asCollection(
                        receiptDiscountRepository.findAllById(receiptGenerate.getDiscountIds())));

            receipts.add(receiptBuilder.build());
        }

        return receipts;
    }

    private void addReceiptItemsToReceipt(IReceiptBuilder receiptBuilder, Collection<ReceiptItemGenerate> receiptItemGenerates) {

        for (var receiptItemGenerate : receiptItemGenerates) {

            var product = productRepository.findById(receiptItemGenerate.getProductId()).orElseThrow(()
                    -> new NoSuchElementException(
                    "Product with id: " + receiptItemGenerate.getProductId() + " not found"));

            var receiptItemBuilder = new ReceiptItem.Builder()
                    .setProduct(product)
                    .setQuantity(receiptItemGenerate.getQuantity());

            if (!CollectionUtils.isNullOrEmpty(receiptItemGenerate.getDiscountIds()))
                receiptItemBuilder.addDiscounts(CollectionUtils.asCollection(
                        receiptItemDiscountRepository.findAllById(receiptItemGenerate.getDiscountIds())));

            receiptBuilder.addItem(receiptItemBuilder.build());
        }
    }

    private Collection<Long> getReceiptDiscountIds(Collection<ReceiptDiscount> receiptDiscounts) {
        return receiptDiscounts.stream()
                .map(BaseEntity::getId)
                .collect(Collectors.toList());
    }

    private Collection<Long> getReceiptItemDiscountIds(Collection<ReceiptItemDiscount> receiptItemDiscounts) {
        return receiptItemDiscounts.stream()
                .map(BaseEntity::getId)
                .collect(Collectors.toList());
    }

    private Collection<ReceiptItemGenerate> getReceiptItemGenerates(Collection<ReceiptItem> receiptItems) {
        return receiptItems.stream().map(receiptItem ->
                new ReceiptItemGenerate(
                        receiptItem.getProduct().getId(),
                        receiptItem.getQuantity(),
                        getReceiptItemDiscountIds(receiptItem.getDiscounts())))
                .collect(Collectors.toList());
    }
}
