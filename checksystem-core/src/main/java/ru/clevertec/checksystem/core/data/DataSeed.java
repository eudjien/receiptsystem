package ru.clevertec.checksystem.core.data;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.clevertec.checksystem.core.entity.Email;
import ru.clevertec.checksystem.core.entity.EventEmail;
import ru.clevertec.checksystem.core.entity.Product;
import ru.clevertec.checksystem.core.entity.discount.receipt.ConstantReceiptDiscount;
import ru.clevertec.checksystem.core.entity.discount.receipt.PercentageReceiptDiscount;
import ru.clevertec.checksystem.core.entity.discount.receipt.ReceiptDiscount;
import ru.clevertec.checksystem.core.entity.discount.receiptitem.ConstantReceiptItemDiscount;
import ru.clevertec.checksystem.core.entity.discount.receiptitem.PercentageReceiptItemDiscount;
import ru.clevertec.checksystem.core.entity.discount.receiptitem.ReceiptItemDiscount;
import ru.clevertec.checksystem.core.entity.discount.receiptitem.ThresholdPercentageReceiptItemDiscount;
import ru.clevertec.checksystem.core.entity.receipt.Receipt;
import ru.clevertec.checksystem.core.entity.receipt.ReceiptItem;
import ru.clevertec.checksystem.core.event.EventType;
import ru.clevertec.checksystem.core.repository.*;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Component
@RequiredArgsConstructor
public final class DataSeed {

    private static Set<Product> products;
    private static Set<Receipt> receipts;
    private static Set<ReceiptDiscount> receiptDiscounts;
    private static Set<ReceiptItemDiscount> receiptItemDiscounts;
    private static Set<Email> emails;
    private static Set<EventEmail> eventEmails;

    private final ReceiptRepository receiptRepository;
    private final ProductRepository productRepository;
    private final ReceiptDiscountRepository receiptDiscountRepository;
    private final ReceiptItemDiscountRepository receiptItemDiscountRepository;
    private final EmailRepository emailRepository;
    private final EventEmailRepository eventEmailRepository;

    public void dbSeed() throws ParseException {
        receiptRepository.saveAll(receipts());
        productRepository.saveAll(products());
        receiptDiscountRepository.saveAll(receiptDiscounts());
        receiptItemDiscountRepository.saveAll(receiptItemDiscounts());
        emailRepository.saveAll(emails());
        eventEmailRepository.saveAll(eventEmails());
    }

    public static Collection<Product> products() {
        if (products == null) {
            products = new HashSet<>();
            products.add(Product.builder().name("Пельмени").price(BigDecimal.valueOf(12.48)).build());
            products.add(Product.builder().name("Картошка").price(BigDecimal.valueOf(7.5)).build());
            products.add(Product.builder().name("Помидоры").price(BigDecimal.valueOf(3.91)).build());
            products.add(Product.builder().name("Сало").price(BigDecimal.valueOf(10)).build());
            products.add(Product.builder().name("Кукуруза").price(BigDecimal.valueOf(20)).build());
            products.add(Product.builder().name("Макароны").price(BigDecimal.valueOf(8.5)).build());
            products.add(Product.builder().name("Колбаса").price(BigDecimal.valueOf(20)).build());
            products.add(Product.builder().name("Рис").price(BigDecimal.valueOf(19.999)).build());
            products.add(Product.builder().name("Рыба").price(BigDecimal.valueOf(50.2)).build());
            products.add(Product.builder().name("Апельсины").price(BigDecimal.valueOf(30.5)).build());
            products.add(Product.builder().name("Яблоки").price(BigDecimal.valueOf(8.7)).build());
            products.add(Product.builder().name("Чай").price(BigDecimal.valueOf(3.51)).build());
            products.add(Product.builder().name("Кофе").price(BigDecimal.valueOf(3.5)).build());
            products.add(Product.builder().name("Печенье").price(BigDecimal.valueOf(2.1)).build());
            products.add(Product.builder().name("Конфеты").price(BigDecimal.valueOf(5.6)).build());
            products.add(Product.builder().name("Монитор").price(BigDecimal.valueOf(155.5)).build());
            products.add(Product.builder().name("Клавиатура").price(BigDecimal.valueOf(30)).build());
            products.add(Product.builder().name("Мышка").price(BigDecimal.valueOf(30)).build());
            products.add(Product.builder().name("SSD").price(BigDecimal.valueOf(50)).build());
            products.add(Product.builder().name("HDD").price(BigDecimal.valueOf(25.5)).build());
        }
        return products;
    }

