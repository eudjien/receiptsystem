package ru.clevertec.checksystem.core.service;

import ru.clevertec.checksystem.core.BaseEntity;
import ru.clevertec.checksystem.core.DataSeed;
import ru.clevertec.checksystem.core.check.Check;
import ru.clevertec.checksystem.core.check.CheckItem;
import ru.clevertec.checksystem.core.check.generated.GeneratedCheck;
import ru.clevertec.checksystem.core.check.generated.GeneratedCheckItem;
import ru.clevertec.checksystem.core.factory.GeneratedCheckReaderFactory;
import ru.clevertec.checksystem.core.factory.GeneratedCheckWriterFactory;
import ru.clevertec.checksystem.core.log.LogLevel;
import ru.clevertec.checksystem.core.log.execution.AroundExecutionLog;
import ru.clevertec.normalino.list.NormalinoList;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

@AroundExecutionLog(level = LogLevel.INFO)
@CheckService
public class GenerateCheckService implements IGenerateCheckService {

    public Collection<GeneratedCheck> toGenerated(Collection<Check> checks) {

        var checkGenerateArray = new ArrayList<GeneratedCheck>();

        for (var check : checks) {

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

            if (check.getItems() != null) {

                var checkItems = check.getItems().stream().map(ci ->
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

    public void toGenerated(Collection<Check> checks, File file, String format) throws Exception {
        var generatedChecks = toGenerated(checks);
        var generateCheckReader = GeneratedCheckWriterFactory.create(format);
        generateCheckReader.write(generatedChecks, file);
    }

    public Collection<Check> fromGenerated(File file, String format) throws Exception {
        var generateCheckReader = GeneratedCheckReaderFactory.create(format);
        return fromGenerated(generateCheckReader.read(file));
    }

    public Collection<Check> fromGenerated(byte[] bytes, String format) throws Exception {
        var generateCheckReader = GeneratedCheckReaderFactory.create(format);
        return fromGenerated(generateCheckReader.read(bytes));
    }

    public Collection<Check> fromGenerated(Collection<GeneratedCheck> generates)
            throws Exception {

        var checkList = new NormalinoList<Check>();

        for (var generate : generates) {

            var newCheck = new Check(
                    generate.getId(),
                    generate.getName(),
                    generate.getDescription(),
                    generate.getAddress(),
                    generate.getPhoneNumber(),
                    generate.getCashier(),
                    generate.getDate());

            if (generate.getItems() != null) {
                addCheckItemsInMemory(newCheck, generate.getItems());
            }

            if (generate.getDiscountIds() != null) {
                addCheckDiscountsInMemory(newCheck, generate.getDiscountIds());
            }

            checkList.add(newCheck);
        }

        return checkList;
    }

    private void addCheckItemsInMemory(Check check, Collection<GeneratedCheckItem> generatedCheckItems)
            throws Exception {

        var products = DataSeed.products();

        for (var item : generatedCheckItems) {

            var product = products.stream()
                    .filter(a -> a.getId() == item.getProductId())
                    .findFirst()
                    .orElseThrow(() -> new Exception("Product with id: " + item.getProductId() + " not found"));

            var checkItem = new CheckItem(product, item.getQuantity());

            if (item.getDiscountIds() != null) {
                addCheckItemDiscountsInMemory(checkItem, item.getDiscountIds());
            }

            check.addItem(checkItem);
        }
    }

    private void addCheckItemDiscountsInMemory(CheckItem checkItem, Collection<Integer> checkItemDiscountIds)
            throws Exception {

        var checkItemDiscounts = DataSeed.checkItemDiscounts();

        for (var checkItemDiscountId : checkItemDiscountIds) {
            var checkItemDiscount = checkItemDiscounts.stream()
                    .filter(a -> a.getId() == checkItemDiscountId)
                    .findFirst()
                    .orElseThrow(() ->
                            new Exception("Check item discount with id: " + checkItemDiscountId + " not found"));
            checkItem.addDiscount(checkItemDiscount);
        }
    }

    private void addCheckDiscountsInMemory(Check check, Collection<Integer> checkDiscountIds)
            throws Exception {

        var checkDiscounts = DataSeed.checkDiscounts();

        for (var checkDiscountId : checkDiscountIds) {
            var checkItemDiscount = checkDiscounts.stream()
                    .filter(a -> a.getId() == checkDiscountId)
                    .findFirst()
                    .orElseThrow(() ->
                            new Exception("Check discount with id: " + checkDiscountId + " not found"));
            check.addDiscount(checkItemDiscount);
        }
    }
}
