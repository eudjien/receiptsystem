package ru.clevertec.checksystem.cli;

public final class Constants {

    private Constants() {
    }

    public static final class Keys {
        public static final String MODE = "mode";

        public static final String FILE_DESERIALIZE_FORMAT = "file-deserialize-format";
        public static final String FILE_DESERIALIZE_PATH = "file-deserialize-path";

        public static final String FILE_DESERIALIZE_GENERATE_FORMAT = "file-deserialize-generate-format";
        public static final String FILE_DESERIALIZE_GENERATE_PATH = "file-deserialize-generate-path";

        public static final String FILE_SERIALIZE = "file-serialize";
        public static final String FILE_SERIALIZE_FORMAT = "file-serialize-format";
        public static final String FILE_SERIALIZE_PATH = "file-serialize-path";

        public static final String FILE_GENERATE_SERIALIZE = "file-serialize-generate";
        public static final String FILE_GENERATE_SERIALIZE_FORMAT = "file-serialize-generate-format";
        public static final String FILE_GENERATE_SERIALIZE_PATH = "file-serialize-generate-path";

        public static final String FILE_PRINT = "file-print";
        public static final String FILE_PRINT_FORMAT = "file-print-format";
        public static final String FILE_PRINT_PATH = "file-print-path";

        public static final String FILE_PRINT_PDF_TEMPLATE = "file-print-pdf-template";
        public static final String FILE_PRINT_PDF_TEMPLATE_PATH = "file-print-pdf-template-path";
        public static final String FILE_PRINT_PDF_TEMPLATE_OFFSET = "file-print-pdf-template-offset";

        public static final String INPUT_FILTER_ID = "input-filter-id";
        public static final String PROXIED_SERVICES = "proxied-services";
    }

    public static final class Mode {
        public static final String GENERATE = "generate";
        public static final String FILE_DESERIALIZE = "file-deserialize";
        public static final String PRE_DEFINED = "pre-defined";
    }

    public static final class Format {
        public static final class Print {
            public static final String PDF = "pdf";
            public static final String TEXT = "text";
            public static final String HTML = "html";
        }

        public static final class IO {
            public static final String JSON = "json";
            public static final String XML = "xml";
        }
    }

    public static final class Task {
        public static final class Names {
            public static final String SERIALIZE = "serialize";
            public static final String PRINT = "print";
            public static final String SERIALIZE_GENERATE = "serialize-generate";
        }
    }
}
