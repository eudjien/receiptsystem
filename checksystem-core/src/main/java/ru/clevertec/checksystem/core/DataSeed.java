package ru.clevertec.checksystem.core;

import ru.clevertec.checksystem.core.check.Check;
import ru.clevertec.checksystem.core.check.CheckItem;
import ru.clevertec.checksystem.core.discount.check.SimpleConstantCheckDiscount;
import ru.clevertec.checksystem.core.discount.check.SimplePercentageOffCheckDiscount;
import ru.clevertec.checksystem.core.discount.check.base.CheckDiscount;
import ru.clevertec.checksystem.core.discount.item.QuantityThresholdPercentOffCheckItemDiscount;
import ru.clevertec.checksystem.core.discount.item.SimpleConstantCheckItemDiscount;
import ru.clevertec.checksystem.core.discount.item.SimplePercentageOffCheckItemDiscount;
import ru.clevertec.checksystem.core.discount.item.base.CheckItemDiscount;
import ru.clevertec.normalino.list.NormalinoList;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.IntStream;

public abstract class DataSeed {

    private static List<Product> products;
    private static NormalinoList<Check> checks;
    private static HashMap<String, CheckDiscount> checkDiscounts;
    private static HashMap<String, CheckItemDiscount> checkItemDiscounts;

    public static List<Product> Products() {
        if (products == null) {
            products = new NormalinoList<>();
            products.add(new Product("Пельмени", BigDecimal.valueOf(12.48)));
            products.add(new Product("Картошка", BigDecimal.valueOf(7.5)));
            products.add(new Product("Помидоры", BigDecimal.valueOf(3.91)));
            products.add(new Product("Сало", BigDecimal.valueOf(10)));
            products.add(new Product("Кукуруза", BigDecimal.valueOf(20)));
            products.add(new Product("Макароны", BigDecimal.valueOf(8.5)));
            products.add(new Product("Колбаса", BigDecimal.valueOf(20)));
            products.add(new Product("Рис", BigDecimal.valueOf(19.999)));
            products.add(new Product("Рыба", BigDecimal.valueOf(50.2)));
            products.add(new Product("Апельсины", BigDecimal.valueOf(30.5)));
            products.add(new Product("Яблоки", BigDecimal.valueOf(8.7)));
            products.add(new Product("Чай", BigDecimal.valueOf(3.51)));
            products.add(new Product("Кофе", BigDecimal.valueOf(3.5)));
            products.add(new Product("Печенье", BigDecimal.valueOf(2.1)));
            products.add(new Product("Конфеты", BigDecimal.valueOf(5.6)));
            products.add(new Product("Монитор", BigDecimal.valueOf(155.5)));
            products.add(new Product("Клавиатура", BigDecimal.valueOf(30)));
            products.add(new Product("Мышка", BigDecimal.valueOf(30)));
            products.add(new Product("SSD", BigDecimal.valueOf(50)));
            products.add(new Product("HDD", BigDecimal.valueOf(25.5)));

            IntStream.range(0, products.size()).forEach(i -> products.get(i).setId(i + 1));
        }
        return products;
    }

