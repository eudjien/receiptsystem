package ru.clevertec.checksystem.cli;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ApplicationTests {

    final static boolean deleteOutputDirAfterAll = true;

    static final String[] generateCheckArgs = new String[]{
            "-mode=generate",
            "-id=1", "-name=test name", "-description=test description", "-address=address address",
            "-cashier=cashier address", "-phoneNumber=phoneNumber test", "-date=10.10.2010",
            "-ci=1:1", "-ci=2:1", "-ci=3:1", "-ci=2:5", "-ci=5:1", "-ci=6:8", "-ci=7:15", "-ci=8:67",
            "-ci=9:3", "-ci=10:1", "-ci=11:1", "-ci=12:1", "-ci=13:4", "-ci=14:2", "d-check=1", "d-item=6:5",
    };

    static String resourcesPath = "../checksystem-core/src/test/resources";
    static String outFolder = "out";

    @AfterAll
    public static void afterAll() {
        if (deleteOutputDirAfterAll) {
            deleteOutputDir();
        }
    }

    @Test
    public void throwsWhenNoMode() {
        assertThrows(IllegalArgumentException.class, () -> Main.main(new String[]{}));
    }

    @Test
    public void readJsonFileThenWriteXmlFileGoesWell() {

        var absPath = Paths.get(resourcesPath).toAbsolutePath();

        var inputFilePath = Path.of(absPath.toString(), "checks.json");
        var outputFilePath = Path.of(absPath.toString(), outFolder, "[Serialized]_From_JSON.xml");

        var args = new String[]{
                argument(Constants.Keys.MODE, Constants.Modes.FILE_DESERIALIZE),
                argument(Constants.Keys.FILE_DESERIALIZE_FORMAT, Constants.Formats.IO.JSON),
                argument(Constants.Keys.FILE_DESERIALIZE_PATH, inputFilePath),
                argument(Constants.Keys.FILE_SERIALIZE, true),
                argument(Constants.Keys.FILE_SERIALIZE_FORMAT, Constants.Formats.IO.XML),
                argument(Constants.Keys.FILE_SERIALIZE_PATH, outputFilePath),
        };

        assertDoesNotThrow(() -> Main.main(args));
    }

    @Test
    public void readXmlFileThenWriteJsonFileGoesWell() {

        var absPath = Paths.get(resourcesPath).toAbsolutePath();

        var inputFilePath = Path.of(absPath.toString(), "checks.xml");
        var outputFilePath = Path.of(absPath.toString(), outFolder, "[Serialized]_From_XML.json");

        var args = new String[]{
                argument(Constants.Keys.MODE, Constants.Modes.FILE_DESERIALIZE),
                argument(Constants.Keys.FILE_DESERIALIZE_FORMAT, Constants.Formats.IO.XML),
                argument(Constants.Keys.FILE_DESERIALIZE_PATH, inputFilePath),
                argument(Constants.Keys.FILE_SERIALIZE, true),
                argument(Constants.Keys.FILE_SERIALIZE_FORMAT, Constants.Formats.IO.JSON),
                argument(Constants.Keys.FILE_SERIALIZE_PATH, outputFilePath),
        };

        assertDoesNotThrow(() -> Main.main(args));
    }

    @Test
    public void generateCheckGoesWell() {
        assertDoesNotThrow(() -> Main.main(generateCheckArgs));
    }

    @Test
    public void generateCheckThenWriteToJsonFileGoesWell() {

        var absPath = Paths.get(resourcesPath).toAbsolutePath();
        var outputFilePath = Path.of(absPath.toString(), outFolder, "[Serialized]_From_Generated.xml");

        var serializeArgs = new String[]{
                argument(Constants.Keys.FILE_SERIALIZE, true),
                argument(Constants.Keys.FILE_SERIALIZE_FORMAT, Constants.Formats.IO.JSON),
                argument(Constants.Keys.FILE_SERIALIZE_PATH, outputFilePath),
        };

        var args = Stream.concat(
                Arrays.stream(generateCheckArgs),
                Arrays.stream(serializeArgs))
                .toArray(String[]::new);

        assertDoesNotThrow(() -> Main.main(args));
    }

    @Test
    public void readJsonFileThenPrintToTextFileGoesWell() {

        var absPath = Paths.get(resourcesPath).toAbsolutePath();

        var inputFilePath = Path.of(absPath.toString(), "checks.json");
        var outputFilePath = Path.of(absPath.toString(), outFolder, "[Printed]_Simple.txt");

        var args = new String[]{
                argument(Constants.Keys.MODE, Constants.Modes.FILE_DESERIALIZE),
                argument(Constants.Keys.FILE_DESERIALIZE_FORMAT, Constants.Formats.IO.JSON),
                argument(Constants.Keys.FILE_DESERIALIZE_PATH, inputFilePath),
                argument(Constants.Keys.FILE_PRINT, true),
                argument(Constants.Keys.FILE_PRINT_FORMAT, Constants.Formats.Print.TEXT),
                argument(Constants.Keys.FILE_PRINT_PATH, outputFilePath),
        };

        assertDoesNotThrow(() -> Main.main(args));
    }

    @Test
    public void readJsonFileThenPrintToHtmlFileGoesWell() {

        var absPath = Paths.get(resourcesPath).toAbsolutePath();

        var inputFilePath = Path.of(absPath.toString(), "checks.json");
        var outputFilePath = Path.of(absPath.toString(), outFolder, "[Printed]_Simple.html");

        var args = new String[]{
                argument(Constants.Keys.MODE, Constants.Modes.FILE_DESERIALIZE),
                argument(Constants.Keys.FILE_DESERIALIZE_FORMAT, Constants.Formats.IO.JSON),
                argument(Constants.Keys.FILE_DESERIALIZE_PATH, inputFilePath),
                argument(Constants.Keys.FILE_PRINT, true),
                argument(Constants.Keys.FILE_PRINT_FORMAT, Constants.Formats.Print.HTML),
                argument(Constants.Keys.FILE_PRINT_PATH, outputFilePath),
        };

        assertDoesNotThrow(() -> Main.main(args));
    }

    @Test
    public void readJsonFileThenPrintToPdfFileGoesWell() {

        var absPath = Paths.get(resourcesPath).toAbsolutePath();

        var inputFilePath = Path.of(absPath.toString(), "checks.json");
        var outputFilePath = Path.of(absPath.toString(), outFolder, "[Printed]_Simple.pdf");

        var args = new String[]{
                argument(Constants.Keys.MODE, Constants.Modes.FILE_DESERIALIZE),
                argument(Constants.Keys.FILE_DESERIALIZE_FORMAT, Constants.Formats.IO.JSON),
                argument(Constants.Keys.FILE_DESERIALIZE_PATH, inputFilePath),
                argument(Constants.Keys.FILE_PRINT, true),
                argument(Constants.Keys.FILE_PRINT_FORMAT, Constants.Formats.Print.PDF),
                argument(Constants.Keys.FILE_PRINT_PATH, outputFilePath),
        };

        assertDoesNotThrow(() -> Main.main(args));
    }

    @Test
    public void readJsonFileThenPrintToPdfFileWithTemplateGoesWell() {

        final int templateOffset = 94;

        var absPath = Paths.get(resourcesPath).toAbsolutePath();

        var inputFilePath = Path.of(absPath.toString(), "checks.json");
        var templateFilePath = Path.of(absPath.toString(), "Clevertec_Template.pdf");
        var outputFilePath = Path.of(absPath.toString(), outFolder, "[Printed]_With_Template.pdf");

        var args = new String[]{
                argument(Constants.Keys.MODE, Constants.Modes.FILE_DESERIALIZE),
                argument(Constants.Keys.FILE_DESERIALIZE_FORMAT, Constants.Formats.IO.JSON),
                argument(Constants.Keys.FILE_DESERIALIZE_PATH, inputFilePath),
                argument(Constants.Keys.FILE_PRINT, true),
                argument(Constants.Keys.FILE_PRINT_FORMAT, Constants.Formats.Print.PDF),
                argument(Constants.Keys.FILE_PRINT_PATH, outputFilePath),
                argument(Constants.Keys.FILE_PRINT_PDF_TEMPLATE, true),
                argument(Constants.Keys.FILE_PRINT_PDF_TEMPLATE_PATH, templateFilePath),
                argument(Constants.Keys.FILE_PRINT_PDF_TEMPLATE_OFFSET, templateOffset),
        };

        assertDoesNotThrow(() -> Main.main(args));
    }

    private String argument(String key, Object value) {
        return "-" + key + "=" + value;
    }

    private static void deleteOutputDir() {
        var dir = Paths.get(resourcesPath, outFolder).toFile();
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
