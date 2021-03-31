package ru.clevertec.checksystem.cli;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.clevertec.checksystem.cli.application.Application;
import ru.clevertec.checksystem.core.constant.Formats;
import ru.clevertec.checksystem.core.data.DataSeed;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static ru.clevertec.checksystem.cli.Constants.Inputs;
import static ru.clevertec.checksystem.cli.Constants.Keys;

@ActiveProfiles("test")
@SpringBootTest(classes = ConsoleTestConfiguration.class)
@TestMethodOrder(OrderAnnotation.class)
class ArgumentTests {

    @Autowired
    private Application application;

    private final static boolean deleteOutputDirAfterAll = true;

    private final static String SERIALIZED_TO_JSON_FILE_FROM_PREDEFINED_FILENAME
            = "serialized_from_predefined.json";
    private final static String SERIALIZED_TO_XML_FILE_FROM_JSON_FILE_FILENAME
            = "serialized_from_json_file.xml";
    private final static String SERIALIZED_TO_JSON_FILE_FROM_XML_FILE_FILENAME
            = "serialized_from_xml_file.json";
    private final static String PRINTED_TO_TEXT_FILE_FROM_JSON_FILE_FILENAME
            = "printed_from_json_file.txt";
    private final static String PRINTED_TO_HTML_FILE_FROM_JSON_FILE_FILENAME
            = "printed_from_json_file.html";
    private final static String PRINTED_TO_PDF_FILE_WITH_TEMPLATE_FROM_PREDEFINED_FILENAME
            = "printed_with_template_from_predefined.pdf";
    private final static String PRINTED_TO_PDF_FILE_FROM_JSON_FILE_FILENAME
            = "printed_from_json_file.pdf";
    private final static String PRINTED_TO_PDF_FILE_WITH_TEMPLATE_FROM_JSON_FILE_FILENAME
            = "printed_with_template_from_json_file.pdf";
    private final static String SERIALIZED_TO_GENERATED_JSON_FILE_FROM_PREDEFINED_FILENAME
            = "serialized_generated_from_predefined.json";
    private final static String SERIALIZED_TO_JSON_FILE_FROM_GENERATED_FILENAME
            = "serialized_from_generated.json";

    private static final String resourcesPath = Path.of("src", "test", "resources").toString();
    private static final String resourcesOutPath = Path.of(resourcesPath, "out").toString();

    @BeforeAll
    public static void setUp() {
    }

    @AfterAll
    public static void afterAll() {
        if (deleteOutputDirAfterAll)
            deleteOutputDir();
    }

    @Order(1)
    @Test
    public void hasErrorWhenNoInput() {
        application.getArgumentsFinder().setArguments(new String[]{});
        assertTrue(() -> application.call().hasErrors());
    }

    @Order(2)
    @Test
    public void readPreDefinedThenWriteJsonFile() {

        var outputFilePath = Path.of(resourcesOutPath, SERIALIZED_TO_JSON_FILE_FROM_PREDEFINED_FILENAME);

        var args = new String[]{
                argument(Keys.INPUT, Inputs.DATABASE),
                argument(Keys.SERIALIZE, true),
                argument(Keys.SERIALIZE_FORMAT, Formats.JSON),
                argument(Keys.SERIALIZE_PATH, outputFilePath)
        };

        application.getArgumentsFinder().setArguments(args);
        assertFalse(() -> application.call().hasErrors());
    }

    @Order(3)
    @Test
    public void readJsonFileThenWriteXmlFile() {

        var inputFilePath = Path.of(resourcesOutPath, SERIALIZED_TO_JSON_FILE_FROM_PREDEFINED_FILENAME);
        var outputFilePath = Path.of(resourcesOutPath, SERIALIZED_TO_XML_FILE_FROM_JSON_FILE_FILENAME);

        var args = new String[]{
                argument(Keys.INPUT, Inputs.DESERIALIZE),
                argument(Keys.DESERIALIZE_FORMAT, Formats.JSON),
                argument(Keys.DESERIALIZE_PATH, inputFilePath),
                argument(Keys.SERIALIZE, true),
                argument(Keys.SERIALIZE_FORMAT, Formats.XML),
                argument(Keys.SERIALIZE_PATH, outputFilePath),
        };

        application.getArgumentsFinder().setArguments(args);
        assertFalse(() -> application.call().hasErrors());
    }

