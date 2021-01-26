package ru.clevertec.checksystem.core.io.write;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.clevertec.checksystem.core.entity.check.Check;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.Collection;

public class JsonCheckWriter implements ICheckWriter {

    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public byte[] write(Collection<Check> checkCollection) throws IOException {
        return mapper.writeValueAsBytes(checkCollection);
    }

    @Override
    public void write(Collection<Check> checkCollection, OutputStream outputStream) throws IOException {
        mapper.writeValue(outputStream, checkCollection);
    }

    @Override
    public void write(Collection<Check> checkCollection, File destinationFile) throws IOException {
        Files.createDirectories(destinationFile.toPath().getParent());
        mapper.writeValue(destinationFile, checkCollection);
    }
}
