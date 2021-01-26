package ru.clevertec.checksystem.core.template.pdf;

public abstract class PdfTemplate implements IPdfTemplate {

    private int topOffset = 0;

    protected PdfTemplate(int topOffset) {
        setTopOffset(topOffset);
    }

    @Override
    public int getTopOffset() {
        return topOffset;
    }

    @Override
    public void setTopOffset(int topOffset) {
        if (topOffset < 0) {
            throw new IllegalArgumentException("Top offset cannot be less than 0.");
        }
        this.topOffset = topOffset;
    }
}
