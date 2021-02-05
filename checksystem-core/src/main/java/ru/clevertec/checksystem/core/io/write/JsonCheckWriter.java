package ru.clevertec.checksystem.core.io.write;

import org.springframework.stereotype.Component;
import ru.clevertec.checksystem.core.common.io.write.ICheckWriter;
import ru.clevertec.checksystem.core.configuration.ApplicationJsonMapper;
import ru.clevertec.checksystem.core.entity.check.Check;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.Collection;

@Component
public class JsonCheckWriter implements ICheckWriter {

    private final ApplicationJsonMapper jsonMapper;

    public JsonCheckWriter(ApplicationJsonMapper jsonMapper) {
        this.jsonMapper = jsonMapper;
    }

    @Override
    public byte[] write(Collection<Check> checks) throws IOException {
        return jsonMapper.writeValueAsBytes(checks);
    }

    @Override
    public void write(Collection<Check> checks, OutputStream outputStream) throws IOException {
        jsonMapper.writeValue(outputStream, checks);
    }

    @Override
    public void write(Collection<Check> checks, File destinationFile) throws IOException {
        Files.createDirectories(destinationFile.toPath().getParent());
        jsonMapper.writeValue(destinationFile, checks);
    }
}
