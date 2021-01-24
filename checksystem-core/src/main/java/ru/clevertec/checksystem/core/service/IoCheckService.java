package ru.clevertec.checksystem.core.service;

import ru.clevertec.checksystem.core.check.Check;
import ru.clevertec.checksystem.core.factory.CheckReaderFactory;
import ru.clevertec.checksystem.core.factory.CheckWriterFactory;
import ru.clevertec.checksystem.core.io.reader.XmlCheckReader;
import ru.clevertec.checksystem.core.io.writer.JsonCheckWriter;
import ru.clevertec.checksystem.core.io.writer.XmlCheckWriter;
import ru.clevertec.checksystem.core.log.LogLevel;
import ru.clevertec.checksystem.core.log.execution.AroundExecutionLog;
import ru.clevertec.normalino.list.NormalinoList;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;

@CheckService
@AroundExecutionLog(level = LogLevel.INFO)
public class IoCheckService implements IIoCheckService {

    @Override
    public void serializeToFile(Collection<Check> checks, File file, String format) throws Exception {
        var checkWriter = CheckWriterFactory.create(format);
        checkWriter.write(new NormalinoList<>(checks), file);
    }

    @Override
    public void serializeToJsonFile(Collection<Check> checks, File file) throws Exception {
        var checkWriter = new JsonCheckWriter();
        checkWriter.write(new NormalinoList<>(checks), file);
    }

    @Override
    public String serializeToJsonString(Collection<Check> checks) throws Exception {
        var checkWriter = new JsonCheckWriter();
        return new String(checkWriter.write(new NormalinoList<>(checks)));
    }

    @Override
    public void serializeToXmlFile(Collection<Check> checks, File file) throws Exception {
        var checkWriter = new XmlCheckWriter();
        checkWriter.write(new NormalinoList<>(checks), file);
    }

    @Override
    public String serializeToXmlString(Collection<Check> checks) throws Exception {
        var checkWriter = new XmlCheckWriter();
        return new String(checkWriter.write(new NormalinoList<>(checks)));
    }

    @AroundExecutionLog(level = LogLevel.NONE)
    @Override
    public Collection<Check> deserializeFromFile(String srcPath, String format) throws Exception {
        var checkReader = CheckReaderFactory.create(format);
        var bytes = Files.readAllBytes(Path.of(srcPath));
        return checkReader.read(bytes);
    }

    @Override
    public Collection<Check> deserializeFromJsonFile(String srcPath) throws Exception {
        var checkReader = new XmlCheckReader();
        var bytes = Files.readAllBytes(Path.of(srcPath));
        return checkReader.read(bytes);
    }

    @Override
    public Collection<Check> deserializeFromXmlFile(String srcPath) throws Exception {
        var checkReader = new XmlCheckReader();
        var bytes = Files.readAllBytes(Path.of(srcPath));
        return checkReader.read(bytes);
    }
}
