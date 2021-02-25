package ru.clevertec.checksystem.core.common.service;

import ru.clevertec.checksystem.core.entity.check.Check;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Collection;

public interface IPrintingCheckService extends IService {

    void printToHtml(Collection<Check> checkCollection, File destinationFile) throws IOException;

    void printToHtml(Collection<Check> checkCollection, OutputStream outputStream) throws IOException;

    String printToHtml(Collection<Check> checkCollection) throws IOException;

    void printToPdf(Collection<Check> checkCollection, File destinationFile) throws IOException;

    void printToPdf(Collection<Check> checkCollection, OutputStream outputStream) throws IOException;

    byte[] printToPdf(Collection<Check> checkCollection) throws IOException;

    void printWithTemplateToPdf(Collection<Check> checkCollection, File destinationFile, File templateFile) throws IOException;

    void printWithTemplateToPdf(Collection<Check> checkCollection, OutputStream stream, File templateFile) throws IOException;

    void printWithTemplateToPdf(Collection<Check> checkCollection, File destinationFile, File templateFile, int templateTopOffset) throws IOException;

    void printWithTemplateToPdf(Collection<Check> checkCollection, OutputStream outputStream, File templateFile, int templateTopOffset) throws IOException;

    byte[] printWithTemplateToPdf(Collection<Check> checkCollection, File templateFile) throws IOException;

    byte[] printWithTemplateToPdf(Collection<Check> checkCollection, File templateFile, int templateOffset) throws IOException;

    void printToText(Collection<Check> checkCollection, File destinationFile) throws IOException;

    void printToText(Collection<Check> checkCollection, OutputStream outputStream) throws IOException;

    String printToText(Collection<Check> checkCollection) throws IOException;
}
