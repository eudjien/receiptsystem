package ru.clevertec.checksystem.core.template.pdf;

import java.io.InputStream;

public class StreamPdfTemplate extends AbstractPdfTemplate {

    private InputStream templateInputStream;

    public StreamPdfTemplate(InputStream templateInputStream) {
        super(0);
    }

    public StreamPdfTemplate(InputStream templateInputStream, int topOffset) {
        super(topOffset);
    }

    @Override
    public InputStream getInputStream() {
        return templateInputStream;
    }
}
