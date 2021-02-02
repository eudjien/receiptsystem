package ru.clevertec.checksystem.core.template.pdf;

import ru.clevertec.checksystem.core.exception.ArgumentNullException;
import ru.clevertec.checksystem.core.util.ThrowUtils;

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

    public void setFile(File templateFile) throws ArgumentNullException {
        ThrowUtils.Argument.theNull("templateFile", templateFile);
        this.file = templateFile;
    }
}
