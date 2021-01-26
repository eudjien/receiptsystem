package ru.clevertec.checksystem.core.io.read;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.clevertec.checksystem.core.entity.check.GeneratedCheck;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collection;

public class JsonIGeneratedCheckReader implements IGeneratedCheckReader {

    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public Collection<GeneratedCheck> read(byte[] bytes) throws IOException {
        return Arrays.asList(mapper.readValue(bytes, GeneratedCheck[].class));
    }

    @Override
    public Collection<GeneratedCheck> read(InputStream inputStream) throws IOException {
        return Arrays.asList(mapper.readValue(inputStream, GeneratedCheck[].class));
    }

    @Override
    public Collection<GeneratedCheck> read(File sourceFile) throws IOException {
        return Arrays.asList(mapper.readValue(sourceFile, GeneratedCheck[].class));
    }
}
