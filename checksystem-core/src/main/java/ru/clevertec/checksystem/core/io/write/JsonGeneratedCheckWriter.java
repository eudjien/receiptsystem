package ru.clevertec.checksystem.core.io.write;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.clevertec.checksystem.core.entity.check.GeneratedCheck;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.Collection;

public class JsonGeneratedCheckWriter implements IGeneratedCheckWriter {

    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public byte[] write(Collection<GeneratedCheck> checkCollection) throws IOException {
        return mapper.writeValueAsBytes(checkCollection);
    }

    @Override
    public void write(Collection<GeneratedCheck> checkCollection, OutputStream outputStream) throws IOException {
        mapper.writeValue(outputStream, checkCollection);
    }

    @Override
    public void write(Collection<GeneratedCheck> checkCollection, File destinationFile) throws IOException {
        Files.createDirectories(destinationFile.toPath().getParent());
        mapper.writeValue(destinationFile, checkCollection);
    }
}
