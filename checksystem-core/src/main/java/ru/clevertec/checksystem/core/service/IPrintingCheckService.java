package ru.clevertec.checksystem.core.service;

import ru.clevertec.checksystem.core.check.Check;

import java.util.Collection;

public interface IPrintingCheckService {

    void printToHtmlFile(Collection<Check> checks, String destPath) throws Exception;

    String printToHtmlString(Collection<Check> checks) throws Exception;

    void printToPdfFile(Collection<Check> checks, String destPath) throws Exception;

    void printToPdfFile(Collection<Check> checks, String destPath, String templatePath, int templateOffset)
            throws Exception;

    byte[] printToPdfBytes(Collection<Check> checks) throws Exception;

    byte[] printToPdfBytes(Collection<Check> checks, String templatePath, int templateOffset) throws Exception;

    void printToTextFile(Collection<Check> checks, String destPath) throws Exception;

    byte[] printToTextBytes(Collection<Check> checks) throws Exception;

    String printToTextString(Collection<Check> checks) throws Exception;
}
