package ru.clevertec.checksystem.core.common.check;

import ru.clevertec.checksystem.core.entity.check.CheckItem;

import java.util.Collection;

public interface ICheckItemAggregable {

    Collection<CheckItem> getCheckItems();

    void setCheckItems(Collection<CheckItem> checkItems);

    void addCheckItem(CheckItem checkItem);

    void addCheckItems(Collection<CheckItem> checkItems);

    void removeCheckItem(CheckItem checkItem);

    void removeCheckItems(Collection<CheckItem> checkItems);

    void clearCheckItems();
}
