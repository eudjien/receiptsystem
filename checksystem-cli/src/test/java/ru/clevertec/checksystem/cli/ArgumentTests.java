package ru.clevertec.checksystem.cli;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.checksystem.cli.argument.ArgumentsFinder;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.clevertec.checksystem.core.Constants.Format;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Transactional
@TestMethodOrder(OrderAnnotation.class)
class ArgumentTests {

    @Autowired
    private Application application;

    @Autowired
    private ArgumentsFinder argumentsFinder;

    private final static boolean deleteOutputDirAfterAll = true;

    private final static String SERIALIZED_TO_JSON_FILE_FROM_PREDEFINED_FILENAME = "serialized_from_predefined.json";
    private final static String SERIALIZED_TO_XML_FILE_FROM_JSON_FILE_FILENAME = "serialized_from_json_file.xml";
    private final static String SERIALIZED_TO_JSON_FILE_FROM_XML_FILE_FILENAME = "serialized_from_xml_file.json";
    private final static String PRINTED_TO_TEXT_FILE_FROM_JSON_FILE_FILENAME = "printed_from_json_file.txt";
    private final static String PRINTED_TO_HTML_FILE_FROM_JSON_FILE_FILENAME = "printed_from_json_file.html";
    private final static String PRINTED_TO_PDF_FILE_WITH_TEMPLATE_FROM_PREDEFINED_FILENAME = "printed_with_template_from_predefined.pdf";
    private final static String PRINTED_TO_PDF_FILE_FROM_JSON_FILE_FILENAME = "printed_from_json_file.pdf";
    private final static String PRINTED_TO_PDF_FILE_WITH_TEMPLATE_FROM_JSON_FILE_FILENAME = "printed_with_template_from_json_file.pdf";
    private final static String SERIALIZED_TO_GENERATED_JSON_FILE_FROM_PREDEFINED_FILENAME = "serialized_generated_from_predefined.json";
    private final static String SERIALIZED_TO_JSON_FILE_FROM_GENERATED_FILENAME = "serialized_from_generated.json";

    private static final String resourcesPath = Path.of("src", "test", "resources").toString();
    private static final String resourcesOutPath = Path.of(resourcesPath, "out").toString();

    @AfterAll
    public static void afterAll() {
        if (deleteOutputDirAfterAll)
            deleteOutputDir();
    }

    @BeforeEach
    public void beforeEach() {
        argumentsFinder.clearArguments();
    }

    @Order(1)
    @Test
    public void throwsWhenNoMode() {
        argumentsFinder.addArguments(new String[]{});
        assertThrows(IllegalArgumentException.class, () -> application.start(argumentsFinder));
    }

    @Order(2)
    @Test
    public void readPreDefinedThenWriteJsonFile() {

        var outputFilePath = Path.of(resourcesOutPath, SERIALIZED_TO_JSON_FILE_FROM_PREDEFINED_FILENAME);

        var args = new String[]{
                argument(Constants.Keys.INPUT, Constants.Inputs.DATABASE),
                argument(Constants.Keys.SERIALIZE, true),
                argument(Constants.Keys.SERIALIZE_FORMAT, Format.IO.JSON),
                argument(Constants.Keys.SERIALIZE_PATH, outputFilePath)
        };

        argumentsFinder.addArguments(args);
        assertDoesNotThrow(() -> application.start(argumentsFinder));
    }

    @Order(3)
    @Test
    public void readJsonFileThenWriteXmlFile() {

        var inputFilePath = Path.of(resourcesOutPath, SERIALIZED_TO_JSON_FILE_FROM_PREDEFINED_FILENAME);
        var outputFilePath = Path.of(resourcesOutPath, SERIALIZED_TO_XML_FILE_FROM_JSON_FILE_FILENAME);

        var args = new String[]{
                argument(Constants.Keys.INPUT, Constants.Inputs.DESERIALIZE),
                argument(Constants.Keys.DESERIALIZE_FORMAT, Format.IO.JSON),
                argument(Constants.Keys.DESERIALIZE_PATH, inputFilePath),
                argument(Constants.Keys.SERIALIZE, true),
                argument(Constants.Keys.SERIALIZE_FORMAT, Format.IO.XML),
                argument(Constants.Keys.SERIALIZE_PATH, outputFilePath),
        };

        argumentsFinder.addArguments(args);
        assertDoesNotThrow(() -> application.start(argumentsFinder));
    }