    public static Collection<Receipt> receipts() throws ParseException {

        var receiptDiscounts = receiptDiscounts().toArray(ReceiptDiscount[]::new);
        var receiptItemDiscounts = receiptItemDiscounts().toArray(ReceiptItemDiscount[]::new);
        var dateFormatter = new SimpleDateFormat("dd.MM.yyyy");

        if (receipts == null) {
            receipts = new HashSet<>();

            var products = products().toArray(Product[]::new);

            // Receipt 1
            var receipt = Receipt.builder()
                    .name("999 проблем")
                    .description("Компьютерный магазин")
                    .address("ул. Пушкина, д. Калатушкина")
                    .phoneNumber("+375290000000")
                    .cashier("Василий Пупкин")
                    .date(dateFormatter.parse("18.04.2018"))
                    .build();
            receipt.addReceiptItem(ReceiptItem.builder().product(products[0]).quantity(3L).build());
            receipt.addReceiptItem(ReceiptItem.builder().product(products[1]).quantity(1L).build());
            var discountedItem = ReceiptItem.builder().product(products[2]).quantity(8L).build();
            discountedItem.addDiscount(receiptItemDiscounts[4]);
            receipt.addReceiptItem(discountedItem);
            receipt.addReceiptItem(ReceiptItem.builder().product(products[5]).quantity(2L).build());
            receipt.addDiscount(receiptDiscounts[2]);
            receipts.add(receipt);

            // Receipt 2
            receipt = Receipt.builder()
                    .name("342 элемент")
                    .description("Гипермаркет")
                    .address("ул. Элементова, д. 1")
                    .phoneNumber("+375290000001")
                    .cashier("Екатерина Пупкина")
                    .date(dateFormatter.parse("02.03.2018")).build();
            discountedItem = ReceiptItem.builder().product(products[2]).quantity(8L).build();
            discountedItem.addDiscount(receiptItemDiscounts[5]);
            receipt.addReceiptItem(discountedItem);
            receipt.addReceiptItem(ReceiptItem.builder().product(products[1]).quantity(10L).build());
            receipt.addReceiptItem(ReceiptItem.builder().product(products[3]).quantity(9L).build());
            receipt.addReceiptItem(ReceiptItem.builder().product(products[10]).quantity(8L).build());
            discountedItem = ReceiptItem.builder().product(products[11]).quantity(8L).build();
            discountedItem.addDiscount(receiptItemDiscounts[1]);
            receipt.addReceiptItem(discountedItem);
            discountedItem = ReceiptItem.builder().product(products[5]).quantity(7L).build();
            discountedItem.addDiscount(receiptItemDiscounts[2]);
            receipt.addReceiptItem(discountedItem);
            receipt.addReceiptItem(ReceiptItem.builder().product(products[9]).quantity(5L).build());
            receipt.addDiscount(receiptDiscounts[0]);
            receipts.add(receipt);

            // Receipt 3
            receipt = Receipt.builder()
                    .name("Магазин №1")
                    .description("Продуктовый магазин")
                    .address("ул. Советская, д. 1001")
                    .phoneNumber("+375290000002")
                    .cashier("Алексей Пупкин")
                    .date(dateFormatter.parse("01.02.2018"))
                    .build();
            receipt.addReceiptItem(ReceiptItem.builder().product(products[2]).quantity(15L).build());
            receipt.addReceiptItem(ReceiptItem.builder().product(products[4]).quantity(1L).build());
            receipt.addReceiptItem(ReceiptItem.builder().product(products[6]).quantity(1L).build());
            receipt.addReceiptItem(ReceiptItem.builder().product(products[8]).quantity(3L).build());
            receipt.addReceiptItem(ReceiptItem.builder().product(products[10]).quantity(11L).build());
            receipt.addReceiptItem(ReceiptItem.builder().product(products[12]).quantity(6L).build());
            receipt.addDiscount(receiptDiscounts[1]);
            receipts.add(receipt);

            // Receipt 4
            receipt = Receipt.builder()
                    .name("Магазин №2")
                    .description("Продуктовый магазин")
                    .address("ул. Свиридова, д. 1234")
                    .phoneNumber("+375290000003")
                    .cashier("Татьяна Пупкина")
                    .date(dateFormatter.parse("04.07.2017"))
                    .build();
            discountedItem = ReceiptItem.builder().product(products[3]).quantity(15L).build();
            discountedItem.addDiscount(receiptItemDiscounts[6]);
            receipt.addReceiptItem(discountedItem);
            receipt.addReceiptItem(ReceiptItem.builder().product(products[5]).quantity(1L).build());
            receipt.addReceiptItem(ReceiptItem.builder().product(products[7]).quantity(1L).build());
            receipt.addReceiptItem(ReceiptItem.builder().product(products[9]).quantity(3L).build());
            discountedItem = ReceiptItem.builder().product(products[11]).quantity(15L).build();
            discountedItem.addDiscount(receiptItemDiscounts[0]);
            receipt.addReceiptItem(discountedItem);
            receipt.addReceiptItem(ReceiptItem.builder().product(products[13]).quantity(6L).build());
            receipt.addReceiptItem(ReceiptItem.builder().product(products[15]).quantity(3L).build());
            receipt.addReceiptItem(ReceiptItem.builder().product(products[17]).quantity(11L).build());
            receipt.addReceiptItem(ReceiptItem.builder().product(products[19]).quantity(6L).build());
            receipt.addDiscount(receiptDiscounts[3]);
            receipts.add(receipt);
        }

        return receipts;
    }

