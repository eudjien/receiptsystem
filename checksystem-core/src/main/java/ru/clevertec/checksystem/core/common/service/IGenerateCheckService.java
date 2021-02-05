package ru.clevertec.checksystem.core.common.service;

import ru.clevertec.checksystem.core.dto.CheckGenerate;
import ru.clevertec.checksystem.core.entity.check.Check;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

public interface IGenerateCheckService extends IService {

    Collection<CheckGenerate> toGenerate(Collection<Check> checks);

    void toGenerate(Collection<Check> checks, File destinationFile, String format) throws IOException;

    Collection<Check> fromGenerate(File sourceFile, String format) throws IOException;

    Collection<Check> fromGenerate(byte[] bytes, String format) throws IOException;

    Collection<Check> fromGenerate(Collection<CheckGenerate> checkGenerates);
}
