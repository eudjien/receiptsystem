package ru.clevertec.checksystem.cli;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@TestMethodOrder(OrderAnnotation.class)
class ArgumentTests {

    final Application application = new Application();

    private final static boolean deleteOutputDirAfterAll = true;

    private final static String SERIALIZED_TO_JSONFILE_FROM_PREDEFINED_FILENAME = "serialized_from_predefined.json";
    private final static String SERIALIZED_TO_XML_FILE_FROM_JSON_FILE_FILENAME = "serialized_from_json_file.xml";
    private final static String SERIALIZED_TO_JSON_FILE_FROM_XML_FILE_FILENAME = "serialized_from_xml_file.json";
    private final static String PRINTED_TO_TEXT_FILE_FROM_JSON_FILE_FILENAME = "printed_from_json_file.txt";
    private final static String PRINTED_TO_HTML_FILE_FROM_JSON_FILE_FILENAME = "printed_from_json_file.html";
    private final static String PRINTED_TO_PDF_FILE_WITH_TEMPLATE_FROM_PREDEFINED_FILENAME = "printed_with_template_from_predefined.pdf";
    private final static String PRINTED_TO_PDF_FILE_FROM_JSON_FILE_FILENAME = "printed_from_json_file.pdf";
    private final static String PRINTED_TO_PDF_FILE_WITH_TEMPLATE_FROM_JSON_FILE_FILENAME = "printed_with_template_from_json_file.pdf";
    private final static String SERIALIZED_TO_GENERATED_JSON_FILE_FROM_PREDEFINED_FILENAME = "serialized_generated_from_predefined.json";
    private final static String SERIALIZED_TO_JSON_FILE_FROM_GENERATED_FILENAME = "serialized_from_generated.json";

    private final String[] generateCheckArgs = new String[]{
            argument(Constants.Keys.MODE, Constants.Mode.GENERATE),
            argument(Constants.Keys.GENERATE_DESERIALIZE_SOURCE, Constants.Source.DATA),
            argument(Constants.Keys.GENERATE_DESERIALIZE_FORMAT, Constants.Format.IO.JSON),
            argument(Constants.Keys.GENERATE_DESERIALIZE_DATA, "[{\"id\":1,\"name\":\"999 проблем\",\"description\":\"Компьютерный магазин\",\"address\":\"ул. Пушкина, д. Калатушкина\",\"cashier\":\"+375290000000\",\"phoneNumber\":\"Василий Пупкин\",\"date\":1611537871405,\"discountIds\":[3],\"items\":[{\"productId\":1,\"quantity\":3,\"discountIds\":[]},{\"productId\":2,\"quantity\":1,\"discountIds\":[]},{\"productId\":3,\"quantity\":8,\"discountIds\":[5]},{\"productId\":4,\"quantity\":9,\"discountIds\":[]},{\"productId\":5,\"quantity\":1,\"discountIds\":[]},{\"productId\":6,\"quantity\":2,\"discountIds\":[]}]},{\"id\":2,\"name\":\"342 элемент\",\"description\":\"Гипермаркет\",\"address\":\"ул. Элементова, д. 1\",\"cashier\":\"+375290000001\",\"phoneNumber\":\"Екатерина Пупкина\",\"date\":1611537871406,\"discountIds\":[1],\"items\":[{\"productId\":3,\"quantity\":8,\"discountIds\":[6,2]},{\"productId\":2,\"quantity\":10,\"discountIds\":[]},{\"productId\":4,\"quantity\":9,\"discountIds\":[]},{\"productId\":3,\"quantity\":8,\"discountIds\":[]},{\"productId\":6,\"quantity\":7,\"discountIds\":[3]},{\"productId\":8,\"quantity\":6,\"discountIds\":[]},{\"productId\":10,\"quantity\":5,\"discountIds\":[]}]},{\"id\":3,\"name\":\"Магазин №1\",\"description\":\"Продуктовый магазин\",\"address\":\"ул. Советская, д. 1001\",\"cashier\":\"+375290000002\",\"phoneNumber\":\"Алексей Пупкин\",\"date\":1611537871406,\"discountIds\":[2],\"items\":[{\"productId\":3,\"quantity\":15,\"discountIds\":[]},{\"productId\":5,\"quantity\":1,\"discountIds\":[]},{\"productId\":7,\"quantity\":1,\"discountIds\":[]},{\"productId\":9,\"quantity\":3,\"discountIds\":[]},{\"productId\":11,\"quantity\":11,\"discountIds\":[]},{\"productId\":13,\"quantity\":6,\"discountIds\":[]}]},{\"id\":4,\"name\":\"Магазин №2\",\"description\":\"Продуктовый магазин\",\"address\":\"ул. Свиридова, д. 1234\",\"cashier\":\"+375290000003\",\"phoneNumber\":\"Татьяна Пупкина\",\"date\":1611537871407,\"discountIds\":[4],\"items\":[{\"productId\":4,\"quantity\":15,\"discountIds\":[8]},{\"productId\":6,\"quantity\":1,\"discountIds\":[]},{\"productId\":8,\"quantity\":1,\"discountIds\":[]},{\"productId\":10,\"quantity\":3,\"discountIds\":[]},{\"productId\":12,\"quantity\":15,\"discountIds\":[1]},{\"productId\":14,\"quantity\":6,\"discountIds\":[]},{\"productId\":16,\"quantity\":3,\"discountIds\":[]},{\"productId\":18,\"quantity\":11,\"discountIds\":[]},{\"productId\":20,\"quantity\":6,\"discountIds\":[]}]}]")
    };

