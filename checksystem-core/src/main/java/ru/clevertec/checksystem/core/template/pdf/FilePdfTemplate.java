package ru.clevertec.checksystem.core.template.pdf;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class FilePdfTemplate extends PdfTemplate {

    private File file;

    public FilePdfTemplate(File templateFile) {
        this(templateFile, 0);
    }

    public FilePdfTemplate(File templateFile, int topOffset) {
        super(topOffset);
        setFile(templateFile);
    }

    @Override
    public InputStream getInputStream() throws FileNotFoundException {
        return new FileInputStream(file);
    }

    public File getFile() {
        return file;
    }

    public void setFile(File templateFile) {
        if (templateFile == null) {
            throw new IllegalArgumentException("Argument 'templateFile' cannot be null");
        }
        this.file = templateFile;
    }
}
