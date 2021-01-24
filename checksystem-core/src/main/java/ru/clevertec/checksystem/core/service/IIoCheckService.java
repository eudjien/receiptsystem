package ru.clevertec.checksystem.core.service;

import ru.clevertec.checksystem.core.check.Check;

import java.io.File;
import java.util.Collection;

public interface IIoCheckService {

    void serializeToFile(Collection<Check> checks, File file, String format) throws Exception;

    void serializeToJsonFile(Collection<Check> checks, File file) throws Exception;

    String serializeToJsonString(Collection<Check> checks) throws Exception;

    void serializeToXmlFile(Collection<Check> checks, File file) throws Exception;

    String serializeToXmlString(Collection<Check> checks) throws Exception;

    Collection<Check> deserializeFromFile(String srcPath, String format) throws Exception;

    Collection<Check> deserializeFromJsonFile(String srcPath) throws Exception;

    Collection<Check> deserializeFromXmlFile(String srcPath) throws Exception;
}
