package ru.clevertec.checksystem.core.template.pdf;

import ru.clevertec.checksystem.core.util.ThrowUtils;

public abstract class AbstractPdfTemplate implements IPdfTemplate {

    private static final int MIN_TOP_OFFSET = 0;

    private long topOffset = 0;

    protected AbstractPdfTemplate(long topOffset) {
        setTopOffset(topOffset);
    }

    @Override
    public long getTopOffset() {
        return topOffset;
    }

    @Override
    public void setTopOffset(long topOffset) {
        ThrowUtils.Argument.lessThan("topOffset", topOffset, MIN_TOP_OFFSET);
        this.topOffset = topOffset;
    }
}