    private static final String resourcesPath = Path.of("src", "test", "resources").toString();
    private static final String resourcesOutPath = Path.of(resourcesPath, "out").toString();

    @AfterAll
    public static void afterAll() {
        if (deleteOutputDirAfterAll) {
            deleteOutputDir();
        }
    }

    @Order(1)
    @Test
    public void throwsWhenNoMode() {
        assertThrows(IllegalArgumentException.class, () -> application.start(new String[]{}));
    }

    @Order(2)
    @Test
    public void readPreDefinedThenWriteJsonFile() {

        var outputFilePath = Path.of(resourcesOutPath, SERIALIZED_TO_JSONFILE_FROM_PREDEFINED_FILENAME);

        var args = new String[]{
                argument(Constants.Keys.MODE, Constants.Mode.PRE_DEFINED),
                argument(Constants.Keys.FILE_SERIALIZE, true),
                argument(Constants.Keys.FILE_SERIALIZE_FORMAT, Constants.Format.IO.JSON),
                argument(Constants.Keys.FILE_SERIALIZE_PATH, outputFilePath)
        };

        assertDoesNotThrow(() -> application.start(args));
    }

    @Order(3)
    @Test
    public void readJsonFileThenWriteXmlFile() {

        var inputFilePath = Path.of(resourcesOutPath, SERIALIZED_TO_JSONFILE_FROM_PREDEFINED_FILENAME);
        var outputFilePath = Path.of(resourcesOutPath, SERIALIZED_TO_XML_FILE_FROM_JSON_FILE_FILENAME);

        var args = new String[]{
                argument(Constants.Keys.MODE, Constants.Mode.FILE_DESERIALIZE),
                argument(Constants.Keys.FILE_DESERIALIZE_FORMAT, Constants.Format.IO.JSON),
                argument(Constants.Keys.FILE_DESERIALIZE_PATH, inputFilePath),
                argument(Constants.Keys.FILE_SERIALIZE, true),
                argument(Constants.Keys.FILE_SERIALIZE_FORMAT, Constants.Format.IO.XML),
                argument(Constants.Keys.FILE_SERIALIZE_PATH, outputFilePath),
        };

        assertDoesNotThrow(() -> application.start(args));
    }

    @Order(4)
    @Test
    public void readXmlFileThenWriteJsonFile() {

        var inputFilePath = Path.of(resourcesOutPath, SERIALIZED_TO_XML_FILE_FROM_JSON_FILE_FILENAME);
        var outputFilePath = Path.of(resourcesOutPath, SERIALIZED_TO_JSON_FILE_FROM_XML_FILE_FILENAME);

        var args = new String[]{
                argument(Constants.Keys.MODE, Constants.Mode.FILE_DESERIALIZE),
                argument(Constants.Keys.FILE_DESERIALIZE_FORMAT, Constants.Format.IO.XML),
                argument(Constants.Keys.FILE_DESERIALIZE_PATH, inputFilePath),
                argument(Constants.Keys.FILE_SERIALIZE, true),
                argument(Constants.Keys.FILE_SERIALIZE_FORMAT, Constants.Format.IO.JSON),
                argument(Constants.Keys.FILE_SERIALIZE_PATH, outputFilePath),
        };

        assertDoesNotThrow(() -> application.start(args));
    }

