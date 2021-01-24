package ru.clevertec.checksystem.core.io.reader;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.clevertec.checksystem.core.check.generated.GeneratedCheck;
import ru.clevertec.normalino.list.NormalinoList;

import java.io.File;
import java.util.Arrays;
import java.util.Collection;

public class JsonGeneratedCheckReader extends GeneratedCheckReader {

    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public Collection<GeneratedCheck> read(byte[] bytes) throws Exception {
        return new NormalinoList<>(Arrays.asList(mapper.readValue(bytes, GeneratedCheck[].class)));
    }

    @Override
    public Collection<GeneratedCheck> read(File file) throws Exception {
        return new NormalinoList<>(Arrays.asList(mapper.readValue(file, GeneratedCheck[].class)));
    }
}