    @Order(4)
    @Test
    public void readXmlFileThenWriteJsonFile() {

        var inputFilePath = Path.of(resourcesOutPath, SERIALIZED_TO_XML_FILE_FROM_JSON_FILE_FILENAME);
        var outputFilePath = Path.of(resourcesOutPath, SERIALIZED_TO_JSON_FILE_FROM_XML_FILE_FILENAME);

        var args = new String[]{
                argument(Constants.Keys.INPUT, Constants.Inputs.DESERIALIZE),
                argument(Constants.Keys.DESERIALIZE_FORMAT, Format.IO.XML),
                argument(Constants.Keys.DESERIALIZE_PATH, inputFilePath),
                argument(Constants.Keys.SERIALIZE, true),
                argument(Constants.Keys.SERIALIZE_FORMAT, Format.IO.JSON),
                argument(Constants.Keys.SERIALIZE_PATH, outputFilePath),
        };

        argumentsFinder.addArguments(args);
        assertDoesNotThrow(() -> application.start(argumentsFinder));
    }

    @Order(5)
    @Test
    public void readJsonFileThenPrintToTextFile() {

        var inputFilePath = Path.of(resourcesOutPath, SERIALIZED_TO_JSON_FILE_FROM_PREDEFINED_FILENAME);
        var outputFilePath = Path.of(resourcesOutPath, PRINTED_TO_TEXT_FILE_FROM_JSON_FILE_FILENAME);

        var args = new String[]{
                argument(Constants.Keys.INPUT, Constants.Inputs.DESERIALIZE),
                argument(Constants.Keys.DESERIALIZE_FORMAT, Format.IO.JSON),
                argument(Constants.Keys.DESERIALIZE_PATH, inputFilePath),
                argument(Constants.Keys.PRINT, true),
                argument(Constants.Keys.PRINT_FORMAT, Format.Print.TEXT),
                argument(Constants.Keys.PRINT_PATH, outputFilePath),
        };

        argumentsFinder.addArguments(args);
        assertDoesNotThrow(() -> application.start(argumentsFinder));
    }

    @Order(6)
    @Test
    public void readJsonFileThenPrintToHtmlFile() {

        var inputFilePath = Path.of(resourcesOutPath, SERIALIZED_TO_JSON_FILE_FROM_PREDEFINED_FILENAME);
        var outputFilePath = Path.of(resourcesOutPath, PRINTED_TO_HTML_FILE_FROM_JSON_FILE_FILENAME);

        var args = new String[]{
                argument(Constants.Keys.INPUT, Constants.Inputs.DESERIALIZE),
                argument(Constants.Keys.DESERIALIZE_FORMAT, Format.IO.JSON),
                argument(Constants.Keys.DESERIALIZE_PATH, inputFilePath),
                argument(Constants.Keys.PRINT, true),
                argument(Constants.Keys.PRINT_FORMAT, Format.Print.HTML),
                argument(Constants.Keys.PRINT_PATH, outputFilePath),
        };

        argumentsFinder.addArguments(args);
        assertDoesNotThrow(() -> application.start(argumentsFinder));
    }

    @Order(7)
    @Test
    public void readPreDefinedThenPrintToPdfFileWithTemplate() {

        final int templateOffset = 100;

        var templateFilePath = Path.of(resourcesPath, "Clevertec_Template.pdf");
        var outputFilePath =
                Path.of(resourcesOutPath, PRINTED_TO_PDF_FILE_WITH_TEMPLATE_FROM_PREDEFINED_FILENAME);

        var args = new String[]{
                argument(Constants.Keys.INPUT, Constants.Inputs.DATABASE),
                argument(Constants.Keys.PRINT, true),
                argument(Constants.Keys.PRINT_FORMAT, Format.Print.PDF),
                argument(Constants.Keys.PRINT_PATH, outputFilePath),
                argument(Constants.Keys.PRINT_PDF_TEMPLATE, true),
                argument(Constants.Keys.PRINT_PDF_TEMPLATE_PATH, templateFilePath),
                argument(Constants.Keys.PRINT_PDF_TEMPLATE_OFFSET, templateOffset),
        };

        argumentsFinder.addArguments(args);
        assertDoesNotThrow(() -> application.start(argumentsFinder));
    }

    @Order(8)
    @Test
    public void readJsonFileThenPrintToPdfFile() {

        var inputFilePath = Path.of(resourcesOutPath, SERIALIZED_TO_JSON_FILE_FROM_PREDEFINED_FILENAME);
        var outputFilePath = Path.of(resourcesOutPath, PRINTED_TO_PDF_FILE_FROM_JSON_FILE_FILENAME);

        var args = new String[]{
                argument(Constants.Keys.INPUT, Constants.Inputs.DESERIALIZE),
                argument(Constants.Keys.DESERIALIZE_FORMAT, Format.IO.JSON),
                argument(Constants.Keys.DESERIALIZE_PATH, inputFilePath),
                argument(Constants.Keys.PRINT, true),
                argument(Constants.Keys.PRINT_FORMAT, Format.Print.PDF),
                argument(Constants.Keys.PRINT_PATH, outputFilePath),
        };

        argumentsFinder.addArguments(args);
        assertDoesNotThrow(() -> application.start(argumentsFinder));
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
                argument(Constants.Keys.INPUT, Constants.Inputs.DESERIALIZE),
                argument(Constants.Keys.DESERIALIZE_FORMAT, Format.IO.JSON),
                argument(Constants.Keys.DESERIALIZE_PATH, inputFilePath),
                argument(Constants.Keys.PRINT, true),
                argument(Constants.Keys.PRINT_FORMAT, Format.Print.PDF),
                argument(Constants.Keys.PRINT_PATH, outputFilePath),
                argument(Constants.Keys.PRINT_PDF_TEMPLATE, true),
                argument(Constants.Keys.PRINT_PDF_TEMPLATE_PATH, templateFilePath),
                argument(Constants.Keys.PRINT_PDF_TEMPLATE_OFFSET, templateOffset),
        };

