package ru.clevertec.checksystem.cli;

public final class Constants {

    private Constants() {
    }

    public static final class Keys {

        public static final String INPUT = "i";

        public static final String DESERIALIZE_FORMAT = "deserialize-format";
        public static final String DESERIALIZE_PATH = "deserialize-path";

        public static final String DESERIALIZE_GENERATE_FORMAT = "deserialize-g-format";
        public static final String DESERIALIZE_GENERATE_PATH = "deserialize-g-path";

        public static final String SERIALIZE = "serialize";
        public static final String SERIALIZE_FORMAT = "serialize-format";
        public static final String SERIALIZE_PATH = "serialize-path";

        public static final String GENERATE_SERIALIZE = "serialize-g";
        public static final String GENERATE_SERIALIZE_FORMAT = "serialize-g-format";
        public static final String GENERATE_SERIALIZE_PATH = "serialize-g-path";

        public static final String PRINT = "print";
        public static final String PRINT_FORMAT = "print-format";
        public static final String PRINT_PATH = "print-path";

        public static final String PRINT_PDF_TEMPLATE = "print-pdf-template";
        public static final String PRINT_PDF_TEMPLATE_PATH = "print-pdf-template-path";
        public static final String PRINT_PDF_TEMPLATE_OFFSET = "print-pdf-template-offset";

        public static final String INPUT_FILTER_ID = "input-filter-id";
    }

    public static final class Inputs {
        public static final String DESERIALIZE_GENERATE = "deserialize-g";
        public static final String DESERIALIZE = "deserialize";
        public static final String DATABASE = "database";
    }
}
