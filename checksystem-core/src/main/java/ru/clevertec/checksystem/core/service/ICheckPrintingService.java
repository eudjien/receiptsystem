package ru.clevertec.checksystem.core.service;

import ru.clevertec.checksystem.core.check.Check;
import ru.clevertec.normalino.list.NormalinoList;

public interface ICheckPrintingService {

    void printToHtmlFile(NormalinoList<Check> checks, String destPath) throws Exception;

    String printToHtmlString(NormalinoList<Check> checks) throws Exception;

    void printToPdfFile(NormalinoList<Check> checks, String destPath) throws Exception;

    void printToPdfFile(NormalinoList<Check> checks, String destPath, String templatePath, int templateOffset)
            throws Exception;

    byte[] printToPdfBytes(NormalinoList<Check> checks) throws Exception;

    byte[] printToPdfBytes(NormalinoList<Check> checks, String templatePath, int templateOffset) throws Exception;

    void printToTextFile(NormalinoList<Check> checks, String destPath) throws Exception;

    byte[] printToTextBytes(NormalinoList<Check> checks) throws Exception;

    String printToTextString(NormalinoList<Check> checks) throws Exception;
}
