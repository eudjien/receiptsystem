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
import java.util.Collection;
import java.util.Date;
import java.util.List;

public abstract class DataSeed {

    private static Collection<Product> products;
    private static Collection<Check> checks;
    private static Collection<CheckDiscount> checkDiscounts;
    private static Collection<CheckItemDiscount> checkItemDiscounts;

    public static Collection<Product> products() {
        if (products == null) {
            products = new NormalinoList<>();
            products.add(new Product(1, "Пельмени", BigDecimal.valueOf(12.48)));
            products.add(new Product(2, "Картошка", BigDecimal.valueOf(7.5)));
            products.add(new Product(3, "Помидоры", BigDecimal.valueOf(3.91)));
            products.add(new Product(4, "Сало", BigDecimal.valueOf(10)));
            products.add(new Product(5, "Кукуруза", BigDecimal.valueOf(20)));
            products.add(new Product(6, "Макароны", BigDecimal.valueOf(8.5)));
            products.add(new Product(7, "Колбаса", BigDecimal.valueOf(20)));
            products.add(new Product(8, "Рис", BigDecimal.valueOf(19.999)));
            products.add(new Product(9, "Рыба", BigDecimal.valueOf(50.2)));
            products.add(new Product(10, "Апельсины", BigDecimal.valueOf(30.5)));
            products.add(new Product(11, "Яблоки", BigDecimal.valueOf(8.7)));
            products.add(new Product(12, "Чай", BigDecimal.valueOf(3.51)));
            products.add(new Product(13, "Кофе", BigDecimal.valueOf(3.5)));
            products.add(new Product(14, "Печенье", BigDecimal.valueOf(2.1)));
            products.add(new Product(15, "Конфеты", BigDecimal.valueOf(5.6)));
            products.add(new Product(16, "Монитор", BigDecimal.valueOf(155.5)));
            products.add(new Product(17, "Клавиатура", BigDecimal.valueOf(30)));
            products.add(new Product(18, "Мышка", BigDecimal.valueOf(30)));
            products.add(new Product(19, "SSD", BigDecimal.valueOf(50)));
            products.add(new Product(20, "HDD", BigDecimal.valueOf(25.5)));
        }
        return products;
    }

    public static Collection<Check> checks() {

        var checkDiscounts = checkDiscounts().toArray(CheckDiscount[]::new);
        var checkItemDiscounts = checkItemDiscounts().toArray(CheckItemDiscount[]::new);

        try {
            if (checks == null) {
                checks = new NormalinoList<>();

                var products = products().toArray(Product[]::new);

                // Check 1
                var check = new Check(1, "999 проблем", "Компьютерный магазин",
                        "ул. Пушкина, д. Калатушкина", "+375290000000", "Василий Пупкин",
                        new Date());

                var checkItems = new NormalinoList<CheckItem>();
                checkItems.add(new CheckItem(221, products[0], 3));
                checkItems.add(new CheckItem(222, products[1], 1));
                var discountedCheckItem = new CheckItem(1, products[2], 8);
                discountedCheckItem.addDiscount(checkItemDiscounts[4]);
                checkItems.add(discountedCheckItem);
                checkItems.add(new CheckItem(2, products[3], 9));
                checkItems.add(new CheckItem(3, products[4], 1));
                checkItems.add(new CheckItem(4, products[5], 2));

                addItemsToCheck(check, checkItems);

                check.addDiscount(checkDiscounts[2]);
                checks.add(check);

                // Check 2
                check = new Check(2, "342 элемент", "Гипермаркет",
                        "ул. Элементова, д. 1", "+375290000001", "Екатерина Пупкина",
                        new Date());

                checkItems = new NormalinoList<>();
                discountedCheckItem = new CheckItem(500, products[2], 8);
                discountedCheckItem.addDiscount(checkItemDiscounts[5]);
                checkItems.add(discountedCheckItem);
                checkItems.add(new CheckItem(501, products[1], 10));
                checkItems.add(new CheckItem(502, products[3], 9));
                checkItems.add(new CheckItem(503, products[2], 8));
                discountedCheckItem.addDiscount(checkItemDiscounts[1]);
                checkItems.add(discountedCheckItem);
                discountedCheckItem = new CheckItem(44, products[5], 7);
                discountedCheckItem.addDiscount(checkItemDiscounts[2]);
                checkItems.add(discountedCheckItem);
                checkItems.add(new CheckItem(45, products[7], 6));
                checkItems.add(new CheckItem(46, products[9], 5));

                addItemsToCheck(check, checkItems);

                check.addDiscount(checkDiscounts[0]);
                checks.add(check);

                // Check 3
                check = new Check(3, "Магазин №1", "Продуктовый магазин",
                        "ул. Советская, д. 1001", "+375290000002", "Алексей Пупкин",
                        new Date());

                checkItems = new NormalinoList<>();
                checkItems.add(new CheckItem(125, products[2], 15));
                checkItems.add(new CheckItem(126, products[4], 1));
                checkItems.add(new CheckItem(127, products[6], 1));
                checkItems.add(new CheckItem(128, products[8], 3));
                checkItems.add(new CheckItem(129, products[10], 11));
                checkItems.add(new CheckItem(130, products[12], 6));

                addItemsToCheck(check, checkItems);

                check.addDiscount(checkDiscounts[1]);
                checks.add(check);

                // Check 4
                check = new Check(4, "Магазин №2", "Продуктовый магазин",
                        "ул. Свиридова, д. 1234", "+375290000003", "Татьяна Пупкина",
                        new Date());

                checkItems = new NormalinoList<>();
                discountedCheckItem = new CheckItem(600, products[3], 15);
                discountedCheckItem.addDiscount(checkItemDiscounts[6]);
                checkItems.add(discountedCheckItem);

                checkItems.add(new CheckItem(131, products[5], 1));
                checkItems.add(new CheckItem(132, products[7], 1));
                checkItems.add(new CheckItem(133, products[9], 3));

                discountedCheckItem = new CheckItem(134, products[11], 15);
                discountedCheckItem.addDiscount(checkItemDiscounts[0]);
                checkItems.add(discountedCheckItem);

                checkItems.add(new CheckItem(134, products[13], 6));
                checkItems.add(new CheckItem(135, products[15], 3));
                checkItems.add(new CheckItem(136, products[17], 11));
                checkItems.add(new CheckItem(138, products[19], 6));

                addItemsToCheck(check, checkItems);

                check.addDiscount(checkDiscounts[3]);
                checks.add(check);
            }
        } catch (Exception ignored) {
        }
        return checks;
    }

