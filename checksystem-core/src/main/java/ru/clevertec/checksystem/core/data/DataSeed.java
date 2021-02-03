package ru.clevertec.checksystem.core.data;

import ru.clevertec.checksystem.core.entity.Product;
import ru.clevertec.checksystem.core.entity.check.Check;
import ru.clevertec.checksystem.core.entity.check.CheckItem;
import ru.clevertec.checksystem.core.entity.discount.check.CheckDiscount;
import ru.clevertec.checksystem.core.entity.discount.check.SimpleConstantCheckDiscount;
import ru.clevertec.checksystem.core.entity.discount.check.SimplePercentageCheckDiscount;
import ru.clevertec.checksystem.core.entity.discount.checkitem.CheckItemDiscount;
import ru.clevertec.checksystem.core.entity.discount.checkitem.SimpleConstantCheckItemDiscount;
import ru.clevertec.checksystem.core.entity.discount.checkitem.SimplePercentageCheckItemDiscount;
import ru.clevertec.checksystem.core.entity.discount.checkitem.ThresholdPercentageCheckItemDiscount;
import ru.clevertec.normalino.list.NormalinoList;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
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

            products.add(new Product.Builder()
                    .setId(1)
                    .setName("Пельмени")
                    .setPrice(BigDecimal.valueOf(12.48))
                    .build());

            products.add(new Product.Builder()
                    .setId(2)
                    .setName("Картошка")
                    .setPrice(BigDecimal.valueOf(7.5))
                    .build());

            products.add(new Product.Builder()
                    .setId(3)
                    .setName("Помидоры")
                    .setPrice(BigDecimal.valueOf(3.91))
                    .build());

            products.add(new Product.Builder()
                    .setId(4)
                    .setName("Сало")
                    .setPrice(BigDecimal.valueOf(10))
                    .build());

            products.add(new Product.Builder()
                    .setId(5)
                    .setName("Кукуруза")
                    .setPrice(BigDecimal.valueOf(20))
                    .build());

            products.add(new Product.Builder()
                    .setId(6)
                    .setName("Макароны")
                    .setPrice(BigDecimal.valueOf(8.5))
                    .build());

            products.add(new Product.Builder()
                    .setId(7)
                    .setName("Колбаса")
                    .setPrice(BigDecimal.valueOf(20))
                    .build());

            products.add(new Product.Builder()
                    .setId(8)
                    .setName("Рис")
                    .setPrice(BigDecimal.valueOf(19.999))
                    .build());

            products.add(new Product.Builder()
                    .setId(9)
                    .setName("Рыба")
                    .setPrice(BigDecimal.valueOf(50.2))
                    .build());

            products.add(new Product.Builder()
                    .setId(10)
                    .setName("Апельсины")
                    .setPrice(BigDecimal.valueOf(30.5))
                    .build());

            products.add(new Product.Builder()
                    .setId(11)
                    .setName("Яблоки")
                    .setPrice(BigDecimal.valueOf(8.7))
                    .build());

            products.add(new Product.Builder()
                    .setId(12)
                    .setName("Чай")
                    .setPrice(BigDecimal.valueOf(3.51))
                    .build());

            products.add(new Product.Builder()
                    .setId(13)
                    .setName("Кофе")
                    .setPrice(BigDecimal.valueOf(3.5))
                    .build());

            products.add(new Product.Builder()
                    .setId(14)
                    .setName("Печенье")
                    .setPrice(BigDecimal.valueOf(2.1))
                    .build());

            products.add(new Product.Builder()
                    .setId(15)
                    .setName("Конфеты")
                    .setPrice(BigDecimal.valueOf(5.6))
                    .build());

            products.add(new Product.Builder()
                    .setId(16)
                    .setName("Монитор")
                    .setPrice(BigDecimal.valueOf(155.5))
                    .build());

            products.add(new Product.Builder()
                    .setId(17)
                    .setName("Клавиатура")
                    .setPrice(BigDecimal.valueOf(30))
                    .build());

            products.add(new Product.Builder()
                    .setId(18)
                    .setName("Мышка")
                    .setPrice(BigDecimal.valueOf(30))
                    .build());

            products.add(new Product.Builder()
                    .setId(19)
                    .setName("SSD")
                    .setPrice(BigDecimal.valueOf(50))
                    .build());

            products.add(new Product.Builder()
                    .setId(20)
                    .setName("HDD")
                    .setPrice(BigDecimal.valueOf(25.5))
                    .build());
        }

        return products;
    }

    public static Collection<Check> checks() {

        var checkDiscounts = checkDiscounts().toArray(CheckDiscount[]::new);
        var checkItemDiscounts = checkItemDiscounts().toArray(CheckItemDiscount[]::new);

        if (checks == null) {
            checks = new NormalinoList<>();

            var products = products().toArray(Product[]::new);

            // Check 1
            var check = new Check.Builder()
                    .setId(1)
                    .setName("999 проблем")
                    .setDescription("Компьютерный магазин")
                    .setAddress("ул. Пушкина, д. Калатушкина")
                    .setPhoneNumber("+375290000000")
                    .setCashier("Василий Пупкин")
                    .setDate(new Date())
                    .build();

            var checkItems = new NormalinoList<CheckItem>();

            checkItems.add(new CheckItem.Builder()
                    .setId(221)
                    .setProduct(products[0])
                    .setQuantity(3)
                    .build());

            checkItems.add(new CheckItem.Builder()
                    .setId(222)
                    .setProduct(products[1])
                    .setQuantity(1)
                    .build());

            checkItems.add(new CheckItem.Builder()
                    .setId(1)
                    .setProduct(products[2])
                    .setQuantity(8)
                    .setDiscounts(checkItemDiscounts[4])
                    .build());

            checkItems.add(new CheckItem.Builder()
                    .setId(2)
                    .setProduct(products[3])
                    .setQuantity(9)
                    .build());

            checkItems.add(new CheckItem.Builder()
                    .setId(3)
                    .setProduct(products[4])
                    .setQuantity(1)
                    .build());

            checkItems.add(new CheckItem.Builder()
                    .setId(4)
                    .setProduct(products[5])
                    .setQuantity(2)
                    .build());

            addItemsToCheck(check, checkItems);

            check.putDiscount(checkDiscounts[2]);
            checks.add(check);

            // Check 2
            check = new Check.Builder()
                    .setId(2)
                    .setName("342 элемент")
                    .setDescription("Гипермаркет")
                    .setAddress("ул. Элементова, д. 1")
                    .setPhoneNumber("+375290000001")
                    .setCashier("Екатерина Пупкина")
                    .setDate(new Date())
                    .build();

            checkItems = new NormalinoList<>();

            checkItems.add(new CheckItem.Builder()
                    .setId(500)
                    .setProduct(products[2])
                    .setQuantity(8)
                    .setDiscounts(checkItemDiscounts[5])
                    .build());

            checkItems.add(new CheckItem.Builder()
                    .setId(501)
                    .setProduct(products[1])
                    .setQuantity(10)
                    .build());

            checkItems.add(new CheckItem.Builder()
                    .setId(502)
                    .setProduct(products[3])
                    .setQuantity(9)
                    .build());

            checkItems.add(new CheckItem.Builder()
                    .setId(503)
                    .setProduct(products[2])
                    .setQuantity(8)
                    .build());

            checkItems.add(new CheckItem.Builder()
                    .setId(503)
                    .setProduct(products[2])
                    .setQuantity(8)
                    .setDiscounts(checkItemDiscounts[1])
                    .build());

            checkItems.add(new CheckItem.Builder()
                    .setId(44)
                    .setProduct(products[5])
                    .setQuantity(7)
                    .setDiscounts(checkItemDiscounts[2])
                    .build());

            checkItems.add(new CheckItem.Builder()
                    .setId(45)
                    .setProduct(products[7])
                    .setQuantity(6)
                    .build());

            checkItems.add(new CheckItem.Builder()
                    .setId(46)
                    .setProduct(products[9])
                    .setQuantity(5)
                    .build());

            addItemsToCheck(check, checkItems);

            check.putDiscount(checkDiscounts[0]);
            checks.add(check);

            // Check 3
            check = new Check.Builder().setId(3)
                    .setName("Магазин №1")
                    .setDescription("Продуктовый магазин")
                    .setAddress("ул. Советская, д. 1001")
                    .setPhoneNumber("+375290000002")
                    .setCashier("Алексей Пупкин")
                    .setDate(new Date())
                    .build();

            checkItems = new NormalinoList<>();

            checkItems.add(new CheckItem.Builder()
                    .setId(125)
                    .setProduct(products[2])
                    .setQuantity(15)
                    .build());

            checkItems.add(new CheckItem.Builder()
                    .setId(126)
                    .setProduct(products[4])
                    .setQuantity(1)
                    .build());

            checkItems.add(new CheckItem.Builder()
                    .setId(127)
                    .setProduct(products[6])
                    .setQuantity(1)
                    .build());

            checkItems.add(new CheckItem.Builder()
                    .setId(128)
                    .setProduct(products[8])
                    .setQuantity(3)
                    .build());

            checkItems.add(new CheckItem.Builder()
                    .setId(129)
                    .setProduct(products[10])
                    .setQuantity(11)
                    .build());

            checkItems.add(new CheckItem.Builder()
                    .setId(130)
                    .setProduct(products[12])
                    .setQuantity(6)
                    .build());

            addItemsToCheck(check, checkItems);

            check.putDiscount(checkDiscounts[1]);
            checks.add(check);

            // Check 4
            check = new Check.Builder()
                    .setId(4)
                    .setName("Магазин №2")
                    .setDescription("Продуктовый магазин")
                    .setAddress("ул. Свиридова, д. 1234")
                    .setPhoneNumber("+375290000003")
                    .setCashier("Татьяна Пупкина")
                    .setDate(new Date())
                    .build();

            checkItems = new NormalinoList<>();

            checkItems.add(new CheckItem.Builder()
                    .setId(600)
                    .setProduct(products[3])
                    .setQuantity(15)
                    .setDiscounts(checkItemDiscounts[6])
                    .build());

            checkItems.add(new CheckItem.Builder()
                    .setId(131)
                    .setProduct(products[5])
                    .setQuantity(1)
                    .build());

            checkItems.add(new CheckItem.Builder()
                    .setId(132)
                    .setProduct(products[7])
                    .setQuantity(1)
                    .build());

            checkItems.add(new CheckItem.Builder()
                    .setId(133)
                    .setProduct(products[9])
                    .setQuantity(3)
                    .build());

            checkItems.add(new CheckItem.Builder()
                    .setId(134)
                    .setProduct(products[11])
                    .setQuantity(15)
                    .setDiscounts(checkItemDiscounts[0])
                    .build());

            checkItems.add(new CheckItem.Builder()
                    .setId(134)
                    .setProduct(products[13])
                    .setQuantity(6)
                    .build());

            checkItems.add(new CheckItem.Builder()
                    .setId(135)
                    .setProduct(products[15])
                    .setQuantity(3)
                    .build());

            checkItems.add(new CheckItem.Builder()
                    .setId(136)
                    .setProduct(products[17])
                    .setQuantity(11)
                    .build());

            checkItems.add(new CheckItem.Builder()
                    .setId(138)
                    .setProduct(products[19])
                    .setQuantity(6)
                    .build());

            addItemsToCheck(check, checkItems);

            check.putDiscount(checkDiscounts[3]);
            checks.add(check);
        }

        return checks;
    }

    public static Collection<CheckDiscount> checkDiscounts() {

        if (checkDiscounts == null) {
            checkDiscounts = new NormalinoList<>();

            checkDiscounts.add(new SimplePercentageCheckDiscount.Builder()
                    .setId(1)
                    .setPercent(30)
                    .setDescription("-30% на сумму чека")
                    .build());

            checkDiscounts.add(new SimplePercentageCheckDiscount.Builder()
                    .setId(2)
                    .setPercent(20)
                    .setDescription("-20% на сумму чека")
                    .build());

            checkDiscounts.add(new SimplePercentageCheckDiscount.Builder()
                    .setId(3)
                    .setPercent(35)
                    .setDescription("-35% на сумму чека")
                    .build());

            checkDiscounts.add(new SimpleConstantCheckDiscount.Builder()
                    .setId(4)
                    .setConstant(BigDecimal.valueOf(5))
                    .setDescription("-5$ на сумму чека").build());

            checkDiscounts.add(new SimpleConstantCheckDiscount.Builder()
                    .setId(5)
                    .setConstant(BigDecimal.valueOf(10))
                    .setDescription("-10$ на сумму чека").build());

            // (сумма - 35%) - 10
            checkDiscounts.add(new SimpleConstantCheckDiscount.Builder()
                    .setId(6)
                    .setConstant(BigDecimal.valueOf(10))
                    .setDependentDiscount(new SimplePercentageCheckDiscount.Builder()
                            .setId(7)
                            .setPercent(35)
                            .setDescription("-35%")
                            .build())
                    .setDescription("-10$").build());

            // (сумма - 10) - 35%
            checkDiscounts.add(new SimplePercentageCheckDiscount.Builder()
                    .setId(8)
                    .setPercent(35)
                    .setDependentDiscount(new SimpleConstantCheckDiscount.Builder()
                            .setId(9)
                            .setConstant(BigDecimal.valueOf(10))
                            .setDescription("-10$")
                            .build())
                    .setDescription("-35%").build());

            // (сумма - 15%) - 10) - 35%
            checkDiscounts.add(new SimplePercentageCheckDiscount.Builder()
                    .setId(10)
                    .setPercent(35)
                    .setDependentDiscount(new SimpleConstantCheckDiscount.Builder()
                            .setId(11)
                            .setConstant(BigDecimal.valueOf(10))
                            .setDependentDiscount(new SimplePercentageCheckDiscount.Builder()
                                    .setId(12)
                                    .setPercent(15)
                                    .setDescription("-15%")
                                    .build())
                            .setDescription("-10$")
                            .build())
                    .setDescription("-35%").build());
        }

        return checkDiscounts;
    }

    public static Collection<CheckItemDiscount> checkItemDiscounts() {

        if (checkItemDiscounts == null) {
            checkItemDiscounts = new NormalinoList<>();

            checkItemDiscounts.add(new SimpleConstantCheckItemDiscount.Builder()
                    .setId(1)
                    .setConstant(BigDecimal.valueOf(5))
                    .setDescription("-5%")
                    .build());

            checkItemDiscounts.add(new SimplePercentageCheckItemDiscount.BuilderSimple()
                    .setId(2)
                    .setPercent(30)
                    .setDescription("-30%")
                    .build());

            checkItemDiscounts.add(new SimplePercentageCheckItemDiscount.BuilderSimple()
                    .setId(3)
                    .setPercent(40)
                    .setDescription("-40%")
                    .build());

            checkItemDiscounts.add(new SimplePercentageCheckItemDiscount.BuilderSimple()
                    .setId(4)
                    .setPercent(50)
                    .setDescription("-50%")
                    .build());

            checkItemDiscounts.add(
                    new ThresholdPercentageCheckItemDiscount.Builder()
                            .setId(5)
                            .setPercent(10)
                            .setThreshold(5)
                            .setDescription("-10% если количество продукта больше чем 5")
                            .build());

            checkItemDiscounts.add(new ThresholdPercentageCheckItemDiscount.Builder()
                    .setId(6)
                    .setPercent(1)
                    .setThreshold(6)
                    .setDependentDiscount(new SimpleConstantCheckItemDiscount.Builder()
                            .setId(7)
                            .setConstant(BigDecimal.valueOf(10))
                            .setDescription("Скидка 10 на продукт")
                            .build())
                    .setDescription("Скидка 1% если количество продуктов больше 6")
                    .build());

            checkItemDiscounts.add(new ThresholdPercentageCheckItemDiscount.Builder()
                    .setId(8)
                    .setPercent(10)
                    .setThreshold(2)
                    .setDescription("Скидка 10% если количество продуктов больше 2")
                    .build());
        }

        return checkItemDiscounts;
    }

    public static InternetAddress[] emailAddresses() {

        var addresses = new InternetAddress[0];
        try {
            addresses = new InternetAddress[]{
                    new InternetAddress("lakadmakatag@gmail.com")
            };
        } catch (AddressException e) {
            e.printStackTrace();
        }

        return addresses;
    }

    private static void addItemsToCheck(Check check, List<CheckItem> items) {
        for (int i = 0; i < items.size(); i++) {
            items.get(i).setId(i + 1);
            check.putCheckItem(items.get(i));
        }
    }
}