    @Order(5)
    @Test
    public void readJsonFileThenPrintToTextFile() {

        var inputFilePath = Path.of(resourcesOutPath, SERIALIZED_TO_JSONFILE_FROM_PREDEFINED_FILENAME);
        var outputFilePath = Path.of(resourcesOutPath, PRINTED_TO_TEXT_FILE_FROM_JSON_FILE_FILENAME);

        var args = new String[]{
                argument(Constants.Keys.MODE, Constants.Mode.FILE_DESERIALIZE),
                argument(Constants.Keys.FILE_DESERIALIZE_FORMAT, Constants.Format.IO.JSON),
                argument(Constants.Keys.FILE_DESERIALIZE_PATH, inputFilePath),
                argument(Constants.Keys.FILE_PRINT, true),
                argument(Constants.Keys.FILE_PRINT_FORMAT, Constants.Format.Print.TEXT),
                argument(Constants.Keys.FILE_PRINT_PATH, outputFilePath),
        };

        assertDoesNotThrow(() -> application.start(args));
    }

    @Order(6)
    @Test
    public void readJsonFileThenPrintToHtmlFile() {

        var inputFilePath = Path.of(resourcesOutPath, SERIALIZED_TO_JSONFILE_FROM_PREDEFINED_FILENAME);
        var outputFilePath = Path.of(resourcesOutPath, PRINTED_TO_HTML_FILE_FROM_JSON_FILE_FILENAME);

        var args = new String[]{
                argument(Constants.Keys.MODE, Constants.Mode.FILE_DESERIALIZE),
                argument(Constants.Keys.FILE_DESERIALIZE_FORMAT, Constants.Format.IO.JSON),
                argument(Constants.Keys.FILE_DESERIALIZE_PATH, inputFilePath),
                argument(Constants.Keys.FILE_PRINT, true),
                argument(Constants.Keys.FILE_PRINT_FORMAT, Constants.Format.Print.HTML),
                argument(Constants.Keys.FILE_PRINT_PATH, outputFilePath),
        };

        assertDoesNotThrow(() -> application.start(args));
    }

    @Order(7)
    @Test
    public void readPreDefinedThenPrintToPdfFileWithTemplate() {

        final int templateOffset = 100;

        var templateFilePath = Path.of(resourcesPath, "Clevertec_Template.pdf");
        var outputFilePath =
                Path.of(resourcesOutPath, PRINTED_TO_PDF_FILE_WITH_TEMPLATE_FROM_PREDEFINED_FILENAME);

        var args = new String[]{
                argument(Constants.Keys.MODE, Constants.Mode.PRE_DEFINED),
                argument(Constants.Keys.FILE_PRINT, true),
                argument(Constants.Keys.FILE_PRINT_FORMAT, Constants.Format.Print.PDF),
                argument(Constants.Keys.FILE_PRINT_PATH, outputFilePath),
                argument(Constants.Keys.FILE_PRINT_PDF_TEMPLATE, true),
                argument(Constants.Keys.FILE_PRINT_PDF_TEMPLATE_PATH, templateFilePath),
                argument(Constants.Keys.FILE_PRINT_PDF_TEMPLATE_OFFSET, templateOffset),
        };

        assertDoesNotThrow(() -> application.start(args));
    }

    @Order(8)
    @Test
    public void readJsonFileThenPrintToPdfFile() {

        var inputFilePath = Path.of(resourcesOutPath, SERIALIZED_TO_JSONFILE_FROM_PREDEFINED_FILENAME);
        var outputFilePath = Path.of(resourcesOutPath, PRINTED_TO_PDF_FILE_FROM_JSON_FILE_FILENAME);

        var args = new String[]{
                argument(Constants.Keys.MODE, Constants.Mode.FILE_DESERIALIZE),
                argument(Constants.Keys.FILE_DESERIALIZE_FORMAT, Constants.Format.IO.JSON),
                argument(Constants.Keys.FILE_DESERIALIZE_PATH, inputFilePath),
                argument(Constants.Keys.FILE_PRINT, true),
                argument(Constants.Keys.FILE_PRINT_FORMAT, Constants.Format.Print.PDF),
                argument(Constants.Keys.FILE_PRINT_PATH, outputFilePath),
        };

        assertDoesNotThrow(() -> application.start(args));
    }