    public static Collection<CheckDiscount> checkDiscounts() {
        try {
            if (checkDiscounts == null) {
                checkDiscounts = new NormalinoList<>();
                checkDiscounts.add(
                        new SimplePercentageOffCheckDiscount(1, "-30% на сумму чека", 30));
                checkDiscounts.add(
                        new SimplePercentageOffCheckDiscount(2, "-20% на сумму чека", 20));
                checkDiscounts.add(
                        new SimplePercentageOffCheckDiscount(3, "-35% на общую сумму", 35));


                checkDiscounts.add(
                        new SimpleConstantCheckDiscount(4, "-5$ на сумму чека", BigDecimal.valueOf(5)));
                checkDiscounts.add(
                        new SimpleConstantCheckDiscount(5, "-10$ на сумму чека", BigDecimal.valueOf(10)));

                // (сумма - 35%) - 10
                checkDiscounts.add(new SimpleConstantCheckDiscount(6, "-10$", BigDecimal.valueOf(10),
                        new SimplePercentageOffCheckDiscount(7, "-35%", 35)));

                // (сумма - 10) - 35%
                checkDiscounts.add(new SimplePercentageOffCheckDiscount(8, "-35%", 35,
                        new SimpleConstantCheckDiscount(9, "-10$", BigDecimal.valueOf(10))));

                // (сумма - 15%) - 10) - 35%
                checkDiscounts.add(new SimplePercentageOffCheckDiscount(10, "-35%", 35,
                        new SimpleConstantCheckDiscount(11, "-10$", BigDecimal.valueOf(10),
                                new SimplePercentageOffCheckDiscount(12, "-15%", 15))));
            }
        } catch (Exception ignored) {
        }
        return checkDiscounts;
    }

    public static Collection<CheckItemDiscount> checkItemDiscounts() {
        try {
            if (checkItemDiscounts == null) {
                checkItemDiscounts = new NormalinoList<>();
                checkItemDiscounts.add(
                        new SimpleConstantCheckItemDiscount(1, "-5$", BigDecimal.valueOf(5)));
                checkItemDiscounts.add(
                        new SimplePercentageOffCheckItemDiscount(2, "-30%", 30));
                checkItemDiscounts.add(
                        new SimplePercentageOffCheckItemDiscount(3, "-40%", 40));
                checkItemDiscounts.add(
                        new SimplePercentageOffCheckItemDiscount(4,
                                "-50%", 50));
                checkItemDiscounts.add(
                        new QuantityThresholdPercentOffCheckItemDiscount(5,
                                "-10% если количество продукта больше чем 5", 10, 5));
                checkItemDiscounts.add(new QuantityThresholdPercentOffCheckItemDiscount(6,
                        "Скидка 1% если количество продуктов больше 6", 1, 6,
                        new SimpleConstantCheckItemDiscount(7, "Скидка 10 на продукт", BigDecimal.valueOf(10))));
                checkItemDiscounts.add(new QuantityThresholdPercentOffCheckItemDiscount(8,
                        "Скидка 10% если количество продуктов больше 2", 10, 2));
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
