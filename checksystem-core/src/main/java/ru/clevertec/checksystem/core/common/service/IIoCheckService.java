package ru.clevertec.checksystem.core.common.service;

import ru.clevertec.checksystem.core.entity.check.Check;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

public interface IIoCheckService extends IService {

    void serialize(Collection<Check> checks, File destinationFile, String format)
            throws IOException;

    void serializeToJson(Collection<Check> checks, File destinationFile)
            throws IOException;

    String serializeToJson(Collection<Check> checks)
            throws IOException;

    void serializeToXml(Collection<Check> checks, File destinationFile)
            throws IOException;

    String serializeToXml(Collection<Check> checks)
            throws IOException;

    Collection<Check> deserialize(File sourceFile, String format)
            throws IOException;

    Collection<Check> deserializeFromJson(File sourceFile)
            throws IOException;

    Collection<Check> deserializeFromXml(File sourceFile)
            throws IOException;
}
