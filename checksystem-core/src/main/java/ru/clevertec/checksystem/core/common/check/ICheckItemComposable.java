package ru.clevertec.checksystem.core.common.check;

import ru.clevertec.checksystem.core.entity.check.CheckItem;

public interface ICheckItemComposable {

    CheckItem getCheckItem();

    void setCheckItem(CheckItem checkItem);
}
