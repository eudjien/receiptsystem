package ru.clevertec.checksystem.core.common.service;

import ru.clevertec.checksystem.core.entity.check.Check;
import ru.clevertec.checksystem.core.entity.check.GeneratedCheck;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.NoSuchElementException;

public interface IGenerateCheckService extends IService {

    Collection<GeneratedCheck> toGenerated(Collection<Check> checkCollection);

    void toGenerated(Collection<Check> checkCollection, File destinationFile, String format)
            throws IllegalArgumentException, IOException;

    Collection<Check> fromGenerated(File sourceFile, String format)
            throws IllegalArgumentException, IOException;

    Collection<Check> fromGenerated(byte[] bytes, String format)
            throws NoSuchElementException, IllegalArgumentException, IOException;

    Collection<Check> fromGenerated(Collection<GeneratedCheck> generatedChecks)
            throws NoSuchElementException, IllegalArgumentException;
}