    @Order(4)
    @Test
    public void readXmlFileThenWriteJsonFile() {

        var inputFilePath = Path.of(resourcesOutPath, SERIALIZED_TO_XML_FILE_FROM_JSON_FILE_FILENAME);
        var outputFilePath = Path.of(resourcesOutPath, SERIALIZED_TO_JSON_FILE_FROM_XML_FILE_FILENAME);

        var args = new String[]{
                argument(Keys.INPUT, Inputs.DESERIALIZE),
                argument(Keys.DESERIALIZE_FORMAT, Formats.XML),
                argument(Keys.DESERIALIZE_PATH, inputFilePath),
                argument(Keys.SERIALIZE, true),
                argument(Keys.SERIALIZE_FORMAT, Formats.JSON),
                argument(Keys.SERIALIZE_PATH, outputFilePath),
        };

        application.getArgumentsFinder().setArguments(args);
        assertFalse(() -> application.call().hasErrors());
    }

    @Order(5)
    @Test
    public void readJsonFileThenPrintToTextFile() {

        var inputFilePath = Path.of(resourcesOutPath, SERIALIZED_TO_JSON_FILE_FROM_PREDEFINED_FILENAME);
        var outputFilePath = Path.of(resourcesOutPath, PRINTED_TO_TEXT_FILE_FROM_JSON_FILE_FILENAME);

        var args = new String[]{
                argument(Keys.INPUT, Inputs.DESERIALIZE),
                argument(Keys.DESERIALIZE_FORMAT, Formats.JSON),
                argument(Keys.DESERIALIZE_PATH, inputFilePath),
                argument(Keys.PRINT, true),
                argument(Keys.PRINT_FORMAT, Formats.TEXT),
                argument(Keys.PRINT_PATH, outputFilePath),
        };

        application.getArgumentsFinder().setArguments(args);
        assertFalse(() -> application.call().hasErrors());
    }

    @Order(6)
    @Test
    public void readJsonFileThenPrintToHtmlFile() {

        var inputFilePath = Path.of(resourcesOutPath, SERIALIZED_TO_JSON_FILE_FROM_PREDEFINED_FILENAME);
        var outputFilePath = Path.of(resourcesOutPath, PRINTED_TO_HTML_FILE_FROM_JSON_FILE_FILENAME);

        var args = new String[]{
                argument(Keys.INPUT, Inputs.DESERIALIZE),
                argument(Keys.DESERIALIZE_FORMAT, Formats.JSON),
                argument(Keys.DESERIALIZE_PATH, inputFilePath),
                argument(Keys.PRINT, true),
                argument(Keys.PRINT_FORMAT, Formats.HTML),
                argument(Keys.PRINT_PATH, outputFilePath),
        };

        application.getArgumentsFinder().setArguments(args);
        assertFalse(() -> application.call().hasErrors());
    }

    @Order(7)
    @Test
    public void readPreDefinedThenPrintToPdfFileWithTemplate() {

        final int templateOffset = 100;

        var templateFilePath = Path.of(resourcesPath, "Clevertec_Template.pdf");
        var outputFilePath =
                Path.of(resourcesOutPath, PRINTED_TO_PDF_FILE_WITH_TEMPLATE_FROM_PREDEFINED_FILENAME);

        var args = new String[]{
                argument(Keys.INPUT, Inputs.DATABASE),
                argument(Keys.PRINT, true),
                argument(Keys.PRINT_FORMAT, Formats.PDF),
                argument(Keys.PRINT_PATH, outputFilePath),
                argument(Keys.PRINT_PDF_TEMPLATE, true),
                argument(Keys.PRINT_PDF_TEMPLATE_PATH, templateFilePath),
                argument(Keys.PRINT_PDF_TEMPLATE_OFFSET, templateOffset),
        };

        application.getArgumentsFinder().setArguments(args);
        assertFalse(() -> application.call().hasErrors());
    }

    @Order(8)
    @Test
    public void readJsonFileThenPrintToPdfFile() {

        var inputFilePath = Path.of(resourcesOutPath, SERIALIZED_TO_JSON_FILE_FROM_PREDEFINED_FILENAME);
        var outputFilePath = Path.of(resourcesOutPath, PRINTED_TO_PDF_FILE_FROM_JSON_FILE_FILENAME);

        var args = new String[]{
                argument(Keys.INPUT, Inputs.DESERIALIZE),
                argument(Keys.DESERIALIZE_FORMAT, Formats.JSON),
                argument(Keys.DESERIALIZE_PATH, inputFilePath),
                argument(Keys.PRINT, true),
                argument(Keys.PRINT_FORMAT, Formats.PDF),
                argument(Keys.PRINT_PATH, outputFilePath),
        };

        application.getArgumentsFinder().setArguments(args);
        assertFalse(() -> application.call().hasErrors());
    }

