package ru.clevertec.checksystem.core;

public abstract class Constants {

    public static final class Format {

        public static final class IO {

            public static final String JSON = "json";
            public static final String XML = "xml";
        }

        public static final class Print {

            public static final String PDF = "pdf";
            public static final String TEXT = "text";
            public static final String HTML = "html";
        }
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
}
