package ru.clevertec.checksystem.core.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.clevertec.checksystem.core.entity.Email;
import ru.clevertec.checksystem.core.entity.EventEmail;
import ru.clevertec.checksystem.core.entity.Product;
import ru.clevertec.checksystem.core.entity.discount.receipt.ReceiptDiscount;
import ru.clevertec.checksystem.core.entity.discount.receipt.SimpleConstantReceiptDiscount;
import ru.clevertec.checksystem.core.entity.discount.receipt.SimplePercentageReceiptDiscount;
import ru.clevertec.checksystem.core.entity.discount.receiptitem.ReceiptItemDiscount;
import ru.clevertec.checksystem.core.entity.discount.receiptitem.SimpleConstantReceiptItemDiscount;
import ru.clevertec.checksystem.core.entity.discount.receiptitem.SimplePercentageReceiptItemDiscount;
import ru.clevertec.checksystem.core.entity.discount.receiptitem.ThresholdPercentageReceiptItemDiscount;
import ru.clevertec.checksystem.core.entity.receipt.Receipt;
import ru.clevertec.checksystem.core.entity.receipt.ReceiptItem;
import ru.clevertec.checksystem.core.event.EventType;
import ru.clevertec.checksystem.core.repository.*;

import javax.mail.internet.AddressException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@Component
public final class DataSeed {

    private static Collection<Product> products;
    private static Collection<Receipt> receipts;
    private static Collection<ReceiptDiscount> receiptDiscounts;
    private static Collection<ReceiptItemDiscount> receiptItemDiscounts;
    private static Collection<Email> emails;
    private static Collection<EventEmail> eventEmails;

    private final ReceiptRepository receiptRepository;
    private final ProductRepository productRepository;
    private final ReceiptDiscountRepository receiptDiscountRepository;
    private final ReceiptItemDiscountRepository receiptItemDiscountRepository;
    private final EmailRepository emailRepository;
    private final EventEmailRepository eventEmailRepository;

    @Autowired
    public DataSeed(ReceiptRepository receiptRepository,
                    ProductRepository productRepository,
                    ReceiptDiscountRepository receiptDiscountRepository,
                    ReceiptItemDiscountRepository receiptItemDiscountRepository,
                    EmailRepository emailRepository,
                    EventEmailRepository eventEmailRepository) {
        this.receiptRepository = receiptRepository;
        this.productRepository = productRepository;
        this.receiptDiscountRepository = receiptDiscountRepository;
        this.receiptItemDiscountRepository = receiptItemDiscountRepository;
        this.emailRepository = emailRepository;
        this.eventEmailRepository = eventEmailRepository;
    }

    public void dbSeed() {
        receiptRepository.saveAll(checks());
        productRepository.saveAll(products());
        receiptDiscountRepository.saveAll(receiptDiscounts());
        receiptItemDiscountRepository.saveAll(receiptItemDiscounts());
        emailRepository.saveAll(emails());
        eventEmailRepository.saveAll(eventEmails());
    }

