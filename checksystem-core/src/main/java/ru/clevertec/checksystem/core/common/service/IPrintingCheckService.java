package ru.clevertec.checksystem.core.common.service;

import ru.clevertec.checksystem.core.entity.check.Check;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Collection;

public interface IPrintingCheckService extends IService {

    void print(Collection<Check> checks, File destinationFile, String format) throws IOException;

    void print(Collection<Check> checks, OutputStream outputStream, String format) throws IOException;

    void printToHtml(Collection<Check> checks, File destinationFile) throws IOException;

    void printToHtml(Collection<Check> checks, OutputStream outputStream) throws IOException;

    String printToHtml(Collection<Check> checks) throws IOException;

    void printToPdf(Collection<Check> checks, File destinationFile) throws IOException;

    void printToPdf(Collection<Check> checks, OutputStream outputStream) throws IOException;

    byte[] printToPdf(Collection<Check> checks) throws IOException;

    void printWithTemplateToPdf(Collection<Check> checks, File destinationFile, File templateFile) throws IOException;

    void printWithTemplateToPdf(Collection<Check> checks, OutputStream stream, File templateFile) throws IOException;

    void printWithTemplateToPdf(Collection<Check> checks, File destinationFile, File templateFile, int templateTopOffset) throws IOException;

    void printWithTemplateToPdf(Collection<Check> checks, OutputStream outputStream, File templateFile, int templateTopOffset) throws IOException;

    byte[] printWithTemplateToPdf(Collection<Check> checks, File templateFile) throws IOException;

    byte[] printWithTemplateToPdf(Collection<Check> checks, File templateFile, int templateOffset) throws IOException;

    void printToText(Collection<Check> checks, File destinationFile) throws IOException;

    void printToText(Collection<Check> checks, OutputStream outputStream) throws IOException;

    String printToText(Collection<Check> checks) throws IOException;
}
