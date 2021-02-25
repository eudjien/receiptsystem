package ru.clevertec.checksystem.core.common;

import ru.clevertec.checksystem.core.common.template.ITemplate;

public interface ITemplatable<T extends ITemplate> {
    boolean hasTemplate();

    void setTemplate(T template);

    T getTemplate();
}
