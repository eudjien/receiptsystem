package ru.clevertec.checksystem.core.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.clevertec.checksystem.core.entity.Email;
import ru.clevertec.checksystem.core.entity.EventEmail;
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
import ru.clevertec.checksystem.core.event.EventType;
import ru.clevertec.checksystem.core.repository.*;
import ru.clevertec.custom.list.SinglyLinkedList;

import javax.mail.internet.AddressException;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

@Component
public final class DataSeed {

    private static Collection<Product> products;
    private static Collection<Check> checks;
    private static Collection<CheckDiscount> checkDiscounts;
    private static Collection<CheckItemDiscount> checkItemDiscounts;
    private static Collection<Email> emails;
    private static Collection<EventEmail> eventEmails;

    private final CheckRepository checkRepository;
    private final ProductRepository productRepository;
    private final CheckDiscountRepository checkDiscountRepository;
    private final CheckItemDiscountRepository checkItemDiscountRepository;
    private final EmailRepository emailRepository;
    private final EventEmailRepository eventEmailRepository;

    @Autowired
    public DataSeed(CheckRepository checkRepository,
                    ProductRepository productRepository,
                    CheckDiscountRepository checkDiscountRepository,
                    CheckItemDiscountRepository checkItemDiscountRepository,
                    EmailRepository emailRepository,
                    EventEmailRepository eventEmailRepository) {
        this.checkRepository = checkRepository;
        this.productRepository = productRepository;
        this.checkDiscountRepository = checkDiscountRepository;
        this.checkItemDiscountRepository = checkItemDiscountRepository;
        this.emailRepository = emailRepository;
        this.eventEmailRepository = eventEmailRepository;
    }

    public void dbSeed() {
        checkRepository.saveAll(checks());
        productRepository.saveAll(products());
        checkDiscountRepository.saveAll(checkDiscounts());
        checkItemDiscountRepository.saveAll(checkItemDiscounts());
        emailRepository.saveAll(emails());
        eventEmailRepository.saveAll(eventEmails());
    }

