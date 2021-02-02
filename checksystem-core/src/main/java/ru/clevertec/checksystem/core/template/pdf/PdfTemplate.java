package ru.clevertec.checksystem.core.template.pdf;

import ru.clevertec.checksystem.core.exception.ArgumentOutOfRangeException;
import ru.clevertec.checksystem.core.util.ThrowUtils;

public abstract class PdfTemplate implements IPdfTemplate {

    private static final int MIN_TOP_OFFSET = 0;

    private int topOffset = 0;

    protected PdfTemplate(int topOffset) {
        setTopOffset(topOffset);
    }

    @Override
    public int getTopOffset() {
        return topOffset;
    }

    @Override
    public void setTopOffset(int topOffset) throws ArgumentOutOfRangeException {
        ThrowUtils.Argument.lessThan("topOffset", topOffset, MIN_TOP_OFFSET);
        this.topOffset = topOffset;
    }
}