    @Order(9)
    @Test
    public void readJsonFileThenPrintToPdfFileWithTemplate() {

        final int templateOffset = 100;

        var inputFilePath = Path.of(resourcesOutPath, SERIALIZED_TO_JSONFILE_FROM_PREDEFINED_FILENAME);
        var templateFilePath = Path.of(resourcesPath, "Clevertec_Template.pdf");
        var outputFilePath =
                Path.of(resourcesOutPath, PRINTED_TO_PDF_FILE_WITH_TEMPLATE_FROM_JSON_FILE_FILENAME);

        var args = new String[]{
                argument(Constants.Keys.MODE, Constants.Mode.FILE_DESERIALIZE),
                argument(Constants.Keys.FILE_DESERIALIZE_FORMAT, Constants.Format.IO.JSON),
                argument(Constants.Keys.FILE_DESERIALIZE_PATH, inputFilePath),
                argument(Constants.Keys.FILE_PRINT, true),
                argument(Constants.Keys.FILE_PRINT_FORMAT, Constants.Format.Print.PDF),
                argument(Constants.Keys.FILE_PRINT_PATH, outputFilePath),
                argument(Constants.Keys.FILE_PRINT_PDF_TEMPLATE, true),
                argument(Constants.Keys.FILE_PRINT_PDF_TEMPLATE_PATH, templateFilePath),
                argument(Constants.Keys.FILE_PRINT_PDF_TEMPLATE_OFFSET, templateOffset),
        };

        assertDoesNotThrow(() -> application.start(args));
    }

    @Order(10)
    @Test
    public void readPreDefinedThenWriteToGeneratedJsonFile() {

        var outputFilePath =
                Path.of(resourcesOutPath, SERIALIZED_TO_GENERATED_JSON_FILE_FROM_PREDEFINED_FILENAME);

        var args = new String[]{
                argument(Constants.Keys.MODE, Constants.Mode.PRE_DEFINED),
                argument(Constants.Keys.GENERATE_FILE_SERIALIZE, true),
                argument(Constants.Keys.GENERATE_FILE_SERIALIZE_FORMAT, Constants.Format.IO.JSON),
                argument(Constants.Keys.GENERATE_FILE_SERIALIZE_PATH, outputFilePath),
        };

        assertDoesNotThrow(() -> application.start(args));
    }

    @Order(11)
    @Test
    public void generateCheckFromArgsThenWriteToJsonFile() {

        var outputFilePath = Path.of(resourcesOutPath, SERIALIZED_TO_JSON_FILE_FROM_GENERATED_FILENAME);

        var serializeArgs = new String[]{
                argument(Constants.Keys.FILE_SERIALIZE, true),
                argument(Constants.Keys.FILE_SERIALIZE_FORMAT, Constants.Format.IO.JSON),
                argument(Constants.Keys.FILE_SERIALIZE_PATH, outputFilePath),
        };

        var args = Stream.concat(
                Arrays.stream(generateCheckArgs),
                Arrays.stream(serializeArgs))
                .toArray(String[]::new);

        assertDoesNotThrow(() -> application.start(args));
    }

    @Order(12)
    @Test
    public void generateCheckFromArgs() {
        assertDoesNotThrow(() -> Main.main(generateCheckArgs));
    }

    @Order(13)
    @Test
    public void generateCheckFromJsonFile() {

        var inputFilePath = Path.of(resourcesOutPath, SERIALIZED_TO_GENERATED_JSON_FILE_FROM_PREDEFINED_FILENAME);

        final String[] generateCheckArgs = new String[]{
                argument(Constants.Keys.MODE, Constants.Mode.GENERATE),
                argument(Constants.Keys.GENERATE_DESERIALIZE_SOURCE, Constants.Source.FILE),
                argument(Constants.Keys.GENERATE_DESERIALIZE_FORMAT, Constants.Format.IO.JSON),
                argument(Constants.Keys.GENERATE_DESERIALIZE_DATA, inputFilePath)
        };

        assertDoesNotThrow(() -> Main.main(generateCheckArgs));
    }

    private String argument(String key, Object value) {
        return "-" + key + "=" + value;
    }

    private static void deleteOutputDir() {
        var dir = Paths.get(resourcesOutPath).toFile();
        var dirFiles = dir.listFiles();

        if (dirFiles != null) {
            for (var file : dirFiles) {
                //noinspection ResultOfMethodCallIgnored
                file.delete();
            }
        }

        //noinspection ResultOfMethodCallIgnored
        dir.delete();
    }
}