    public static Collection<Product> products() {
        if (products == null) {
            products = new SinglyLinkedList<>();

            products.add(new Product.Builder()
                    .setName("Пельмени")
                    .setPrice(BigDecimal.valueOf(12.48))
                    .build());

            products.add(new Product.Builder()
                    .setName("Картошка")
                    .setPrice(BigDecimal.valueOf(7.5))
                    .build());

            products.add(new Product.Builder()
                    .setName("Помидоры")
                    .setPrice(BigDecimal.valueOf(3.91))
                    .build());

            products.add(new Product.Builder()
                    .setName("Сало")
                    .setPrice(BigDecimal.valueOf(10))
                    .build());

            products.add(new Product.Builder()
                    .setName("Кукуруза")
                    .setPrice(BigDecimal.valueOf(20))
                    .build());

            products.add(new Product.Builder()
                    .setName("Макароны")
                    .setPrice(BigDecimal.valueOf(8.5))
                    .build());

            products.add(new Product.Builder()
                    .setName("Колбаса")
                    .setPrice(BigDecimal.valueOf(20))
                    .build());

            products.add(new Product.Builder()
                    .setName("Рис")
                    .setPrice(BigDecimal.valueOf(19.999))
                    .build());

            products.add(new Product.Builder()
                    .setName("Рыба")
                    .setPrice(BigDecimal.valueOf(50.2))
                    .build());

            products.add(new Product.Builder()
                    .setName("Апельсины")
                    .setPrice(BigDecimal.valueOf(30.5))
                    .build());

            products.add(new Product.Builder()
                    .setName("Яблоки")
                    .setPrice(BigDecimal.valueOf(8.7))
                    .build());

            products.add(new Product.Builder()
                    .setName("Чай")
                    .setPrice(BigDecimal.valueOf(3.51))
                    .build());

            products.add(new Product.Builder()
                    .setName("Кофе")
                    .setPrice(BigDecimal.valueOf(3.5))
                    .build());

            products.add(new Product.Builder()
                    .setName("Печенье")
                    .setPrice(BigDecimal.valueOf(2.1))
                    .build());

            products.add(new Product.Builder()
                    .setName("Конфеты")
                    .setPrice(BigDecimal.valueOf(5.6))
                    .build());

            products.add(new Product.Builder()
                    .setName("Монитор")
                    .setPrice(BigDecimal.valueOf(155.5))
                    .build());

            products.add(new Product.Builder()
                    .setName("Клавиатура")
                    .setPrice(BigDecimal.valueOf(30))
                    .build());

            products.add(new Product.Builder()
                    .setName("Мышка")
                    .setPrice(BigDecimal.valueOf(30))
                    .build());

            products.add(new Product.Builder()
                    .setName("SSD")
                    .setPrice(BigDecimal.valueOf(50))
                    .build());

            products.add(new Product.Builder()
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
            checks = new SinglyLinkedList<>();

            var products = products().toArray(Product[]::new);

            // Check 1
            var check = new Check.Builder()

                    .setName("999 проблем")
                    .setDescription("Компьютерный магазин")
                    .setAddress("ул. Пушкина, д. Калатушкина")
                    .setPhoneNumber("+375290000000")
                    .setCashier("Василий Пупкин")
                    .setDate(new Date())
                    .build();

            var checkItems = new SinglyLinkedList<CheckItem>();

            checkItems.add(new CheckItem.Builder()
                    .setProduct(products[0])
                    .setQuantity(3)
                    .build());

            checkItems.add(new CheckItem.Builder()
                    .setProduct(products[1])
                    .setQuantity(1)
                    .build());

            checkItems.add(new CheckItem.Builder()
                    .setProduct(products[2])
                    .setQuantity(8)
                    .addDiscount(checkItemDiscounts[4])
                    .build());

            checkItems.add(new CheckItem.Builder()
                    .setProduct(products[3])
                    .setQuantity(9)
                    .build());

            checkItems.add(new CheckItem.Builder()
                    .setProduct(products[4])
                    .setQuantity(1)
                    .build());

            checkItems.add(new CheckItem.Builder()
                    .setProduct(products[5])
                    .setQuantity(2)
                    .build());

            check.addCheckItems(checkItems);

            check.addDiscount(checkDiscounts[2]);
            checks.add(check);

            // Check 2
            check = new Check.Builder()
                    .setName("342 элемент")
                    .setDescription("Гипермаркет")
                    .setAddress("ул. Элементова, д. 1")
                    .setPhoneNumber("+375290000001")
                    .setCashier("Екатерина Пупкина")
                    .setDate(new Date())
                    .build();

            checkItems = new SinglyLinkedList<>();

            checkItems.add(new CheckItem.Builder()

                    .setProduct(products[2])
                    .setQuantity(8)
                    .addDiscount(checkItemDiscounts[5])
                    .build());

            checkItems.add(new CheckItem.Builder()
                    .setProduct(products[1])
                    .setQuantity(10)
                    .build());

            checkItems.add(new CheckItem.Builder()
                    .setProduct(products[3])
                    .setQuantity(9)
                    .build());

            checkItems.add(new CheckItem.Builder()
                    .setProduct(products[10])
                    .setQuantity(8)
                    .build());

            checkItems.add(new CheckItem.Builder()
                    .setProduct(products[11])
                    .setQuantity(8)
                    .addDiscount(checkItemDiscounts[1])
                    .build());

            checkItems.add(new CheckItem.Builder()
                    .setProduct(products[5])
                    .setQuantity(7)
                    .addDiscount(checkItemDiscounts[2])
                    .build());

            checkItems.add(new CheckItem.Builder()
                    .setProduct(products[7])
                    .setQuantity(6)
                    .build());

            checkItems.add(new CheckItem.Builder()
                    .setProduct(products[9])
                    .setQuantity(5)
                    .build());

            check.addCheckItems(checkItems);
            check.addDiscount(checkDiscounts[0]);
            checks.add(check);

            // Check 3
            check = new Check.Builder()
                    .setName("Магазин №1")
                    .setDescription("Продуктовый магазин")
                    .setAddress("ул. Советская, д. 1001")
                    .setPhoneNumber("+375290000002")
                    .setCashier("Алексей Пупкин")
                    .setDate(new Date())
                    .build();

            checkItems = new SinglyLinkedList<>();

            checkItems.add(new CheckItem.Builder()
                    .setProduct(products[2])
                    .setQuantity(15)
                    .build());

            checkItems.add(new CheckItem.Builder()
                    .setProduct(products[4])
                    .setQuantity(1)
                    .build());

            checkItems.add(new CheckItem.Builder()
                    .setProduct(products[6])
                    .setQuantity(1)
                    .build());

            checkItems.add(new CheckItem.Builder()
                    .setProduct(products[8])
                    .setQuantity(3)
                    .build());

            checkItems.add(new CheckItem.Builder()
                    .setProduct(products[10])
                    .setQuantity(11)
                    .build());

            checkItems.add(new CheckItem.Builder()
                    .setProduct(products[12])
                    .setQuantity(6)
                    .build());

            check.addCheckItems(checkItems);
            check.addDiscount(checkDiscounts[1]);
            checks.add(check);

            // Check 4
            check = new Check.Builder()
                    .setName("Магазин №2")
                    .setDescription("Продуктовый магазин")
                    .setAddress("ул. Свиридова, д. 1234")
                    .setPhoneNumber("+375290000003")
                    .setCashier("Татьяна Пупкина")
                    .setDate(new Date())
                    .build();

            checkItems = new SinglyLinkedList<>();

            checkItems.add(new CheckItem.Builder()
                    .setProduct(products[3])
                    .setQuantity(15)
                    .addDiscount(checkItemDiscounts[6])
                    .build());

            checkItems.add(new CheckItem.Builder()
                    .setProduct(products[5])
                    .setQuantity(1)
                    .build());

            checkItems.add(new CheckItem.Builder()
                    .setProduct(products[7])
                    .setQuantity(1)
                    .build());

            checkItems.add(new CheckItem.Builder()
                    .setProduct(products[9])
                    .setQuantity(3)
                    .build());

            checkItems.add(new CheckItem.Builder()
                    .setProduct(products[11])
                    .setQuantity(15)
                    .addDiscount(checkItemDiscounts[0])
                    .build());

            checkItems.add(new CheckItem.Builder()
                    .setProduct(products[13])
                    .setQuantity(6)
                    .build());

            checkItems.add(new CheckItem.Builder()
                    .setProduct(products[15])
                    .setQuantity(3)
                    .build());

            checkItems.add(new CheckItem.Builder()
                    .setProduct(products[17])
                    .setQuantity(11)
                    .build());

            checkItems.add(new CheckItem.Builder()
                    .setProduct(products[19])
                    .setQuantity(6)
                    .build());

            check.addCheckItems(checkItems);
            check.addDiscount(checkDiscounts[3]);
            checks.add(check);
        }

        return checks;
    }

    public static Collection<CheckDiscount> checkDiscounts() {

        if (checkDiscounts == null) {
            checkDiscounts = new SinglyLinkedList<>();

            checkDiscounts.add(new SimplePercentageCheckDiscount.Builder()
                    .setPercent(30D)
                    .setDescription("-30% на сумму чека")
                    .build());

            checkDiscounts.add(new SimplePercentageCheckDiscount.Builder()
                    .setPercent(20D)
                    .setDescription("-20% на сумму чека")
                    .build());

            checkDiscounts.add(new SimplePercentageCheckDiscount.Builder()
                    .setPercent(35D)
                    .setDescription("-35% на сумму чека")
                    .build());

            checkDiscounts.add(new SimpleConstantCheckDiscount.Builder()
                    .setConstant(BigDecimal.valueOf(5))
                    .setDescription("-5$ на сумму чека").build());

            checkDiscounts.add(new SimpleConstantCheckDiscount.Builder()
                    .setConstant(BigDecimal.valueOf(10))
                    .setDescription("-10$ на сумму чека").build());

            // (сумма - 35%) - 10
            checkDiscounts.add(new SimpleConstantCheckDiscount.Builder()
                    .setConstant(BigDecimal.valueOf(10))
                    .setDependentDiscount(new SimplePercentageCheckDiscount.Builder()
                            .setPercent(35D)
                            .setDescription("-35%")
                            .build())
                    .setDescription("-10$")
                    .build());

            // (сумма - 10) - 35%
            checkDiscounts.add(new SimplePercentageCheckDiscount.Builder()
                    .setPercent(35D)
                    .setDependentDiscount(new SimpleConstantCheckDiscount.Builder()
                            .setConstant(BigDecimal.valueOf(10))
                            .setDescription("-10$")
                            .build())
                    .setDescription("-35%")
                    .build());

            // (сумма - 15%) - 10) - 35%
            checkDiscounts.add(new SimplePercentageCheckDiscount.Builder()
                    .setPercent(35D)
                    .setDependentDiscount(new SimpleConstantCheckDiscount.Builder()
                            .setConstant(BigDecimal.valueOf(10))
                            .setDependentDiscount(new SimplePercentageCheckDiscount.Builder()
                                    .setPercent(15D)
                                    .setDescription("-15%")
                                    .build())
                            .setDescription("-10$")
                            .build())
                    .setDescription("-35%")
                    .build());
        }

        return checkDiscounts;
    }

    public static Collection<CheckItemDiscount> checkItemDiscounts() {

        if (checkItemDiscounts == null) {
            checkItemDiscounts = new SinglyLinkedList<>();

            checkItemDiscounts.add(new SimpleConstantCheckItemDiscount.Builder()
                    .setConstant(BigDecimal.valueOf(5))
                    .setDescription("-5$")
                    .build());

            checkItemDiscounts.add(new SimplePercentageCheckItemDiscount.BuilderSimple()
                    .setPercent(30D)
                    .setDescription("-30%")
                    .build());

            checkItemDiscounts.add(new SimplePercentageCheckItemDiscount.BuilderSimple()
                    .setPercent(40D)
                    .setDescription("-40%")
                    .build());

            checkItemDiscounts.add(new SimplePercentageCheckItemDiscount.BuilderSimple()
                    .setPercent(50D)
                    .setDescription("-50%")
                    .build());

            checkItemDiscounts.add(
                    new ThresholdPercentageCheckItemDiscount.Builder()
                            .setPercent(10D)
                            .setThreshold(5L)
                            .setDescription("-10% если количество продукта больше чем 5")
                            .build());

            checkItemDiscounts.add(new ThresholdPercentageCheckItemDiscount.Builder()
                    .setPercent(1D)
                    .setThreshold(6L)
                    .setDependentDiscount(new SimpleConstantCheckItemDiscount.Builder()
                            .setConstant(BigDecimal.valueOf(10))
                            .setDescription("Скидка 10 на продукт")
                            .build())
                    .setDescription("Скидка 1% если количество продуктов больше 6")
                    .build());

            checkItemDiscounts.add(new ThresholdPercentageCheckItemDiscount.Builder()
                    .setPercent(10D)
                    .setThreshold(2L)
                    .setDescription("Скидка 10% если количество продуктов больше 2")
                    .build());
        }

        return checkItemDiscounts;
    }

    public static Collection<Email> emails() {
        if (emails == null) {
            try {
                emails = new SinglyLinkedList<>();
                emails.add(new Email("lakadmakatag@gmail.com"));
                emails.add(new Email("chcksstm1@gmail.com"));

            } catch (AddressException e) {
                throw new RuntimeException(e);
            }
        }
        return emails;
    }

    public static Collection<EventEmail> eventEmails() {
        if (eventEmails == null) {
            eventEmails = new SinglyLinkedList<>();
            emails().forEach(email -> eventEmails.add(new EventEmail(email, EventType.PrintEnd)));
        }
        return eventEmails;
    }
}
