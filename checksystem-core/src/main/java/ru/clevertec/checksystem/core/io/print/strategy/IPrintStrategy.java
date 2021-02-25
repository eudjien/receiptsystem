package ru.clevertec.checksystem.core.io.print.strategy;

import java.io.IOException;
import java.util.Collection;

public interface IPrintStrategy<TInput> {

    byte[] getData(TInput input) throws IOException;

    byte[] getCombinedData(Collection<TInput> collection) throws IOException;
}
