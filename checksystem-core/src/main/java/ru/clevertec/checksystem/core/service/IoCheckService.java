package ru.clevertec.checksystem.core.service;

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

public class IoCheckService extends EventEmitter<Object> implements IIoCheckService {

    @Override
    public void serialize(Collection<Check> checkCollection, File destinationFile, String format)
            throws IOException {
        CheckWriterFactory.create(format).write(checkCollection, destinationFile);
    }

    @Override
    public void serializeToJson(Collection<Check> checkCollection, File destinationFile) throws IOException {
        CheckWriterFactory.create(Constants.Format.IO.JSON).write(checkCollection, destinationFile);
    }

    @Override
    public String serializeToJson(Collection<Check> checkCollection) throws IOException {
        var checkWriter = CheckWriterFactory.create(Constants.Format.IO.JSON);
        return new String(checkWriter.write(checkCollection));
    }

    @Override
    public void serializeToXml(Collection<Check> checkCollection, File destinationFile) throws IOException {
        CheckWriterFactory.create(Constants.Format.IO.XML).write(checkCollection, destinationFile);
    }

    @Override
    public String serializeToXml(Collection<Check> checkCollection) throws IOException {
        var checkWriter = CheckWriterFactory.create(Constants.Format.IO.XML);
        return new String(checkWriter.write(checkCollection));
    }

    @AroundExecutionLog(level = LogLevel.NONE)
    @Override
    public Collection<Check> deserialize(File sourceFile, String format) throws IOException {
        return CheckReaderFactory.create(format).read(sourceFile);
    }

    @Override
    public Collection<Check> deserializeFromJson(File sourceFile) throws IOException {
        return CheckReaderFactory.create(Constants.Format.IO.JSON).read(sourceFile);
    }

    @Override
    public Collection<Check> deserializeFromXml(File sourceFile) throws IOException {
        return CheckReaderFactory.create(Constants.Format.IO.XML).read(sourceFile);
    }
}
