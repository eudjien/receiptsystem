package ru.clevertec.checksystem.core.io.printer.template.pdf;

import com.itextpdf.kernel.pdf.PdfReader;

import java.io.ByteArrayInputStream;
import java.io.IOException;

public abstract class PrintPdfTemplate {

    private final byte[] templateBytes;
    private int topOffset = 0;

    protected PrintPdfTemplate(byte[] templateBytes) throws IOException {
        throwIfIsNotPdf(templateBytes);
        this.templateBytes = templateBytes;
    }

    protected PrintPdfTemplate(byte[] templateBytes, int topOffset) throws IOException {
        throwIfIsNotPdf(templateBytes);
        this.templateBytes = templateBytes;
        setTopOffset(topOffset);
    }

    public byte[] getBytes() {
        return templateBytes;
    }

    public byte[] getTemplateBytes() {
        return templateBytes;
    }

    public int getTopOffset() {
        return topOffset;
    }

    public void setTopOffset(int topOffset) {
        if (topOffset < 0) {
            throw new IllegalArgumentException("Top offset cannot be less than 0.");
        }
        this.topOffset = topOffset;
    }

    private void throwIfIsNotPdf(byte[] pdfBytes) throws IOException {
        new PdfReader(new ByteArrayInputStream(pdfBytes));
    }
}
