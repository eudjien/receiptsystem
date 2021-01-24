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

    final String[] generateCheckArgs = new String[]{
            argument(Constants.Keys.MODE, Constants.Mode.GENERATE),
            argument(Constants.Keys.GENERATE_DESERIALIZE_SOURCE, Constants.Source.DATA),
            argument(Constants.Keys.GENERATE_DESERIALIZE_FORMAT, Constants.Format.IO.JSON),
            argument(Constants.Keys.GENERATE_DESERIALIZE_DATA, "[{\"id\":1,\"name\":\"999 проблем\",\"description\":\"Компьютерный магазин\",\"address\":\"ул. Пушкина, д. Калатушкина\",\"cashier\":\"+375290000000\",\"phoneNumber\":\"Василий Пупкин\",\"date\":1611537871405,\"discountIds\":[3],\"items\":[{\"productId\":1,\"quantity\":3,\"discountIds\":[]},{\"productId\":2,\"quantity\":1,\"discountIds\":[]},{\"productId\":3,\"quantity\":8,\"discountIds\":[5]},{\"productId\":4,\"quantity\":9,\"discountIds\":[]},{\"productId\":5,\"quantity\":1,\"discountIds\":[]},{\"productId\":6,\"quantity\":2,\"discountIds\":[]}]},{\"id\":2,\"name\":\"342 элемент\",\"description\":\"Гипермаркет\",\"address\":\"ул. Элементова, д. 1\",\"cashier\":\"+375290000001\",\"phoneNumber\":\"Екатерина Пупкина\",\"date\":1611537871406,\"discountIds\":[1],\"items\":[{\"productId\":3,\"quantity\":8,\"discountIds\":[6,2]},{\"productId\":2,\"quantity\":10,\"discountIds\":[]},{\"productId\":4,\"quantity\":9,\"discountIds\":[]},{\"productId\":3,\"quantity\":8,\"discountIds\":[]},{\"productId\":6,\"quantity\":7,\"discountIds\":[3]},{\"productId\":8,\"quantity\":6,\"discountIds\":[]},{\"productId\":10,\"quantity\":5,\"discountIds\":[]}]},{\"id\":3,\"name\":\"Магазин №1\",\"description\":\"Продуктовый магазин\",\"address\":\"ул. Советская, д. 1001\",\"cashier\":\"+375290000002\",\"phoneNumber\":\"Алексей Пупкин\",\"date\":1611537871406,\"discountIds\":[2],\"items\":[{\"productId\":3,\"quantity\":15,\"discountIds\":[]},{\"productId\":5,\"quantity\":1,\"discountIds\":[]},{\"productId\":7,\"quantity\":1,\"discountIds\":[]},{\"productId\":9,\"quantity\":3,\"discountIds\":[]},{\"productId\":11,\"quantity\":11,\"discountIds\":[]},{\"productId\":13,\"quantity\":6,\"discountIds\":[]}]},{\"id\":4,\"name\":\"Магазин №2\",\"description\":\"Продуктовый магазин\",\"address\":\"ул. Свиридова, д. 1234\",\"cashier\":\"+375290000003\",\"phoneNumber\":\"Татьяна Пупкина\",\"date\":1611537871407,\"discountIds\":[4],\"items\":[{\"productId\":4,\"quantity\":15,\"discountIds\":[8]},{\"productId\":6,\"quantity\":1,\"discountIds\":[]},{\"productId\":8,\"quantity\":1,\"discountIds\":[]},{\"productId\":10,\"quantity\":3,\"discountIds\":[]},{\"productId\":12,\"quantity\":15,\"discountIds\":[1]},{\"productId\":14,\"quantity\":6,\"discountIds\":[]},{\"productId\":16,\"quantity\":3,\"discountIds\":[]},{\"productId\":18,\"quantity\":11,\"discountIds\":[]},{\"productId\":20,\"quantity\":6,\"discountIds\":[]}]}]")
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
                argument(Constants.Keys.MODE, Constants.Mode.FILE_DESERIALIZE),
                argument(Constants.Keys.FILE_DESERIALIZE_FORMAT, Constants.Format.IO.JSON),
                argument(Constants.Keys.FILE_DESERIALIZE_PATH, inputFilePath),
                argument(Constants.Keys.FILE_SERIALIZE, true),
                argument(Constants.Keys.FILE_SERIALIZE_FORMAT, Constants.Format.IO.XML),
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
                argument(Constants.Keys.MODE, Constants.Mode.FILE_DESERIALIZE),
                argument(Constants.Keys.FILE_DESERIALIZE_FORMAT, Constants.Format.IO.XML),
                argument(Constants.Keys.FILE_DESERIALIZE_PATH, inputFilePath),
                argument(Constants.Keys.FILE_SERIALIZE, true),
                argument(Constants.Keys.FILE_SERIALIZE_FORMAT, Constants.Format.IO.JSON),
                argument(Constants.Keys.FILE_SERIALIZE_PATH, outputFilePath),
        };

        assertDoesNotThrow(() -> Main.main(args));
    }

    @Test
    public void generateCheckGoesWell() {
        assertDoesNotThrow(() -> Main.main(generateCheckArgs));
    }

    @Test
    public void generateCheckFromFileGoesWell() {

        var absPath = Paths.get(resourcesPath).toAbsolutePath();
        var inputFilePath = Path.of(absPath.toString(), "generated_checks.json");

        final String[] generateCheckArgs = new String[]{
                argument(Constants.Keys.MODE, Constants.Mode.GENERATE),
                argument(Constants.Keys.GENERATE_DESERIALIZE_SOURCE, Constants.Source.FILE),
                argument(Constants.Keys.GENERATE_DESERIALIZE_FORMAT, Constants.Format.IO.JSON),
                argument(Constants.Keys.GENERATE_DESERIALIZE_DATA, inputFilePath)
        };

        assertDoesNotThrow(() -> Main.main(generateCheckArgs));
    }

    @Test
    public void generateCheckThenWriteToJsonFileGoesWell() {

        var absPath = Paths.get(resourcesPath).toAbsolutePath();
        var outputFilePath = Path.of(absPath.toString(), outFolder, "[Serialized]_From_Generated.xml");

        var serializeArgs = new String[]{
                argument(Constants.Keys.FILE_SERIALIZE, true),
                argument(Constants.Keys.FILE_SERIALIZE_FORMAT, Constants.Format.IO.JSON),
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
                argument(Constants.Keys.MODE, Constants.Mode.FILE_DESERIALIZE),
                argument(Constants.Keys.FILE_DESERIALIZE_FORMAT, Constants.Format.IO.JSON),
                argument(Constants.Keys.FILE_DESERIALIZE_PATH, inputFilePath),
                argument(Constants.Keys.FILE_PRINT, true),
                argument(Constants.Keys.FILE_PRINT_FORMAT, Constants.Format.Print.TEXT),
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
                argument(Constants.Keys.MODE, Constants.Mode.FILE_DESERIALIZE),
                argument(Constants.Keys.FILE_DESERIALIZE_FORMAT, Constants.Format.IO.JSON),
                argument(Constants.Keys.FILE_DESERIALIZE_PATH, inputFilePath),
                argument(Constants.Keys.FILE_PRINT, true),
                argument(Constants.Keys.FILE_PRINT_FORMAT, Constants.Format.Print.HTML),
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
                argument(Constants.Keys.MODE, Constants.Mode.FILE_DESERIALIZE),
                argument(Constants.Keys.FILE_DESERIALIZE_FORMAT, Constants.Format.IO.JSON),
                argument(Constants.Keys.FILE_DESERIALIZE_PATH, inputFilePath),
                argument(Constants.Keys.FILE_PRINT, true),
                argument(Constants.Keys.FILE_PRINT_FORMAT, Constants.Format.Print.PDF),
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
