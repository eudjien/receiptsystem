package ru.clevertec.checksystem.core.common.check;

import ru.clevertec.checksystem.core.entity.check.CheckItem;

import java.util.Collection;

public interface ICheckItemAggregable {

    Collection<CheckItem> getCheckItems();

    void setCheckItems(Collection<CheckItem> checkItems);

    void putCheckItems(Collection<CheckItem> checkItems);

    void putCheckItem(CheckItem checkItem);

    void deleteCheckItems(Collection<CheckItem> checkItems);

    void deleteCheckItem(CheckItem checkItem);

    void clearCheckItems();
}
