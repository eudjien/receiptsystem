package ru.clevertec.checksystem.core.template.pdf;

import ru.clevertec.checksystem.core.template.ITemplate;

public interface IPdfTemplate extends ITemplate {
    long getTopOffset();

    void setTopOffset(long offset);
}
