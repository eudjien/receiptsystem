package ru.clevertec.checksystem.core.service;

import ru.clevertec.checksystem.core.common.builder.ICheckBuilder;
import ru.clevertec.checksystem.core.common.service.IGenerateCheckService;
import ru.clevertec.checksystem.core.data.DataSeed;
import ru.clevertec.checksystem.core.entity.BaseEntity;
import ru.clevertec.checksystem.core.entity.check.Check;
import ru.clevertec.checksystem.core.entity.check.CheckItem;
import ru.clevertec.checksystem.core.entity.check.GeneratedCheck;
import ru.clevertec.checksystem.core.entity.check.GeneratedCheckItem;
import ru.clevertec.checksystem.core.event.EventEmitter;
import ru.clevertec.checksystem.core.factory.io.GeneratedCheckReaderFactory;
import ru.clevertec.checksystem.core.factory.io.GeneratedCheckWriterFactory;
import ru.clevertec.normalino.list.NormalinoList;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

public class GenerateCheckService extends EventEmitter<Object> implements IGenerateCheckService {

    public Collection<GeneratedCheck> toGenerated(Collection<Check> checkCollection) {

        var checkGenerateArray = new ArrayList<GeneratedCheck>();

        for (var check : checkCollection) {

            var newCheckGenerate = new GeneratedCheck(
                    check.getId(),
                    check.getName(),
                    check.getDescription(),
                    check.getAddress(),
                    check.getPhoneNumber(),
                    check.getCashier(),
                    check.getDate());


            if (check.getDiscounts() != null) {
                var discountIds = check.getDiscounts().stream()
                        .map(BaseEntity::getId).collect(Collectors.toList());

                newCheckGenerate.setDiscountIds(discountIds);
            }

            if (check.getCheckItems() != null) {

                var checkItems = check.getCheckItems().stream().map(ci ->
                        new GeneratedCheckItem(
                                ci.getProduct().getId(),
                                ci.getQuantity(),
                                ci.getDiscounts().stream().map(BaseEntity::getId).collect(Collectors.toList())))
                        .collect(Collectors.toList());

                newCheckGenerate.setItems(checkItems);
            }

            checkGenerateArray.add(newCheckGenerate);
        }

        return checkGenerateArray;
    }

    public void toGenerated(Collection<Check> checkCollection, File destinationFile, String format)
            throws IllegalArgumentException, IOException {
        var generatedChecks = toGenerated(checkCollection);
        var generateCheckReader = GeneratedCheckWriterFactory.create(format);
        generateCheckReader.write(generatedChecks, destinationFile);
    }

    public Collection<Check> fromGenerated(File sourceFile, String format)
            throws IllegalArgumentException, IOException {
        var generateCheckReader = GeneratedCheckReaderFactory.create(format);
        return fromGenerated(generateCheckReader.read(sourceFile));
    }

    public Collection<Check> fromGenerated(byte[] bytes, String format)
            throws NoSuchElementException, IllegalArgumentException, IOException {
        var generateCheckReader = GeneratedCheckReaderFactory.create(format);
        return fromGenerated(generateCheckReader.read(bytes));
    }

    public Collection<Check> fromGenerated(Collection<GeneratedCheck> generatedChecks)
            throws NoSuchElementException, IllegalArgumentException {

        var checkList = new NormalinoList<Check>();

        for (var generatedCheck : generatedChecks) {

            var builder = new Check.Builder()
                    .setId(generatedCheck.getId())
                    .setName(generatedCheck.getName())
                    .setDescription(generatedCheck.getDescription())
                    .setAddress(generatedCheck.getAddress())
                    .setPhoneNumber(generatedCheck.getPhoneNumber())
                    .setCashier(generatedCheck.getCashier())
                    .setDate(generatedCheck.getDate());

            if (generatedCheck.getItems() != null) {
                addCheckItemsInMemory(builder, generatedCheck.getItems());
            }

            if (generatedCheck.getDiscountIds() != null) {
                addCheckDiscountsInMemory(builder, generatedCheck.getDiscountIds());
            }

            checkList.add(builder.build());
        }

        return checkList;
    }

    private void addCheckItemsInMemory(ICheckBuilder checkBuilder, Collection<GeneratedCheckItem> generatedCheckItems)
            throws NoSuchElementException {

        var products = DataSeed.products();

        for (var generatedCheckItem : generatedCheckItems) {

            var product = products.stream()
                    .filter(a -> a.getId() == generatedCheckItem.getProductId())
                    .findFirst()
                    .orElseThrow(() ->
                            new NoSuchElementException(
                                    "Product with id: " + generatedCheckItem.getProductId() + " not found"));

            var checkItem = new CheckItem.Builder()
                    .setProduct(product)
                    .setQuantity(generatedCheckItem.getQuantity())
                    .build();

            if (generatedCheckItem.getDiscountIds() != null) {
                addCheckItemDiscountsInMemory(checkItem, generatedCheckItem.getDiscountIds());
            }

            checkBuilder.setItems(checkItem);
        }
    }

    private void addCheckItemDiscountsInMemory(CheckItem checkItem, Collection<Integer> checkItemDiscountIds)
            throws NoSuchElementException, IllegalArgumentException {

        var checkItemDiscounts = DataSeed.checkItemDiscounts();

        for (var checkItemDiscountId : checkItemDiscountIds) {
            var checkItemDiscount = checkItemDiscounts.stream()
                    .filter(a -> a.getId() == checkItemDiscountId)
                    .findFirst()
                    .orElseThrow(() -> new NoSuchElementException(
                            "Check item discount with id: " + checkItemDiscountId + " not found"));
            checkItem.putDiscount(checkItemDiscount);
        }
    }

    private void addCheckDiscountsInMemory(ICheckBuilder checkBuilder, Collection<Integer> checkDiscountIds)
            throws NoSuchElementException, IllegalArgumentException {

        var checkDiscounts = DataSeed.checkDiscounts();

        for (var checkDiscountId : checkDiscountIds) {
            var checkItemDiscount = checkDiscounts.stream()
                    .filter(a -> a.getId() == checkDiscountId)
                    .findFirst()
                    .orElseThrow(() ->
                            new NoSuchElementException("Check discount with id: " + checkDiscountId + " not found"));
            checkBuilder.setDiscounts(checkItemDiscount);
        }
    }
}
