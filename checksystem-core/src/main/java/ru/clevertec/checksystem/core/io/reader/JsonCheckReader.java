package ru.clevertec.checksystem.core.io.reader;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.clevertec.checksystem.core.check.Check;
import ru.clevertec.normalino.list.NormalinoList;

import java.io.File;
import java.util.Arrays;
import java.util.Collection;

public class JsonCheckReader extends CheckReader {

    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public Collection<Check> read(byte[] bytes) throws Exception {
        return new NormalinoList<>(Arrays.asList(mapper.readValue(bytes, Check[].class)));
    }

    @Override
    public Collection<Check> read(File file) throws Exception {
        return new NormalinoList<>(Arrays.asList(mapper.readValue(file, Check[].class)));
    }
}
