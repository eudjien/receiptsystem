package ru.clevertec.checksystem.core.service;

import ru.clevertec.checksystem.core.check.Check;
import ru.clevertec.checksystem.core.factory.CheckReaderFactory;
import ru.clevertec.checksystem.core.factory.CheckWriterFactory;
import ru.clevertec.checksystem.core.io.reader.XmlCheckReader;
import ru.clevertec.checksystem.core.io.writer.JsonCheckWriter;
import ru.clevertec.checksystem.core.io.writer.XmlCheckWriter;
import ru.clevertec.checksystem.core.log.LogLevel;
import ru.clevertec.checksystem.core.log.execution.AroundExecutionLog;
import ru.clevertec.checksystem.core.utils.FileUtils;
import ru.clevertec.checksystem.normalino.list.NormalinoList;

import java.nio.file.Files;
import java.nio.file.Path;

@CheckService
@AroundExecutionLog(level = LogLevel.INFO)
public class CheckIoService implements ICheckIoService {

    @Override
    public void serializeToFile(NormalinoList<Check> checks, String destPath, String format) throws Exception {
        var checkWriter = CheckWriterFactory.create(format);
        FileUtils.writeBytesToFile(checkWriter.write(new NormalinoList<>(checks)), destPath);
    }

    @Override
    public void serializeToJsonFile(NormalinoList<Check> checks, String destPath) throws Exception {
        var checkWriter = new JsonCheckWriter();
        FileUtils.writeBytesToFile(checkWriter.write(new NormalinoList<>(checks)), destPath);
    }

    @Override
    public String serializeToJsonString(NormalinoList<Check> checks) throws Exception {
        var checkWriter = new JsonCheckWriter();
        return new String(checkWriter.write(new NormalinoList<>(checks)));
    }

    @Override
    public void serializeToXmlFile(NormalinoList<Check> checks, String destPath) throws Exception {
        var checkWriter = new XmlCheckWriter();
        FileUtils.writeBytesToFile(checkWriter.write(new NormalinoList<>(checks)), destPath);
    }

    @Override
    public String serializeToXmlString(NormalinoList<Check> checks) throws Exception {
        var checkWriter = new XmlCheckWriter();
        return new String(checkWriter.write(new NormalinoList<>(checks)));
    }

    @AroundExecutionLog(level = LogLevel.NONE)
    @Override
    public NormalinoList<Check> deserializeFromFile(String srcPath, String format) throws Exception {
        var checkReader = CheckReaderFactory.create(format);
        var bytes = Files.readAllBytes(Path.of(srcPath));
        return checkReader.readMany(bytes);
    }

    @Override
    public NormalinoList<Check> deserializeFromJsonFile(String srcPath) throws Exception {
        var checkReader = new XmlCheckReader();
        var bytes = Files.readAllBytes(Path.of(srcPath));
        return checkReader.readMany(bytes);
    }

    @Override
    public NormalinoList<Check> deserializeFromXmlFile(String srcPath) throws Exception {
        var checkReader = new XmlCheckReader();
        var bytes = Files.readAllBytes(Path.of(srcPath));
        return checkReader.readMany(bytes);
    }
}