    @Order(9)
    @Test
    public void readJsonFileThenPrintToPdfFileWithTemplate() {

        final int templateOffset = 100;

        var inputFilePath = Path.of(resourcesOutPath, SERIALIZED_TO_JSON_FILE_FROM_PREDEFINED_FILENAME);
        var templateFilePath = Path.of(resourcesPath, "Clevertec_Template.pdf");
        var outputFilePath =
                Path.of(resourcesOutPath, PRINTED_TO_PDF_FILE_WITH_TEMPLATE_FROM_JSON_FILE_FILENAME);

        var args = new String[]{
                argument(Keys.INPUT, Inputs.DESERIALIZE),
                argument(Keys.DESERIALIZE_FORMAT, Formats.JSON),
                argument(Keys.DESERIALIZE_PATH, inputFilePath),
                argument(Keys.PRINT, true),
                argument(Keys.PRINT_FORMAT, Formats.PDF),
                argument(Keys.PRINT_PATH, outputFilePath),
                argument(Keys.PRINT_PDF_TEMPLATE, true),
                argument(Keys.PRINT_PDF_TEMPLATE_PATH, templateFilePath),
                argument(Keys.PRINT_PDF_TEMPLATE_OFFSET, templateOffset),
        };

        application.getArgumentsFinder().setArguments(args);
        assertFalse(() -> application.call().hasErrors());
    }

    @Order(10)
    @Test
    public void readPreDefinedThenWriteToGeneratedJsonFile() {

        var outputFilePath = Path.of(
                resourcesOutPath, SERIALIZED_TO_GENERATED_JSON_FILE_FROM_PREDEFINED_FILENAME);

        var args = new String[]{
                argument(Keys.INPUT, Inputs.DATABASE),
                argument(Keys.GENERATE_SERIALIZE, true),
                argument(Keys.GENERATE_SERIALIZE_FORMAT, Formats.JSON),
                argument(Keys.GENERATE_SERIALIZE_PATH, outputFilePath),
        };

        application.getArgumentsFinder().setArguments(args);
        assertFalse(() -> application.call().hasErrors());
    }

    @Order(11)
    @Test
    public void generateReceiptFromFileThenWriteToJsonFile() {

        var inputFilePath = Path.of(resourcesOutPath, SERIALIZED_TO_GENERATED_JSON_FILE_FROM_PREDEFINED_FILENAME);
        var outputFilePath = Path.of(resourcesOutPath, SERIALIZED_TO_JSON_FILE_FROM_GENERATED_FILENAME);

        var args = new String[]{
                argument(Keys.INPUT, Inputs.DESERIALIZE_GENERATE),
                argument(Keys.DESERIALIZE_GENERATE_FORMAT, Formats.JSON),
                argument(Keys.DESERIALIZE_GENERATE_PATH, inputFilePath),

                argument(Keys.SERIALIZE, true),
                argument(Keys.SERIALIZE_FORMAT, Formats.JSON),
                argument(Keys.SERIALIZE_PATH, outputFilePath),
        };

        application.getArgumentsFinder().setArguments(args);
        assertFalse(() -> application.call().hasErrors());
    }

    @Order(12)
    @Test
    public void generateReceiptFromJsonFile() {

        var inputFilePath = Path.of(resourcesOutPath, SERIALIZED_TO_GENERATED_JSON_FILE_FROM_PREDEFINED_FILENAME);

        var args = new String[]{
                argument(Keys.INPUT, Inputs.DESERIALIZE_GENERATE),
                argument(Keys.DESERIALIZE_GENERATE_FORMAT, Formats.JSON),
                argument(Keys.DESERIALIZE_GENERATE_PATH, inputFilePath)
        };

        application.getArgumentsFinder().setArguments(args);
        assertFalse(() -> application.call().hasErrors());
    }

    @Order(13)
    @Test
    public void readPreDefinedThenWriteAndPrintAndGenerate() {

        var m1 = Path.of(resourcesOutPath, "multi1");
        var m2 = Path.of(resourcesOutPath, "multi2");
        var m3 = Path.of(resourcesOutPath, "multi3");

        var args = new String[]{
                argument(Keys.INPUT, Inputs.DATABASE),

                argument(Keys.SERIALIZE, true),
                argument(Keys.SERIALIZE_FORMAT, Formats.JSON),
                argument(Keys.SERIALIZE_PATH, m1),

                argument(Keys.PRINT, true),
                argument(Keys.PRINT_FORMAT, Formats.HTML),
                argument(Keys.PRINT_PATH, m2),

                argument(Keys.GENERATE_SERIALIZE, true),
                argument(Keys.GENERATE_SERIALIZE_FORMAT, Formats.JSON),
                argument(Keys.GENERATE_SERIALIZE_PATH, m3)
        };

        application.getArgumentsFinder().setArguments(args);
        assertFalse(() -> application.call().hasErrors());
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
