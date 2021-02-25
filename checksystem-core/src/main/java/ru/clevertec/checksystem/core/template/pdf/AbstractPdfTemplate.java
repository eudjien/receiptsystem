package ru.clevertec.checksystem.core.template.pdf;

import ru.clevertec.checksystem.core.common.template.IPdfTemplate;
import ru.clevertec.checksystem.core.util.ThrowUtils;

public abstract class AbstractPdfTemplate implements IPdfTemplate {

    private static final int MIN_TOP_OFFSET = 0;

    private int topOffset = 0;

    protected AbstractPdfTemplate(int topOffset) {
        setTopOffset(topOffset);
    }

    @Override
    public int getTopOffset() {
        return topOffset;
    }

    @Override
    public void setTopOffset(int topOffset) {
        ThrowUtils.Argument.lessThan("topOffset", topOffset, MIN_TOP_OFFSET);
        this.topOffset = topOffset;
    }
}
