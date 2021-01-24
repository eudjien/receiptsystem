package ru.clevertec.checksystem.core.io.reader;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.clevertec.checksystem.core.check.Check;
import ru.clevertec.normalino.list.NormalinoList;

import java.util.Arrays;

public class JsonCheckReader extends CheckReader {

    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public Check read(byte[] bytes) throws Exception {
        return mapper.readValue(bytes, Check.class);
    }

    @Override
    public NormalinoList<Check> readMany(byte[] bytes) throws Exception {
        return new NormalinoList<>(Arrays.asList(mapper.readValue(bytes, Check[].class)));
    }
}
