package ru.clevertec.checksystem.core.common.template;

public interface IPdfTemplate extends ITemplate {
    long getTopOffset();

    void setTopOffset(long offset);
}
