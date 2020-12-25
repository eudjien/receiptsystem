package ru.clevertec.checksystem.core.io.writer;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.clevertec.checksystem.core.check.Check;

import java.util.List;

public class JsonCheckWriter extends CheckWriter {

    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public byte[] write(Check check) throws Exception {
        return mapper.writeValueAsBytes(check);
    }

    @Override
    public byte[] write(List<Check> input) throws Exception {
        return mapper.writeValueAsBytes(input);
    }
}