    public static Collection<Product> products() {
        if (products == null) {
            products = new ArrayList<>();

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

    public static Collection<Receipt> checks() {

        var receiptDiscounts = receiptDiscounts().toArray(ReceiptDiscount[]::new);
        var receiptItemDiscounts = receiptItemDiscounts().toArray(ReceiptItemDiscount[]::new);

        if (receipts == null) {
            receipts = new ArrayList<>();

            var products = products().toArray(Product[]::new);

            // Receipt 1
            var receipt = new Receipt.Builder()

                    .setName("999 проблем")
                    .setDescription("Компьютерный магазин")
                    .setAddress("ул. Пушкина, д. Калатушкина")
                    .setPhoneNumber("+375290000000")
                    .setCashier("Василий Пупкин")
                    .setDate(new Date())
                    .build();

            var receiptItems = new ArrayList<ReceiptItem>();

            receiptItems.add(new ReceiptItem.Builder()
                    .setProduct(products[0])
                    .setQuantity(3L)
                    .build());

            receiptItems.add(new ReceiptItem.Builder()
                    .setProduct(products[1])
                    .setQuantity(1L)
                    .build());

            receiptItems.add(new ReceiptItem.Builder()
                    .setProduct(products[2])
                    .setQuantity(8L)
                    .addDiscount(receiptItemDiscounts[4])
                    .build());

            receiptItems.add(new ReceiptItem.Builder()
                    .setProduct(products[3])
                    .setQuantity(9L)
                    .build());

            receiptItems.add(new ReceiptItem.Builder()
                    .setProduct(products[4])
                    .setQuantity(1L)
                    .build());

            receiptItems.add(new ReceiptItem.Builder()
                    .setProduct(products[5])
                    .setQuantity(2L)
                    .build());

            receipt.addReceiptItems(receiptItems);

            receipt.addDiscount(receiptDiscounts[2]);
            receipts.add(receipt);

            // Receipt 2
            receipt = new Receipt.Builder()
                    .setName("342 элемент")
                    .setDescription("Гипермаркет")
                    .setAddress("ул. Элементова, д. 1")
                    .setPhoneNumber("+375290000001")
                    .setCashier("Екатерина Пупкина")
                    .setDate(new Date())
                    .build();

            receiptItems = new ArrayList<>();

            receiptItems.add(new ReceiptItem.Builder()

                    .setProduct(products[2])
                    .setQuantity(8L)
                    .addDiscount(receiptItemDiscounts[5])
                    .build());

            receiptItems.add(new ReceiptItem.Builder()
                    .setProduct(products[1])
                    .setQuantity(10L)
                    .build());

            receiptItems.add(new ReceiptItem.Builder()
                    .setProduct(products[3])
                    .setQuantity(9L)
                    .build());

            receiptItems.add(new ReceiptItem.Builder()
                    .setProduct(products[10])
                    .setQuantity(8L)
                    .build());

            receiptItems.add(new ReceiptItem.Builder()
                    .setProduct(products[11])
                    .setQuantity(8L)
                    .addDiscount(receiptItemDiscounts[1])
                    .build());

            receiptItems.add(new ReceiptItem.Builder()
                    .setProduct(products[5])
                    .setQuantity(7L)
                    .addDiscount(receiptItemDiscounts[2])
                    .build());

            receiptItems.add(new ReceiptItem.Builder()
                    .setProduct(products[7])
                    .setQuantity(6L)
                    .build());

            receiptItems.add(new ReceiptItem.Builder()
                    .setProduct(products[9])
                    .setQuantity(5L)
                    .build());

            receipt.addReceiptItems(receiptItems);
            receipt.addDiscount(receiptDiscounts[0]);
            receipts.add(receipt);

            // Receipt 3
            receipt = new Receipt.Builder()
                    .setName("Магазин №1")
                    .setDescription("Продуктовый магазин")
                    .setAddress("ул. Советская, д. 1001")
                    .setPhoneNumber("+375290000002")
                    .setCashier("Алексей Пупкин")
                    .setDate(new Date())
                    .build();

            receiptItems = new ArrayList<>();

            receiptItems.add(new ReceiptItem.Builder()
                    .setProduct(products[2])
                    .setQuantity(15L)
                    .build());

            receiptItems.add(new ReceiptItem.Builder()
                    .setProduct(products[4])
                    .setQuantity(1L)
                    .build());

            receiptItems.add(new ReceiptItem.Builder()
                    .setProduct(products[6])
                    .setQuantity(1L)
                    .build());

            receiptItems.add(new ReceiptItem.Builder()
                    .setProduct(products[8])
                    .setQuantity(3L)
                    .build());

            receiptItems.add(new ReceiptItem.Builder()
                    .setProduct(products[10])
                    .setQuantity(11L)
                    .build());

            receiptItems.add(new ReceiptItem.Builder()
                    .setProduct(products[12])
                    .setQuantity(6L)
                    .build());

            receipt.addReceiptItems(receiptItems);
            receipt.addDiscount(receiptDiscounts[1]);
            receipts.add(receipt);

            // Receipt 4
            receipt = new Receipt.Builder()
                    .setName("Магазин №2")
                    .setDescription("Продуктовый магазин")
                    .setAddress("ул. Свиридова, д. 1234")
                    .setPhoneNumber("+375290000003")
                    .setCashier("Татьяна Пупкина")
                    .setDate(new Date())
                    .build();

            receiptItems = new ArrayList<>();

            receiptItems.add(new ReceiptItem.Builder()
                    .setProduct(products[3])
                    .setQuantity(15L)
                    .addDiscount(receiptItemDiscounts[6])
                    .build());

            receiptItems.add(new ReceiptItem.Builder()
                    .setProduct(products[5])
                    .setQuantity(1L)
                    .build());

            receiptItems.add(new ReceiptItem.Builder()
                    .setProduct(products[7])
                    .setQuantity(1L)
                    .build());

            receiptItems.add(new ReceiptItem.Builder()
                    .setProduct(products[9])
                    .setQuantity(3L)
                    .build());

            receiptItems.add(new ReceiptItem.Builder()
                    .setProduct(products[11])
                    .setQuantity(15L)
                    .addDiscount(receiptItemDiscounts[0])
                    .build());

            receiptItems.add(new ReceiptItem.Builder()
                    .setProduct(products[13])
                    .setQuantity(6L)
                    .build());

            receiptItems.add(new ReceiptItem.Builder()
                    .setProduct(products[15])
                    .setQuantity(3L)
                    .build());

            receiptItems.add(new ReceiptItem.Builder()
                    .setProduct(products[17])
                    .setQuantity(11L)
                    .build());

            receiptItems.add(new ReceiptItem.Builder()
                    .setProduct(products[19])
                    .setQuantity(6L)
                    .build());

            receipt.addReceiptItems(receiptItems);
            receipt.addDiscount(receiptDiscounts[3]);
            receipts.add(receipt);
        }

        return receipts;
    }

    public static Collection<ReceiptDiscount> receiptDiscounts() {

        if (receiptDiscounts == null) {
            receiptDiscounts = new ArrayList<>();

            receiptDiscounts.add(new SimplePercentageReceiptDiscount.Builder()
                    .setPercent(30D)
                    .setDescription("-30% на сумму чека")
                    .build());

            receiptDiscounts.add(new SimplePercentageReceiptDiscount.Builder()
                    .setPercent(20D)
                    .setDescription("-20% на сумму чека")
                    .build());

            receiptDiscounts.add(new SimplePercentageReceiptDiscount.Builder()
                    .setPercent(35D)
                    .setDescription("-35% на сумму чека")
                    .build());

            receiptDiscounts.add(new SimpleConstantReceiptDiscount.Builder()
                    .setConstant(BigDecimal.valueOf(5))
                    .setDescription("-5$ на сумму чека").build());

            receiptDiscounts.add(new SimpleConstantReceiptDiscount.Builder()
                    .setConstant(BigDecimal.valueOf(10))
                    .setDescription("-10$ на сумму чека").build());

            // (сумма - 35%) - 10
            receiptDiscounts.add(new SimpleConstantReceiptDiscount.Builder()
                    .setConstant(BigDecimal.valueOf(10))
                    .setDependentDiscount(new SimplePercentageReceiptDiscount.Builder()
                            .setPercent(35D)
                            .setDescription("-35%")
                            .build())
                    .setDescription("-10$")
                    .build());

            // (сумма - 10) - 35%
            receiptDiscounts.add(new SimplePercentageReceiptDiscount.Builder()
                    .setPercent(35D)
                    .setDependentDiscount(new SimpleConstantReceiptDiscount.Builder()
                            .setConstant(BigDecimal.valueOf(10))
                            .setDescription("-10$")
                            .build())
                    .setDescription("-35%")
                    .build());

            // (сумма - 15%) - 10) - 35%
            receiptDiscounts.add(new SimplePercentageReceiptDiscount.Builder()
                    .setPercent(35D)
                    .setDependentDiscount(new SimpleConstantReceiptDiscount.Builder()
                            .setConstant(BigDecimal.valueOf(10))
                            .setDependentDiscount(new SimplePercentageReceiptDiscount.Builder()
                                    .setPercent(15D)
                                    .setDescription("-15%")
                                    .build())
                            .setDescription("-10$")
                            .build())
                    .setDescription("-35%")
                    .build());
        }

        return receiptDiscounts;
    }

    public static Collection<ReceiptItemDiscount> receiptItemDiscounts() {

        if (receiptItemDiscounts == null) {
            receiptItemDiscounts = new ArrayList<>();

            receiptItemDiscounts.add(new SimpleConstantReceiptItemDiscount.Builder()
                    .setConstant(BigDecimal.valueOf(5))
                    .setDescription("-5$")
                    .build());

            receiptItemDiscounts.add(new SimplePercentageReceiptItemDiscount.BuilderSimple()
                    .setPercent(30D)
                    .setDescription("-30%")
                    .build());

            receiptItemDiscounts.add(new SimplePercentageReceiptItemDiscount.BuilderSimple()
                    .setPercent(40D)
                    .setDescription("-40%")
                    .build());

            receiptItemDiscounts.add(new SimplePercentageReceiptItemDiscount.BuilderSimple()
                    .setPercent(50D)
                    .setDescription("-50%")
                    .build());

            receiptItemDiscounts.add(
                    new ThresholdPercentageReceiptItemDiscount.Builder()
                            .setPercent(10D)
                            .setThreshold(5L)
                            .setDescription("-10% если количество продукта больше чем 5")
                            .build());

            receiptItemDiscounts.add(new ThresholdPercentageReceiptItemDiscount.Builder()
                    .setPercent(1D)
                    .setThreshold(6L)
                    .setDependentDiscount(new SimpleConstantReceiptItemDiscount.Builder()
                            .setConstant(BigDecimal.valueOf(10))
                            .setDescription("Скидка 10 на продукт")
                            .build())
                    .setDescription("Скидка 1% если количество продуктов больше 6")
                    .build());

            receiptItemDiscounts.add(new ThresholdPercentageReceiptItemDiscount.Builder()
                    .setPercent(10D)
                    .setThreshold(2L)
                    .setDescription("Скидка 10% если количество продуктов больше 2")
                    .build());
        }

        return receiptItemDiscounts;
    }

    public static Collection<Email> emails() {
        if (emails == null) {
            try {
                emails = new ArrayList<>();
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
            eventEmails = new ArrayList<>();
            emails().forEach(email -> eventEmails.add(new EventEmail(email, EventType.PrintEnd)));
        }
        return eventEmails;
    }
}
