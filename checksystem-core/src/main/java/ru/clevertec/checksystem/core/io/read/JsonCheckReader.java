package ru.clevertec.checksystem.core.io.read;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.clevertec.checksystem.core.entity.check.Check;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collection;

public class JsonCheckReader implements ICheckReader {

    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public Collection<Check> read(byte[] bytes) throws IOException {
        return Arrays.asList(mapper.readValue(bytes, Check[].class));
    }

    @Override
    public Collection<Check> read(InputStream inputStream) throws IOException {
        return Arrays.asList(mapper.readValue(inputStream, Check[].class));
    }

    @Override
    public Collection<Check> read(File sourceFile) throws IOException {
        return Arrays.asList(mapper.readValue(sourceFile, Check[].class));
    }
}
