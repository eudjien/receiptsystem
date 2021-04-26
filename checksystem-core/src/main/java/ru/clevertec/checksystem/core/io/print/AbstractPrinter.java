package ru.clevertec.checksystem.core.io.print;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.clevertec.checksystem.core.io.print.layout.ILayout;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class AbstractPrinter<T> implements IPrinter<T> {
    private ILayout<T> layout;
}
