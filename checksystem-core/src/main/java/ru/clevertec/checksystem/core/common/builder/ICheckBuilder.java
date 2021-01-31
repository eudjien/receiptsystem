package ru.clevertec.checksystem.core.common.builder;

import ru.clevertec.checksystem.core.common.IBuildable;
import ru.clevertec.checksystem.core.entity.check.Check;
import ru.clevertec.checksystem.core.entity.check.CheckItem;
import ru.clevertec.checksystem.core.entity.discount.check.CheckDiscount;

import java.util.Date;

public interface ICheckBuilder extends IBuildable<Check> {

    ICheckBuilder setId(int id);

    ICheckBuilder setName(String name);

    ICheckBuilder setDescription(String description);

    ICheckBuilder setAddress(String address);

    ICheckBuilder setPhoneNumber(String phoneNumber);

    ICheckBuilder setCashier(String cashier);

    ICheckBuilder setDate(Date date);

    ICheckBuilder setItems(CheckItem... checkItems);

    ICheckBuilder setDiscounts(CheckDiscount... checkDiscounts);

    Check build();
}
