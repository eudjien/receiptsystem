package ru.clevertec.checksystem.core.io.printer.template.pdf;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class FilePrintPdfTemplate extends PrintPdfTemplate {

    public FilePrintPdfTemplate(String templatePath) throws IOException {
        super(Files.readAllBytes(new File(templatePath).toPath()));
    }

    public FilePrintPdfTemplate(String templatePath, int topOffset) throws IOException {
        super(Files.readAllBytes(new File(templatePath).toPath()), topOffset);
    }
}
