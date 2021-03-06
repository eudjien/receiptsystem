package ru.clevertec.checksystem.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.clevertec.checksystem.core.annotation.execution.AroundExecutionLog;
import ru.clevertec.checksystem.core.common.service.IIoCheckService;
import ru.clevertec.checksystem.core.entity.check.Check;
import ru.clevertec.checksystem.core.event.EventEmitter;
import ru.clevertec.checksystem.core.factory.io.CheckReaderFactory;
import ru.clevertec.checksystem.core.factory.io.CheckWriterFactory;
import ru.clevertec.checksystem.core.log.LogLevel;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;

import static ru.clevertec.checksystem.core.Constants.Format;

@Service
public class IoCheckService extends EventEmitter<Object> implements IIoCheckService {

    private final CheckReaderFactory checkReaderFactory;
    private final CheckWriterFactory checkWriterFactory;

    @Autowired
    public IoCheckService(CheckReaderFactory checkReaderFactory, CheckWriterFactory checkWriterFactory) {
        this.checkReaderFactory = checkReaderFactory;
        this.checkWriterFactory = checkWriterFactory;
    }

    @Override
    public void serialize(Collection<Check> checks, File destinationFile, String format) throws IOException {
        checkWriterFactory.instance(format).write(checks, destinationFile);
    }

    @Override
    public void serialize(Collection<Check> checks, OutputStream outputStream, String format) throws IOException {
        checkWriterFactory.instance(format).write(checks, outputStream);
    }

    @Override
    public void serializeToJson(Collection<Check> checks, File destinationFile) throws IOException {
        checkWriterFactory.instance(Format.JSON).write(checks, destinationFile);
    }

    @Override
    public String serializeToJson(Collection<Check> checks) throws IOException {
        var writer = checkWriterFactory.instance(Format.JSON);
        return new String(writer.write(checks));
    }

    @Override
    public void serializeToXml(Collection<Check> checks, File destinationFile) throws IOException {
        checkWriterFactory.instance(Format.XML).write(checks, destinationFile);
    }

    @Override
    public String serializeToXml(Collection<Check> checks) throws IOException {
        var writer = checkWriterFactory.instance(Format.XML);
        return new String(writer.write(checks));
    }

    @AroundExecutionLog(level = LogLevel.NONE)
    @Override
    public Collection<Check> deserialize(File sourceFile, String format) throws IOException {
        return checkReaderFactory.instance(format).read(sourceFile);
    }

    @Override
    public Collection<Check> deserialize(InputStream is, String format) throws IOException {
        return checkReaderFactory.instance(format).read(is);
    }

    @Override
    public Collection<Check> deserializeFromJson(File sourceFile) throws IOException {
        return checkReaderFactory.instance(Format.JSON).read(sourceFile);
    }

    @Override
    public Collection<Check> deserializeFromXml(File sourceFile) throws IOException {
        return checkReaderFactory.instance(Format.XML).read(sourceFile);
    }
}
