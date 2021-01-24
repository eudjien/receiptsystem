package ru.clevertec.checksystem.core.io.writer;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.clevertec.checksystem.core.check.generated.GeneratedCheck;

import java.io.File;
import java.util.Collection;

public class JsonGeneratedCheckWriter extends GeneratedCheckWriter {

    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public byte[] write(Collection<GeneratedCheck> input) throws Exception {
        return mapper.writeValueAsBytes(input);
    }

    @Override
    public void write(Collection<GeneratedCheck> input, File file) throws Exception {
        mapper.writeValue(file, input);
    }
}
