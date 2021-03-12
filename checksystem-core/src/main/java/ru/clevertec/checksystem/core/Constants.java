package ru.clevertec.checksystem.core;

public final class Constants {

    private Constants() {
    }

    public static final class Percent {
        public static final double MIN = 0;
        public static final double MAX = 100;
    }

    public static final class Format {
        public static final String JSON = "json";
        public static final String XML = "xml";
        public static final String PDF = "pdf";
        public static final String TEXT = "text";
        public static final String HTML = "html";
    }

    public static final class Types {
        public static final String PRINT = "print";
        public static final String SERIALIZE = "serialize";
    }

    public static final class Properties {

        public static final class Config {

            public static final String FILENAME = "config.properties";

            public static final class Mail {
                public static final String USERNAME = "mail.username";
                public static final String PASSWORD = "mail.password";
            }
        }
    }

    public static final class Entities {

        public static final class Mapping {

            public static final class Table {
                public static final String EMAILS = "emails";
                public static final String EVENT_EMAILS = "event_emails";
                public static final String PRODUCTS = "products";
                public static final String CHECKS = "checks";
                public static final String CHECK_DISCOUNTS = "check_discounts";
                public static final String SIMPLE_CONSTANT_CHECK_DISCOUNT = "simple_constant_check_discounts";
                public static final String SIMPLE_PERCENTAGE_CHECK_DISCOUNT = "simple_percentage_check_discounts";
                public static final String CHECK_ITEMS = "check_items";
                public static final String CHECK_ITEM_DISCOUNTS = "check_item_discounts";
                public static final String SIMPLE_CONSTANT_CHECK_ITEM_DISCOUNT = "simple_constant_check_item_discounts";
                public static final String SIMPLE_PERCENTAGE_CHECK_ITEM_DISCOUNT = "simple_percentage_check_item_discounts";
                public static final String THRESHOLD_PERCENTAGE_CHECK_ITEM_DISCOUNT = "threshold_percentage_check_item_discounts";
                public static final String CHECK__CHECK_DISCOUNT = "_check__check_discount";
                public static final String CHECK_ITEM__CHECK_ITEM_DISCOUNT = "_check_item__check_item_discount";
            }

            public static final class Column {
                public static final String ID = "id";
                public static final String NAME = "name";
                public static final String DESCRIPTION = "description";
                public static final String ADDRESS = "address";
                public static final String CASHIER = "cashier";
                public static final String PHONE_NUMBER = "phone_number";
                public static final String DATE = "date";
                public static final String PRICE = "price";
                public static final String CONSTANT = "constant";
                public static final String PERCENT = "percent";
                public static final String THRESHOLD = "threshold";
                public static final String QUANTITY = "quantity";
                public static final String EVENT_TYPE = "event_type";
            }

            public static final class JoinColumn {
                public static final String PRODUCT_ID = "product_id";
                public static final String CHECK_ID = "check_id";
                public static final String EMAIL_ID = "email_id";
                public static final String CHECK_ITEM_ID = "check_item_id";
                public static final String CHECK_DISCOUNT_ID = "check_discount_id";
                public static final String CHECK_ITEM_DISCOUNT_ID = "check_item_discount_id";
                public static final String DEPENDENT_DISCOUNT_ID = "dependent_discount_id";
            }
        }
    }

    public static class Packages {

        public static final String ROOT = "ru.clevertec.checksystem";

        public static final class CORE {
            public static final String ROOT = Packages.ROOT + ".core";
            public static final String ENTITY = ROOT + ".entity";
            public static final String REPOSITORY = ROOT + ".repository";
        }
    }
}
