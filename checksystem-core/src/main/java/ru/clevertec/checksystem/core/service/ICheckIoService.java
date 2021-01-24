package ru.clevertec.checksystem.core.service;

import ru.clevertec.checksystem.core.check.Check;
import ru.clevertec.normalino.list.NormalinoList;

public interface ICheckIoService {

    void serializeToFile(NormalinoList<Check> checks, String destPath, String format) throws Exception;

    void serializeToJsonFile(NormalinoList<Check> checks, String destPath) throws Exception;

    String serializeToJsonString(NormalinoList<Check> checks) throws Exception;

    void serializeToXmlFile(NormalinoList<Check> checks, String destPath) throws Exception;

    String serializeToXmlString(NormalinoList<Check> checks) throws Exception;

    NormalinoList<Check> deserializeFromFile(String srcPath, String format) throws Exception;

    NormalinoList<Check> deserializeFromJsonFile(String srcPath) throws Exception;

    NormalinoList<Check> deserializeFromXmlFile(String srcPath) throws Exception;
}
