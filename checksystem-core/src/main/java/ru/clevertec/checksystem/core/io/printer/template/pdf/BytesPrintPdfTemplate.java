package ru.clevertec.checksystem.core.io.printer.template.pdf;

import java.io.IOException;

public class BytesPrintPdfTemplate extends PrintPdfTemplate {

    public BytesPrintPdfTemplate(byte[] templateBytes) throws IOException {
        super(templateBytes);
    }

    public BytesPrintPdfTemplate(byte[] templateBytes, int topOffset) throws IOException {
        super(templateBytes, topOffset);
    }
}
