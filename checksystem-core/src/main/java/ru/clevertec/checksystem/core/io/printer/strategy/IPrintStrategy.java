package ru.clevertec.checksystem.core.io.printer.strategy;

import java.io.IOException;
import java.net.URISyntaxException;

public interface IPrintStrategy<TInput> {
    byte[] getData(TInput input) throws IOException, URISyntaxException;

    byte[] getCombinedData(TInput[] input) throws IOException, URISyntaxException;
}
