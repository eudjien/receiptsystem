package ru.clevertec.checksystem.core.service;

import ru.clevertec.checksystem.core.check.Check;
import ru.clevertec.checksystem.core.check.generated.GeneratedCheck;

import java.io.File;
import java.util.Collection;

public interface IGenerateCheckService {
    Collection<GeneratedCheck> toGenerated(Collection<Check> checks);

    void toGenerated(Collection<Check> checks, File file, String format) throws Exception;

    Collection<Check> fromGenerated(File file, String format) throws Exception;

    Collection<Check> fromGenerated(byte[] bytes, String format) throws Exception;

    Collection<Check> fromGenerated(Collection<GeneratedCheck> generates) throws Exception;
}
