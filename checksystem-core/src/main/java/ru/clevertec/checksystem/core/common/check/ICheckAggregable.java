package ru.clevertec.checksystem.core.common.check;

import ru.clevertec.checksystem.core.entity.check.Check;

import java.util.Collection;

public interface ICheckAggregable {

    Collection<Check> getChecks();

    void setChecks(Collection<Check> checks);

    void addCheck(Check check);

    void addChecks(Collection<Check> checks);

    void removeCheck(Check check);

    void removeChecks(Collection<Check> checks);

    void clearChecks();
}