    public static Collection<ReceiptDiscount> receiptDiscounts() {

        if (receiptDiscounts == null) {
            receiptDiscounts = new HashSet<>();

            receiptDiscounts.add(PercentageReceiptDiscount.builder()
                    .description("-30% на сумму чека")
                    .percent(30D).build());
            receiptDiscounts.add(PercentageReceiptDiscount.builder()
                    .description("-20% на сумму чека")
                    .percent(20D).build());
            receiptDiscounts.add(PercentageReceiptDiscount.builder()
                    .description("-35% на сумму чека")
                    .percent(35D).build());
            receiptDiscounts.add(ConstantReceiptDiscount.builder()
                    .description("-5$ на сумму чека")
                    .constant(BigDecimal.valueOf(5)).build());
            receiptDiscounts.add(ConstantReceiptDiscount.builder()
                    .description("-10$ на сумму чека")
                    .constant(BigDecimal.valueOf(10)).build());
            receiptDiscounts.add(ConstantReceiptDiscount.builder()
                    .description("-10$")
                    .constant(BigDecimal.valueOf(10))
                    .dependentDiscount(PercentageReceiptDiscount.builder()
                            .description("-35%")
                            .percent(35D).build()).build());
            receiptDiscounts.add(PercentageReceiptDiscount.builder()
                    .description("-35%")
                    .percent(35D)
                    .dependentDiscount(ConstantReceiptDiscount.builder()
                            .description("-10%").constant(BigDecimal.valueOf(10)).build()).build());
            receiptDiscounts.add(PercentageReceiptDiscount.builder()
                    .description("-35%")
                    .percent(35D)
                    .dependentDiscount(ConstantReceiptDiscount.builder()
                            .description("-10$")
                            .constant(BigDecimal.valueOf(10))
                            .dependentDiscount(PercentageReceiptDiscount.builder()
                                    .description("-15%")
                                    .percent(15D).build()).build()).build());
        }

        return receiptDiscounts;
    }

    public static Collection<ReceiptItemDiscount> receiptItemDiscounts() {

        if (receiptItemDiscounts == null) {
            receiptItemDiscounts = new HashSet<>();

            receiptItemDiscounts.add(ConstantReceiptItemDiscount.builder()
                    .description("-5$")
                    .constant(BigDecimal.valueOf(5)).build());
            receiptItemDiscounts.add(PercentageReceiptItemDiscount.builder()
                    .description("-30%")
                    .percent(30D).build());
            receiptItemDiscounts.add(PercentageReceiptItemDiscount.builder()
                    .description("-40%")
                    .percent(40D).build());
            receiptItemDiscounts.add(PercentageReceiptItemDiscount.builder()
                    .description("-50%")
                    .percent(50D).build());
            receiptItemDiscounts.add(ThresholdPercentageReceiptItemDiscount.builder()
                    .description("-10% если количество продукта больше чем 5")
                    .percent(10D)
                    .threshold(5L).build());
            receiptItemDiscounts.add(ThresholdPercentageReceiptItemDiscount.builder()
                    .description("Скидка 1% если количество продуктов больше 6")
                    .percent(1D)
                    .threshold(6L)
                    .dependentDiscount(ConstantReceiptItemDiscount.builder()
                            .description("Скидка 10 на продукт")
                            .constant(BigDecimal.valueOf(10))
                            .build()).build());
            receiptItemDiscounts.add(ThresholdPercentageReceiptItemDiscount.builder()
                    .description("Скидка 10% если количество продуктов больше 2")
                    .percent(10D)
                    .threshold(2L).build());
        }

        return receiptItemDiscounts;
    }

    public static Collection<Email> emails() {
        if (emails == null) {
            emails = new HashSet<>();
            emails.add(new Email("lakadmakatag@gmail.com"));
            emails.add(new Email("chcksstm1@gmail.com"));
        }
        return emails;
    }

    public static Collection<EventEmail> eventEmails() {
        if (eventEmails == null) {
            eventEmails = new HashSet<>();
            emails().forEach(email -> eventEmails.add(new EventEmail(email, EventType.PrintEnd, null)));
        }
        return eventEmails;
    }
}
