package ru.clevertec.checksystem.core.io.write;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.clevertec.checksystem.core.common.io.write.ICheckGenerateWriter;
import ru.clevertec.checksystem.core.configuration.ApplicationJsonMapper;
import ru.clevertec.checksystem.core.dto.CheckGenerate;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.Collection;

@Component
public class JsonCheckGenerateWriter implements ICheckGenerateWriter {

    private final ApplicationJsonMapper jsonMapper;

    @Autowired
    public JsonCheckGenerateWriter(ApplicationJsonMapper jsonMapper) {
        this.jsonMapper = jsonMapper;
    }

    @Override
    public byte[] write(Collection<CheckGenerate> checks) throws IOException {
        return jsonMapper.writeValueAsBytes(checks);
    }

    @Override
    public void write(Collection<CheckGenerate> checks, OutputStream outputStream) throws IOException {
        jsonMapper.writeValue(outputStream, checks);
    }

    @Override
    public void write(Collection<CheckGenerate> checks, File destinationFile) throws IOException {
        Files.createDirectories(destinationFile.toPath().getParent());
        jsonMapper.writeValue(destinationFile, checks);
    }
}
