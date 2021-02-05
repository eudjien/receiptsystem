package ru.clevertec.checksystem.core.io.write;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.clevertec.checksystem.core.common.io.write.ICheckWriter;
import ru.clevertec.checksystem.core.configuration.ApplicationXmlMapper;
import ru.clevertec.checksystem.core.entity.check.Check;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.Collection;

@Component
public class XmlCheckWriter implements ICheckWriter {

    private final ApplicationXmlMapper xmlMapper;

    @Autowired
    public XmlCheckWriter(ApplicationXmlMapper xmlMapper) {
        this.xmlMapper = xmlMapper;
    }

    @Override
    public byte[] write(Collection<Check> checks) throws IOException {
        return xmlMapper.writeValueAsBytes(checks);
    }

    @Override
    public void write(Collection<Check> checks, OutputStream outputStream) throws IOException {
        xmlMapper.writeValue(outputStream, checks);
    }

    @Override
    public void write(Collection<Check> checks, File destinationFile) throws IOException {
        Files.createDirectories(destinationFile.toPath().getParent());
        xmlMapper.writeValue(destinationFile, checks);
    }
}
