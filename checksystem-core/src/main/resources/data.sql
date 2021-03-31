INSERT INTO emails (id, address) VALUES (75, 'chcksstm1@gmail.com');
INSERT INTO emails (id, address) VALUES (74, 'lakadmakatag@gmail.com');

INSERT INTO event_emails (id, email_id, event_type) VALUES (76, 74, 'PRINT_END');
INSERT INTO event_emails (id, email_id, event_type) VALUES (77, 75, 'PRINT_END');

INSERT INTO products (id, name, price) VALUES (4, 'Сало', 10.00);
INSERT INTO products (id, name, price) VALUES (6, 'Пельмени', 12.48);
INSERT INTO products (id, name, price) VALUES (8, 'Картошка', 7.50);
INSERT INTO products (id, name, price) VALUES (10, 'Помидоры', 3.91);
INSERT INTO products (id, name, price) VALUES (13, 'Макароны', 8.50);
INSERT INTO products (id, name, price) VALUES (15, 'Кукуруза', 20.00);
INSERT INTO products (id, name, price) VALUES (19, 'Апельсины', 30.50);
INSERT INTO products (id, name, price) VALUES (22, 'Чай', 3.51);
INSERT INTO products (id, name, price) VALUES (27, 'Рис', 20.00);
INSERT INTO products (id, name, price) VALUES (29, 'Яблоки', 8.70);
INSERT INTO products (id, name, price) VALUES (38, 'Колбаса', 20.00);
INSERT INTO products (id, name, price) VALUES (40, 'Кофе', 3.50);
INSERT INTO products (id, name, price) VALUES (43, 'Рыба', 50.20);
INSERT INTO products (id, name, price) VALUES (48, 'HDD', 25.50);
INSERT INTO products (id, name, price) VALUES (55, 'Печенье', 2.10);
INSERT INTO products (id, name, price) VALUES (59, 'Монитор', 155.50);
INSERT INTO products (id, name, price) VALUES (61, 'Мышка', 30.00);
INSERT INTO products (id, name, price) VALUES (62, 'Конфеты', 5.60);
INSERT INTO products (id, name, price) VALUES (63, 'Клавиатура', 30.00);
INSERT INTO products (id, name, price) VALUES (64, 'SSD', 50.00);

INSERT INTO receipts (id, address, cashier, date, description, name, phone_number) VALUES (1, 'ул. Пушкина, д. Калатушкина', 'Василий Пупкин', '2021-03-31 06:42:03.607000', 'Компьютерный магазин', '999 проблем', '+375290000000');
INSERT INTO receipts (id, address, cashier, date, description, name, phone_number) VALUES (16, 'ул. Элементова, д. 1', 'Екатерина Пупкина', '2021-03-31 06:42:03.614000', 'Гипермаркет', '342 элемент', '+375290000001');
INSERT INTO receipts (id, address, cashier, date, description, name, phone_number) VALUES (34, 'ул. Советская, д. 1001', 'Алексей Пупкин', '2021-03-31 06:42:03.614000', 'Продуктовый магазин', 'Магазин №1', '+375290000002');
INSERT INTO receipts (id, address, cashier, date, description, name, phone_number) VALUES (45, 'ул. Свиридова, д. 1234', 'Татьяна Пупкина', '2021-03-31 06:42:03.614000', 'Продуктовый магазин', 'Магазин №2', '+375290000003');

INSERT INTO receipt_discounts (type, id, dependent_discount_id, description) VALUES ('SimplePercentageReceiptDiscount', 2, null, '-35% на сумму чека');
INSERT INTO receipt_discounts (type, id, dependent_discount_id, description) VALUES ('SimplePercentageReceiptDiscount', 17, null, '-30% на сумму чека');
INSERT INTO receipt_discounts (type, id, dependent_discount_id, description) VALUES ('SimplePercentageReceiptDiscount', 35, null, '-20% на сумму чека');
INSERT INTO receipt_discounts (type, id, dependent_discount_id, description) VALUES ('SimpleConstantReceiptDiscount', 46, null, '-5$ на сумму чека');
INSERT INTO receipt_discounts (type, id, dependent_discount_id, description) VALUES ('SimpleConstantReceiptDiscount', 65, null, '-10$ на сумму чека');
INSERT INTO receipt_discounts (type, id, dependent_discount_id, description) VALUES ('SimplePercentageReceiptDiscount', 67, null, '-35%');
INSERT INTO receipt_discounts (type, id, dependent_discount_id, description) VALUES ('SimpleConstantReceiptDiscount', 66, 67, '-10$');
INSERT INTO receipt_discounts (type, id, dependent_discount_id, description) VALUES ('SimpleConstantReceiptDiscount', 69, null, '-10$');
INSERT INTO receipt_discounts (type, id, dependent_discount_id, description) VALUES ('SimplePercentageReceiptDiscount', 68, 69, '-35%');
INSERT INTO receipt_discounts (type, id, dependent_discount_id, description) VALUES ('SimplePercentageReceiptDiscount', 72, null, '-15%');
INSERT INTO receipt_discounts (type, id, dependent_discount_id, description) VALUES ('SimpleConstantReceiptDiscount', 71, 72, '-10$');
INSERT INTO receipt_discounts (type, id, dependent_discount_id, description) VALUES ('SimplePercentageReceiptDiscount', 70, 71, '-35%');

