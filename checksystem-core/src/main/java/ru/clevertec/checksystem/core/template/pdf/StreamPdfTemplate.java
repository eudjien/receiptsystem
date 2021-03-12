package ru.clevertec.checksystem.core.template.pdf;

import ru.clevertec.checksystem.core.util.ThrowUtils;

import java.io.InputStream;

public class StreamPdfTemplate extends AbstractPdfTemplate {

    private final InputStream templateInputStream;

    public StreamPdfTemplate(InputStream templateInputStream) {
        this(templateInputStream, 0);
    }

    public StreamPdfTemplate(InputStream templateInputStream, long topOffset) {
        super(topOffset);
        ThrowUtils.Argument.nullValue("templateInputStream", templateInputStream);
        this.templateInputStream = templateInputStream;
    }

    @Override
    public InputStream getInputStream() {
        return templateInputStream;
    }
}
