package ru.clevertec.checksystem.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.clevertec.checksystem.core.Constants;
import ru.clevertec.checksystem.core.annotation.execution.AroundExecutionLog;
import ru.clevertec.checksystem.core.common.service.IIoCheckService;
import ru.clevertec.checksystem.core.entity.check.Check;
import ru.clevertec.checksystem.core.event.EventEmitter;
import ru.clevertec.checksystem.core.factory.io.CheckReaderFactory;
import ru.clevertec.checksystem.core.factory.io.CheckWriterFactory;
import ru.clevertec.checksystem.core.log.LogLevel;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

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
        checkWriterFactory.create(format).write(checks, destinationFile);
    }

    @Override
    public void serializeToJson(Collection<Check> checks, File destinationFile) throws IOException {
        checkWriterFactory.create(Constants.Format.IO.JSON).write(checks, destinationFile);
    }

    @Override
    public String serializeToJson(Collection<Check> checks) throws IOException {
        var checkWriter = checkWriterFactory.create(Constants.Format.IO.JSON);
        return new String(checkWriter.write(checks));
    }

    @Override
    public void serializeToXml(Collection<Check> checks, File destinationFile) throws IOException {
        checkWriterFactory.create(Constants.Format.IO.XML).write(checks, destinationFile);
    }

    @Override
    public String serializeToXml(Collection<Check> checks) throws IOException {
        var checkWriter = checkWriterFactory.create(Constants.Format.IO.XML);
        return new String(checkWriter.write(checks));
    }

    @AroundExecutionLog(level = LogLevel.NONE)
    @Override
    public Collection<Check> deserialize(File sourceFile, String format) throws IOException {
        return checkReaderFactory.create(format).read(sourceFile);
    }

    @Override
    public Collection<Check> deserializeFromJson(File sourceFile) throws IOException {
        return checkReaderFactory.create(Constants.Format.IO.JSON).read(sourceFile);
    }

    @Override
    public Collection<Check> deserializeFromXml(File sourceFile) throws IOException {
        return checkReaderFactory.create(Constants.Format.IO.XML).read(sourceFile);
    }
}
