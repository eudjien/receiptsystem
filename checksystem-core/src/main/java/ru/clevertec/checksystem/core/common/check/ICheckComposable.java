package ru.clevertec.checksystem.core.common.check;

import ru.clevertec.checksystem.core.entity.check.Check;

public interface ICheckComposable {

    Check getCheck();

    void setCheck(Check check);
}