INSERT INTO _receipt__receipt_discount (receipt_id, receipt_discount_id) VALUES (1, 2);
INSERT INTO _receipt__receipt_discount (receipt_id, receipt_discount_id) VALUES (16, 17);
INSERT INTO _receipt__receipt_discount (receipt_id, receipt_discount_id) VALUES (34, 35);
INSERT INTO _receipt__receipt_discount (receipt_id, receipt_discount_id) VALUES (45, 46);

INSERT INTO simple_constant_receipt_discounts (constant, id) VALUES (5.00, 46);
INSERT INTO simple_constant_receipt_discounts (constant, id) VALUES (10.00, 65);
INSERT INTO simple_constant_receipt_discounts (constant, id) VALUES (10.00, 66);
INSERT INTO simple_constant_receipt_discounts (constant, id) VALUES (10.00, 69);
INSERT INTO simple_constant_receipt_discounts (constant, id) VALUES (10.00, 71);

INSERT INTO simple_percentage_receipt_discounts (percent, id) VALUES (35, 2);
INSERT INTO simple_percentage_receipt_discounts (percent, id) VALUES (30, 17);
INSERT INTO simple_percentage_receipt_discounts (percent, id) VALUES (20, 35);
INSERT INTO simple_percentage_receipt_discounts (percent, id) VALUES (35, 67);
INSERT INTO simple_percentage_receipt_discounts (percent, id) VALUES (35, 68);
INSERT INTO simple_percentage_receipt_discounts (percent, id) VALUES (35, 70);
INSERT INTO simple_percentage_receipt_discounts (percent, id) VALUES (15, 72);

INSERT INTO receipt_items (id, product_id, quantity, receipt_id) VALUES (3, 4, 9, 1);
INSERT INTO receipt_items (id, product_id, quantity, receipt_id) VALUES (5, 6, 3, 1);
INSERT INTO receipt_items (id, product_id, quantity, receipt_id) VALUES (7, 8, 1, 1);
INSERT INTO receipt_items (id, product_id, quantity, receipt_id) VALUES (9, 10, 8, 1);
INSERT INTO receipt_items (id, product_id, quantity, receipt_id) VALUES (12, 13, 2, 1);
INSERT INTO receipt_items (id, product_id, quantity, receipt_id) VALUES (14, 15, 1, 1);
INSERT INTO receipt_items (id, product_id, quantity, receipt_id) VALUES (18, 19, 5, 16);
INSERT INTO receipt_items (id, product_id, quantity, receipt_id) VALUES (20, 8, 10, 16);
INSERT INTO receipt_items (id, product_id, quantity, receipt_id) VALUES (21, 22, 8, 16);
INSERT INTO receipt_items (id, product_id, quantity, receipt_id) VALUES (24, 13, 7, 16);
INSERT INTO receipt_items (id, product_id, quantity, receipt_id) VALUES (26, 27, 6, 16);
INSERT INTO receipt_items (id, product_id, quantity, receipt_id) VALUES (28, 29, 8, 16);
INSERT INTO receipt_items (id, product_id, quantity, receipt_id) VALUES (30, 10, 8, 16);
INSERT INTO receipt_items (id, product_id, quantity, receipt_id) VALUES (33, 4, 9, 16);
INSERT INTO receipt_items (id, product_id, quantity, receipt_id) VALUES (36, 15, 1, 34);
INSERT INTO receipt_items (id, product_id, quantity, receipt_id) VALUES (37, 38, 1, 34);
INSERT INTO receipt_items (id, product_id, quantity, receipt_id) VALUES (39, 40, 6, 34);
INSERT INTO receipt_items (id, product_id, quantity, receipt_id) VALUES (41, 29, 11, 34);
INSERT INTO receipt_items (id, product_id, quantity, receipt_id) VALUES (42, 43, 3, 34);
INSERT INTO receipt_items (id, product_id, quantity, receipt_id) VALUES (44, 10, 15, 34);
INSERT INTO receipt_items (id, product_id, quantity, receipt_id) VALUES (47, 48, 6, 45);
INSERT INTO receipt_items (id, product_id, quantity, receipt_id) VALUES (49, 22, 15, 45);
INSERT INTO receipt_items (id, product_id, quantity, receipt_id) VALUES (51, 4, 15, 45);
INSERT INTO receipt_items (id, product_id, quantity, receipt_id) VALUES (53, 27, 1, 45);
INSERT INTO receipt_items (id, product_id, quantity, receipt_id) VALUES (54, 55, 6, 45);
INSERT INTO receipt_items (id, product_id, quantity, receipt_id) VALUES (56, 13, 1, 45);
INSERT INTO receipt_items (id, product_id, quantity, receipt_id) VALUES (57, 19, 3, 45);
INSERT INTO receipt_items (id, product_id, quantity, receipt_id) VALUES (58, 59, 3, 45);
INSERT INTO receipt_items (id, product_id, quantity, receipt_id) VALUES (60, 61, 11, 45);

