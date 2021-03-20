package ru.clevertec.checksystem.core.io.print.layout;

import java.io.IOException;
import java.util.Collection;

public interface ILayout<T> {
    byte[] getLayoutData(T input) throws IOException;

    byte[] getAllLayoutData(Collection<T> collection) throws IOException;
}
