package ru.clevertec.checksystem.core.common;

import java.io.IOException;
import java.util.Collection;

public interface ILayout<TInput> {

    byte[] getLayoutData(TInput input) throws IOException;

    byte[] getAllLayoutData(Collection<TInput> collection) throws IOException;
}
