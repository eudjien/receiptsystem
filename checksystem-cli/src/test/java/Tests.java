import org.junit.jupiter.api.Test;
import ru.clevertec.checksystem.cli.Constants;
import ru.clevertec.checksystem.cli.Main;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class Tests {
    static final String[] generateCheckArgs = new String[]{
            "-mode=generate",
            "-id=1",
            "-name=test name",
            "-description=test description",
            "-address=address address",
            "-cashier=cashier address",
            "-phoneNumber=phoneNumber test",
            "-date=10.10.2010",
            "-ci=1:1",
            "-ci=2:1",
            "-ci=3:1",
            "-ci=2:5",
            "-ci=5:1",
            "-ci=6:8",
            "-ci=7:15",
            "-ci=8:67",
            "-ci=9:3",
            "-ci=10:1",
            "-ci=11:1",
            "-ci=12:1",
            "-ci=13:4",
            "-ci=14:2",
            "d-check=1",
            "d-item=6:5",
    };
    static String resourcesPath = "src/test/resources";
    static String outFolder = "out";

    @Test
    public void readJsonFileThenWriteXmlFileGoesWell() throws IOException {

        var absPath = Paths.get(resourcesPath).toAbsolutePath();

        var inputFilePath = Path.of(absPath.toString(), "checks.json");
        var outputFilePath = Path.of(absPath.toString(), outFolder, "checks_convFromJson.xml");

        var args = new String[]{
                "-mode=file-deserialize",
                "-file-deserialize=true",
                "-file-deserialize-format=json",
                "-file-deserialize-path=" + inputFilePath,
                "-file-serialize=true",
                "-file-serialize-format=xml",
                "-file-serialize-path=" + outputFilePath
        };

        assertDoesNotThrow(() -> Main.main(args));
        //Files.delete(outputFilePath);
    }

    @Test
    public void readXmlFileThenWriteJsonFileGoesWell() throws IOException {

        var absPath = Paths.get(resourcesPath).toAbsolutePath();

        var inputFilePath = Path.of(absPath.toString(), "checks.xml");
        var outputFilePath = Path.of(absPath.toString(), outFolder, "checks_convFromXml.json");

        var args = new String[]{
                "-mode=file-deserialize",
                "-file-deserialize=true",
                "-file-deserialize-format=xml",
                "-file-deserialize-path=" + inputFilePath,
                "-file-serialize=true",
                "-file-serialize-format=json",
                "-file-serialize-path=" + outputFilePath
        };

        assertDoesNotThrow(() -> Main.main(args));
        //Files.delete(outputFilePath);
    }

    @Test
    public void generateCheckGoesWell() throws IOException {
        assertDoesNotThrow(() -> Main.main(generateCheckArgs));
    }

    @Test
    public void generateCheckThenWriteToJsonFileGoesWell() throws IOException {

        var absPath = Paths.get(resourcesPath).toAbsolutePath();
        var outputFilePath = Path.of(absPath.toString(), outFolder, "checks_convFromGenerated.xml");

        var serializeArgs = new String[]{
                "-file-serialize=true",
                "-file-serialize-format=json",
                "-file-serialize-path=" + outputFilePath
        };

        var args = Stream.concat(
                Arrays.stream(generateCheckArgs),
                Arrays.stream(serializeArgs))
                .toArray(String[]::new);

        assertDoesNotThrow(() -> Main.main(args));
        //Files.delete(outputFilePath);
    }

    @Test
    public void readJsonFileThenPrintToTextFileGoesWell() throws IOException {

        var absPath = Paths.get(resourcesPath).toAbsolutePath();

        var inputFilePath = Path.of(absPath.toString(), "checks.json");
        var outputFilePath = Path.of(absPath.toString(), outFolder, "checks_printed.txt");

        var args = new String[]{
                "-mode=file-deserialize",
                "-file-deserialize=true",
                "-file-deserialize-format=json",
                "-file-deserialize-path=" + inputFilePath,
                "-file-print=true",
                "-file-print-format=text",
                "-file-print-path=" + outputFilePath
        };

        assertDoesNotThrow(() -> Main.main(args));
        //Files.delete(outputFilePath);
    }

    @Test
    public void readJsonFileThenPrintToHtmlFileGoesWell() throws IOException {

        var absPath = Paths.get(resourcesPath).toAbsolutePath();

        var inputFilePath = Path.of(absPath.toString(), "checks.json");
        var outputFilePath = Path.of(absPath.toString(), outFolder, "checks_printed.html");

        var args = new String[]{
                "-mode=file-deserialize",
                "-file-deserialize=true",
                "-file-deserialize-format=json",
                "-file-deserialize-path=" + inputFilePath,
                "-file-print=true",
                "-file-print-format=html",
                "-file-print-path=" + outputFilePath
        };

        assertDoesNotThrow(() -> Main.main(args));
        //Files.delete(outputFilePath);
    }

    @Test
    public void readJsonFileThenPrintToPdfFileGoesWell() throws IOException {

        var absPath = Paths.get(resourcesPath).toAbsolutePath();

        var inputFilePath = Path.of(absPath.toString(), "checks.json");
        var outputFilePath = Path.of(absPath.toString(), outFolder, "checks_printed.pdf");

        var args = new String[]{
                "-mode=file-deserialize",
                "-file-deserialize=true",
                "-" + Constants.PARAM_KEY_FILE_DESERIALIZE_FORMAT + "=json",
                "-" + Constants.PARAM_KEY_FILE_DESERIALIZE_PATH + "=" + inputFilePath,
                "-file-print=true",
                "-" + Constants.PARAM_KEY_FILE_PRINT_FORMAT + "=pdf",
                "-" + Constants.PARAM_KEY_FILE_PRINT_PATH + "=" + outputFilePath,
        };

        assertDoesNotThrow(() -> Main.main(args));
        //Files.delete(outputFilePath);
    }

    @Test
    public void readJsonFileThenPrintToPdfFileWithTemplateGoesWell() throws IOException {

        var absPath = Paths.get(resourcesPath).toAbsolutePath();

        var inputFilePath = Path.of(absPath.toString(), "checks.json");
        var templateFilePath = Path.of(absPath.toString(), "Clevertec_Template.pdf");
        var outputFilePath = Path.of(absPath.toString(), outFolder, "checks_templated_printed.pdf");

        var args = new String[]{
                "-mode=file-deserialize",
                "-file-deserialize=true",
                "-" + Constants.PARAM_KEY_FILE_DESERIALIZE_FORMAT + "=json",
                "-" + Constants.PARAM_KEY_FILE_DESERIALIZE_PATH + "=" + inputFilePath,
                "-file-print=true",
                "-" + Constants.PARAM_KEY_FILE_PRINT_FORMAT + "=pdf",
                "-" + Constants.PARAM_KEY_FILE_PRINT_PATH + "=" + outputFilePath,
                "-" + Constants.PARAM_KEY_FILE_PRINT_PDF_TEMPLATE + "=" + templateFilePath + "|94"
        };

        assertDoesNotThrow(() -> Main.main(args));
        //Files.delete(outputFilePath);
    }
}
