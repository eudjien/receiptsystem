package ru.clevertec.checksystem.core.common.io.print;

import org.springframework.stereotype.Component;
import ru.clevertec.checksystem.core.common.ICurrencyOptionable;
import ru.clevertec.checksystem.core.common.ILayout;
import ru.clevertec.checksystem.core.common.IScalable;
import ru.clevertec.checksystem.core.entity.check.Check;

@Component
public interface ICheckLayout extends ILayout<Check>, ICheckHeader, IScalable<Integer>, ICurrencyOptionable {
}