INSERT INTO receipt_item_discounts (type, id, dependent_discount_id, description) VALUES ('ThresholdPercentageReceiptItemDiscount', 11, null, '-10% если количество продукта больше чем 5');
INSERT INTO receipt_item_discounts (type, id, dependent_discount_id, description) VALUES ('SimplePercentageReceiptItemDiscount', 23, null, '-30%');
INSERT INTO receipt_item_discounts (type, id, dependent_discount_id, description) VALUES ('SimplePercentageReceiptItemDiscount', 25, null, '-40%');
INSERT INTO receipt_item_discounts (type, id, dependent_discount_id, description) VALUES ('SimpleConstantReceiptItemDiscount', 32, null, 'Скидка 10 на продукт');
INSERT INTO receipt_item_discounts (type, id, dependent_discount_id, description) VALUES ('ThresholdPercentageReceiptItemDiscount', 31, 32, 'Скидка 1% если количество продуктов больше 6');
INSERT INTO receipt_item_discounts (type, id, dependent_discount_id, description) VALUES ('SimpleConstantReceiptItemDiscount', 50, null, '-5$');
INSERT INTO receipt_item_discounts (type, id, dependent_discount_id, description) VALUES ('ThresholdPercentageReceiptItemDiscount', 52, null, 'Скидка 10% если количество продуктов больше 2');
INSERT INTO receipt_item_discounts (type, id, dependent_discount_id, description) VALUES ('SimplePercentageReceiptItemDiscount', 73, null, '-50%');

INSERT INTO _receipt_item__receipt_item_discount (receipt_item_id, receipt_item_discount_id) VALUES (9, 11);
INSERT INTO _receipt_item__receipt_item_discount (receipt_item_id, receipt_item_discount_id) VALUES (21, 23);
INSERT INTO _receipt_item__receipt_item_discount (receipt_item_id, receipt_item_discount_id) VALUES (24, 25);
INSERT INTO _receipt_item__receipt_item_discount (receipt_item_id, receipt_item_discount_id) VALUES (30, 31);
INSERT INTO _receipt_item__receipt_item_discount (receipt_item_id, receipt_item_discount_id) VALUES (49, 50);
INSERT INTO _receipt_item__receipt_item_discount (receipt_item_id, receipt_item_discount_id) VALUES (51, 52);

INSERT INTO simple_constant_receipt_item_discounts (constant, id) VALUES (10.00, 32);
INSERT INTO simple_constant_receipt_item_discounts (constant, id) VALUES (5.00, 50);

INSERT INTO simple_percentage_receipt_item_discounts (percent, id) VALUES (30, 23);
INSERT INTO simple_percentage_receipt_item_discounts (percent, id) VALUES (40, 25);
INSERT INTO simple_percentage_receipt_item_discounts (percent, id) VALUES (50, 73);

INSERT INTO threshold_percentage_receipt_item_discounts (percent, threshold, id) VALUES (10, 5, 11);
INSERT INTO threshold_percentage_receipt_item_discounts (percent, threshold, id) VALUES (1, 6, 31);
INSERT INTO threshold_percentage_receipt_item_discounts (percent, threshold, id) VALUES (10, 2, 52);

INSERT INTO hibernate_sequence (next_val) VALUES (78);
