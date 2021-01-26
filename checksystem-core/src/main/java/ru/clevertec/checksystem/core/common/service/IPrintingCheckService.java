package ru.clevertec.checksystem.core.common.service;

import ru.clevertec.checksystem.core.entity.check.Check;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Collection;

public interface IPrintingCheckService extends IService {

    void printToHtml(Collection<Check> checkCollection, File destinationFile)
            throws IllegalArgumentException, IOException;

    void printToHtml(Collection<Check> checkCollection, OutputStream outputStream)
            throws IllegalArgumentException, IOException;

    String printToHtml(Collection<Check> checkCollection)
            throws IllegalArgumentException, IOException;

    void printToPdf(Collection<Check> checkCollection, File destinationFile)
            throws IllegalArgumentException, IOException;

    void printToPdf(Collection<Check> checkCollection, OutputStream outputStream)
            throws IllegalArgumentException, IOException;

    byte[] printToPdf(Collection<Check> checkCollection)
            throws IllegalArgumentException, IOException;

    void printWithTemplateToPdf(Collection<Check> checkCollection, File destinationFile, File templateFile)
            throws IllegalArgumentException, IOException;

    void printWithTemplateToPdf(Collection<Check> checkCollection, OutputStream stream, File templateFile)
            throws IllegalArgumentException, IOException;

    void printWithTemplateToPdf(Collection<Check> checkCollection, File destinationFile,
                                File templateFile, int templateTopOffset)
            throws IllegalArgumentException, IOException;

    void printWithTemplateToPdf(Collection<Check> checkCollection, OutputStream outputStream,
                                File templateFile, int templateTopOffset)
            throws IllegalArgumentException, IOException;

    byte[] printWithTemplateToPdf(Collection<Check> checkCollection, File templateFile)
            throws IllegalArgumentException, IOException;

    byte[] printWithTemplateToPdf(Collection<Check> checkCollection, File templateFile, int templateOffset)
            throws IllegalArgumentException, IOException;

    void printToText(Collection<Check> checkCollection, File destinationFile)
            throws IllegalArgumentException, IOException;

    void printToText(Collection<Check> checkCollection, OutputStream outputStream)
            throws IllegalArgumentException, IOException;

    String printToText(Collection<Check> checkCollection)
            throws IllegalArgumentException, IOException;
}
