package ru.clevertec.checksystem.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.clevertec.checksystem.core.common.builder.ICheckBuilder;
import ru.clevertec.checksystem.core.common.service.IGenerateCheckService;
import ru.clevertec.checksystem.core.dto.CheckGenerate;
import ru.clevertec.checksystem.core.dto.CheckItemGenerate;
import ru.clevertec.checksystem.core.entity.BaseEntity;
import ru.clevertec.checksystem.core.entity.check.Check;
import ru.clevertec.checksystem.core.entity.check.CheckItem;
import ru.clevertec.checksystem.core.entity.discount.check.CheckDiscount;
import ru.clevertec.checksystem.core.entity.discount.checkitem.CheckItemDiscount;
import ru.clevertec.checksystem.core.event.EventEmitter;
import ru.clevertec.checksystem.core.factory.io.CheckGenerateReaderFactory;
import ru.clevertec.checksystem.core.factory.io.CheckGenerateWriterFactory;
import ru.clevertec.checksystem.core.repository.CheckDiscountRepository;
import ru.clevertec.checksystem.core.repository.CheckItemDiscountRepository;
import ru.clevertec.checksystem.core.repository.ProductRepository;
import ru.clevertec.checksystem.core.util.CollectionUtils;
import ru.clevertec.custom.list.SinglyLinkedList;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class GenerateCheckService extends EventEmitter<Object> implements IGenerateCheckService {

    private final CheckGenerateWriterFactory checkGenerateWriterFactory;
    private final CheckGenerateReaderFactory checkGenerateReaderFactory;

    private final ProductRepository productRepository;
    private final CheckDiscountRepository checkDiscountRepository;
    private final CheckItemDiscountRepository checkItemDiscountRepository;

    @Autowired
    public GenerateCheckService(CheckGenerateWriterFactory checkGenerateWriterFactory,
                                CheckGenerateReaderFactory checkGenerateReaderFactory,
                                ProductRepository productRepository,
                                CheckDiscountRepository checkDiscountRepository,
                                CheckItemDiscountRepository checkItemDiscountRepository) {
        this.checkGenerateWriterFactory = checkGenerateWriterFactory;
        this.checkGenerateReaderFactory = checkGenerateReaderFactory;
        this.productRepository = productRepository;
        this.checkDiscountRepository = checkDiscountRepository;
        this.checkItemDiscountRepository = checkItemDiscountRepository;
    }

    public Collection<CheckGenerate> toGenerate(Collection<Check> checks) {

        var checkGenerateArray = new ArrayList<CheckGenerate>();

        for (var check : checks) {

            var newCheckGenerate = new CheckGenerate(
                    check.getId(),
                    check.getName(),
                    check.getDescription(),
                    check.getAddress(),
                    check.getPhoneNumber(),
                    check.getCashier(),
                    check.getDate());

            newCheckGenerate.setDiscountIds(getCheckDiscountIds(check.getDiscounts()));
            newCheckGenerate.setCheckItems(getCheckItemGenerates(check.getCheckItems()));

            checkGenerateArray.add(newCheckGenerate);
        }

        return checkGenerateArray;
    }

    public void toGenerate(Collection<Check> checks, File destinationFile, String format) throws IOException {
        var checkGenerates = toGenerate(checks);
        checkGenerateWriterFactory.instance(format).write(checkGenerates, destinationFile);
    }

    public Collection<Check> fromGenerate(File sourceFile, String format) throws IOException {
        var checkGenerates = checkGenerateReaderFactory.instance(format).read(sourceFile);
        return fromGenerate(checkGenerates);
    }

    public Collection<Check> fromGenerate(byte[] bytes, String format) throws IOException {
        var checkGenerates = checkGenerateReaderFactory.instance(format).read(bytes);
        return fromGenerate(checkGenerates);
    }

    public Collection<Check> fromGenerate(Collection<CheckGenerate> checkGenerates) {

        var checks = new SinglyLinkedList<Check>();

        for (var checkGenerate : checkGenerates) {

            var checkBuilder = new Check.Builder()
                    .setId(checkGenerate.getId())
                    .setName(checkGenerate.getName())
                    .setDescription(checkGenerate.getDescription())
                    .setAddress(checkGenerate.getAddress())
                    .setPhoneNumber(checkGenerate.getPhoneNumber())
                    .setCashier(checkGenerate.getCashier())
                    .setDate(checkGenerate.getDate());

            if (!checkGenerate.getCheckItemGenerates().isEmpty())
                addCheckItemsToCheck(checkBuilder, checkGenerate.getCheckItemGenerates());

            if (!checkGenerate.getDiscountIds().isEmpty())
                checkBuilder.addDiscounts(CollectionUtils.asCollection(
                        checkDiscountRepository.findAllById(checkGenerate.getDiscountIds())));

            checks.add(checkBuilder.build());
        }

        return checks;
    }

    private void addCheckItemsToCheck(ICheckBuilder checkBuilder, Collection<CheckItemGenerate> checkItemGenerates) {

        for (var checkItemGenerate : checkItemGenerates) {

            var product = productRepository.findById(checkItemGenerate.getProductId()).orElseThrow(()
                    -> new NoSuchElementException(
                    "Product with id: " + checkItemGenerate.getProductId() + " not found"));

            var checkItemBuilder = new CheckItem.Builder()
                    .setProduct(product)
                    .setQuantity(checkItemGenerate.getQuantity());

            if (!CollectionUtils.isNullOrEmpty(checkItemGenerate.getDiscountIds()))
                checkItemBuilder.addDiscounts(CollectionUtils.asCollection(
                        checkItemDiscountRepository.findAllById(checkItemGenerate.getDiscountIds())));

            checkBuilder.addItem(checkItemBuilder.build());
        }
    }

    private Collection<Long> getCheckDiscountIds(Collection<CheckDiscount> checkDiscounts) {
        return checkDiscounts.stream()
                .map(BaseEntity::getId)
                .collect(Collectors.toList());
    }

    private Collection<Long> getCheckItemDiscountIds(Collection<CheckItemDiscount> checkItemDiscounts) {
        return checkItemDiscounts.stream()
                .map(BaseEntity::getId)
                .collect(Collectors.toList());
    }

    private Collection<CheckItemGenerate> getCheckItemGenerates(Collection<CheckItem> checkItems) {
        return checkItems.stream().map(checkItem ->
                new CheckItemGenerate(
                        checkItem.getProduct().getId(),
                        checkItem.getQuantity(),
                        getCheckItemDiscountIds(checkItem.getDiscounts())))
                .collect(Collectors.toList());
    }
}