    public static NormalinoList<Check> Checks() {

        try {
            if (checks == null) {
                checks = new NormalinoList<>();

                var products = Products();

                // Check 1
                var check = new Check(1, "999 проблем", "Компьютерный магазин",
                        "ул. Пушкина, д. Калатушкина", "+375290000000", "Василий Пупкин",
                        new Date());

                var checkItems = new NormalinoList<CheckItem>();
                checkItems.add(new CheckItem(products.get(0), 3));
                checkItems.add(new CheckItem(products.get(1), 1));
                var discountedCheckItem = new CheckItem(products.get(2), 8);
                discountedCheckItem.addDiscount(new QuantityThresholdPercentOffCheckItemDiscount(
                        "Скидка 10% если количество продуктов больше 5", 10, 5));
                checkItems.add(discountedCheckItem);
                checkItems.add(new CheckItem(products.get(3), 9));
                checkItems.add(new CheckItem(products.get(4), 1));
                checkItems.add(new CheckItem(products.get(5), 2));

                addItemsToCheck(check, checkItems);

                check.addDiscount(new SimplePercentageOffCheckDiscount("Скидка 35% на общую сумму", 35));
                checks.add(check);

                // Check 2
                check = new Check(2, "342 элемент", "Гипермаркет",
                        "ул. Элементова, д. 1", "+375290000001", "Екатерина Пупкина",
                        new Date());

                checkItems = new NormalinoList<>();
                discountedCheckItem = new CheckItem(products.get(2), 8);
                CheckItemDiscount discount = new QuantityThresholdPercentOffCheckItemDiscount(
                        "Скидка 1% если количество продуктов больше 6", 1, 6);
                discount.setChildDiscount(new SimpleConstantCheckItemDiscount("Скидка 10 на продукт", BigDecimal.valueOf(10)));
                discountedCheckItem.addDiscount(discount);
                checkItems.add(discountedCheckItem);
                checkItems.add(new CheckItem(products.get(1), 10));
                checkItems.add(new CheckItem(products.get(3), 9));
                checkItems.add(new CheckItem(products.get(2), 8));
                discount = new SimplePercentageOffCheckItemDiscount("10%", 10);
                discountedCheckItem.addDiscount(discount);
                checkItems.add(discountedCheckItem);
                discountedCheckItem = new CheckItem(products.get(5), 7);
                discountedCheckItem.addDiscount(discount);
                checkItems.add(discountedCheckItem);
                checkItems.add(new CheckItem(products.get(7), 6));
                checkItems.add(new CheckItem(products.get(9), 5));

                addItemsToCheck(check, checkItems);

                check.addDiscount(new SimplePercentageOffCheckDiscount("Скидка 1% на общую сумму", 1));
                checks.add(check);

                // Check 3
                check = new Check(3, "Магазин №1", "Продуктовый магазин",
                        "ул. Советская, д. 1001", "+375290000002", "Алексей Пупкин",
                        new Date());

                checkItems = new NormalinoList<>();
                checkItems.add(new CheckItem(products.get(2), 15));
                checkItems.add(new CheckItem(products.get(4), 1));
                checkItems.add(new CheckItem(products.get(6), 1));
                checkItems.add(new CheckItem(products.get(8), 3));
                checkItems.add(new CheckItem(products.get(10), 11));
                checkItems.add(new CheckItem(products.get(12), 6));

                addItemsToCheck(check, checkItems);

                check.addDiscount(new SimplePercentageOffCheckDiscount("Скидка 1% на общую сумму", 1));
                checks.add(check);

                // Check 4
                check = new Check(4, "Магазин №2", "Продуктовый магазин",
                        "ул. Свиридова, д. 1234", "+375290000003", "Татьяна Пупкина",
                        new Date());

                checkItems = new NormalinoList<>();
                discountedCheckItem = new CheckItem(products.get(3), 15);
                discount = new QuantityThresholdPercentOffCheckItemDiscount(
                        "Скидка 10% если количество продуктов больше 2", 10, 2);
                discountedCheckItem.addDiscount(discount);
                checkItems.add(discountedCheckItem);

                checkItems.add(new CheckItem(products.get(5), 1));
                checkItems.add(new CheckItem(products.get(7), 1));
                checkItems.add(new CheckItem(products.get(9), 3));

                discountedCheckItem = new CheckItem(products.get(11), 15);
                discount = new SimpleConstantCheckItemDiscount("Скидка 5$", BigDecimal.valueOf(5));
                discountedCheckItem.addDiscount(discount);
                checkItems.add(discountedCheckItem);

                checkItems.add(new CheckItem(products.get(13), 6));
                checkItems.add(new CheckItem(products.get(15), 3));
                checkItems.add(new CheckItem(products.get(17), 11));
                checkItems.add(new CheckItem(products.get(19), 6));

                addItemsToCheck(check, checkItems);

                check.addDiscount(new SimplePercentageOffCheckDiscount("Скидка 20% на общую сумму", 20));
                checks.add(check);
            }
        } catch (Exception ignored) {
        }
        return checks;
    }

    public static HashMap<String, CheckDiscount> CheckDiscounts() {
        try {
            if (checkDiscounts == null) {
                checkDiscounts = new HashMap<>();
                checkDiscounts.put("1",
                        new SimplePercentageOffCheckDiscount(1, "-30% на сумму чека", 30));
                checkDiscounts.put("2",
                        new SimplePercentageOffCheckDiscount(2, "-20% на сумму чека", 20));
                checkDiscounts.put("3",
                        new SimpleConstantCheckDiscount(3, "-5$ на сумму чека", BigDecimal.valueOf(5)));
                checkDiscounts.put("4",
                        new SimpleConstantCheckDiscount(4, "-10$ на сумму чека", BigDecimal.valueOf(10)));

                // (сумма - 35%) - 10
                checkDiscounts.put("5",
                        new SimpleConstantCheckDiscount(5, "-10$", BigDecimal.valueOf(10),
                                new SimplePercentageOffCheckDiscount(6, "-35%", 35)));

                // (сумма - 10) - 35%
                checkDiscounts.put("6",
                        new SimplePercentageOffCheckDiscount(7, "-35%", 35,
                                new SimpleConstantCheckDiscount(8, "-10$", BigDecimal.valueOf(10))));

                // (сумма - 15%) - 10) - 35%
                checkDiscounts.put("7",
                        new SimplePercentageOffCheckDiscount(7, "-35%", 35,
                                new SimpleConstantCheckDiscount(8, "-10$", BigDecimal.valueOf(10),
                                        new SimplePercentageOffCheckDiscount(7, "-15%", 15))));
            }
        } catch (Exception ignored) {
        }
        return checkDiscounts;
    }

    public static HashMap<String, CheckItemDiscount> CheckItemDiscounts() {
        try {
            if (checkItemDiscounts == null) {
                checkItemDiscounts = new HashMap<>();
                checkItemDiscounts.put("1", new SimpleConstantCheckItemDiscount("-5$", BigDecimal.valueOf(5)));
                checkItemDiscounts.put("2", new SimplePercentageOffCheckItemDiscount("-30%", 30));
                checkItemDiscounts.put("3", new SimplePercentageOffCheckItemDiscount("-40%", 40));
                checkItemDiscounts.put("4", new SimplePercentageOffCheckItemDiscount("-50%", 50));
                checkItemDiscounts.put("5", new QuantityThresholdPercentOffCheckItemDiscount(
                        "-10% если количество продукта больше чем 5", 10, 5));
            }
        } catch (Exception ignored) {
        }
        return checkItemDiscounts;
    }

    private static void addItemsToCheck(Check check, List<CheckItem> items) {
        for (int i = 0; i < items.size(); i++) {
            items.get(i).setId(i + 1);
            check.addItem(items.get(i));
        }
    }
}