        argumentsFinder.addArguments(args);
        assertDoesNotThrow(() -> application.start(argumentsFinder));
    }

    @Order(10)
    @Test
    public void readPreDefinedThenWriteToGeneratedJsonFile() {

        var outputFilePath =
                Path.of(resourcesOutPath, SERIALIZED_TO_GENERATED_JSON_FILE_FROM_PREDEFINED_FILENAME);

        var args = new String[]{
                argument(Constants.Keys.INPUT, Constants.Inputs.DATABASE),
                argument(Constants.Keys.GENERATE_SERIALIZE, true),
                argument(Constants.Keys.GENERATE_SERIALIZE_FORMAT, Format.IO.JSON),
                argument(Constants.Keys.GENERATE_SERIALIZE_PATH, outputFilePath),
        };

        argumentsFinder.addArguments(args);
        assertDoesNotThrow(() -> application.start(argumentsFinder));
    }

    @Order(11)
    @Test
    public void generateCheckFromFileThenWriteToJsonFile() {

        var inputFilePath = Path.of(resourcesOutPath, SERIALIZED_TO_GENERATED_JSON_FILE_FROM_PREDEFINED_FILENAME);
        var outputFilePath = Path.of(resourcesOutPath, SERIALIZED_TO_JSON_FILE_FROM_GENERATED_FILENAME);

        var args = new String[]{
                argument(Constants.Keys.INPUT, Constants.Inputs.DESERIALIZE_GENERATE),
                argument(Constants.Keys.DESERIALIZE_GENERATE_FORMAT, Format.IO.JSON),
                argument(Constants.Keys.DESERIALIZE_GENERATE_PATH, inputFilePath),

                argument(Constants.Keys.SERIALIZE, true),
                argument(Constants.Keys.SERIALIZE_FORMAT, Format.IO.JSON),
                argument(Constants.Keys.SERIALIZE_PATH, outputFilePath),
        };

        argumentsFinder.addArguments(args);
        assertDoesNotThrow(() -> application.start(argumentsFinder));
    }

    @Order(12)
    @Test
    public void generateCheckFromJsonFile() {

        var inputFilePath = Path.of(resourcesOutPath, SERIALIZED_TO_GENERATED_JSON_FILE_FROM_PREDEFINED_FILENAME);

        var args = new String[]{
                argument(Constants.Keys.INPUT, Constants.Inputs.DESERIALIZE_GENERATE),
                argument(Constants.Keys.DESERIALIZE_GENERATE_FORMAT, Format.IO.JSON),
                argument(Constants.Keys.DESERIALIZE_GENERATE_PATH, inputFilePath)
        };

        argumentsFinder.addArguments(args);
        assertDoesNotThrow(() -> application.start(argumentsFinder));
    }

    @Order(13)
    @Test
    public void readPreDefinedThenWriteAndPrintAndGenerate() {

        var m1 = Path.of(resourcesOutPath, "multi1");
        var m2 = Path.of(resourcesOutPath, "multi2");
        var m3 = Path.of(resourcesOutPath, "multi3");

        var args = new String[]{
                argument(Constants.Keys.INPUT, Constants.Inputs.DATABASE),

                argument(Constants.Keys.SERIALIZE, true),
                argument(Constants.Keys.SERIALIZE_FORMAT, Format.IO.JSON),
                argument(Constants.Keys.SERIALIZE_PATH, m1),

                argument(Constants.Keys.PRINT, true),
                argument(Constants.Keys.PRINT_FORMAT, Format.Print.HTML),
                argument(Constants.Keys.PRINT_PATH, m2),

                argument(Constants.Keys.GENERATE_SERIALIZE, true),
                argument(Constants.Keys.GENERATE_SERIALIZE_FORMAT, Format.IO.JSON),
                argument(Constants.Keys.GENERATE_SERIALIZE_PATH, m3)
        };

        argumentsFinder.addArguments(args);
        assertDoesNotThrow(() -> application.start(argumentsFinder));
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
