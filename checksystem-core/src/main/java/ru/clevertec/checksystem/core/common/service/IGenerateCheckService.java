package ru.clevertec.checksystem.core.common.service;

import ru.clevertec.checksystem.core.dto.GeneratedCheck;
import ru.clevertec.checksystem.core.entity.check.Check;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

public interface IGenerateCheckService extends IService {

    Collection<GeneratedCheck> toGenerated(Collection<Check> checkCollection);

    void toGenerated(Collection<Check> checkCollection, File destinationFile, String format) throws IOException;

    Collection<Check> fromGenerated(File sourceFile, String format) throws IOException;

    Collection<Check> fromGenerated(byte[] bytes, String format) throws IOException;

    Collection<Check> fromGenerated(Collection<GeneratedCheck> generatedChecks);
}
