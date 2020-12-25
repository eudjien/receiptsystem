package ru.clevertec.checksystem.core.io.reader;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.clevertec.checksystem.core.check.Check;

import java.util.Arrays;
import java.util.List;

public class JsonCheckReader extends CheckReader {

    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public Check read(byte[] bytes) throws Exception {
        return mapper.readValue(bytes, Check.class);
    }

    @Override
    public List<Check> readMany(byte[] bytes) throws Exception {
        return Arrays.asList(mapper.readValue(bytes, Check[].class));
    }
}
