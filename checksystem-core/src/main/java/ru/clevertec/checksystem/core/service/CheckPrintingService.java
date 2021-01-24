package ru.clevertec.checksystem.core.service;

import ru.clevertec.checksystem.core.check.Check;
import ru.clevertec.checksystem.core.io.printer.CheckPrinter;
import ru.clevertec.checksystem.core.io.printer.strategy.HtmlCheckPrintStrategy;
import ru.clevertec.checksystem.core.io.printer.strategy.PdfCheckPrintStrategy;
import ru.clevertec.checksystem.core.io.printer.strategy.TextCheckPrintStrategy;
import ru.clevertec.checksystem.core.io.printer.template.pdf.FilePrintPdfTemplate;
import ru.clevertec.checksystem.core.log.LogLevel;
import ru.clevertec.checksystem.core.log.execution.AfterExecutionLog;
import ru.clevertec.checksystem.core.log.execution.AroundExecutionLog;
import ru.clevertec.checksystem.core.log.execution.BeforeExecutionLog;
import ru.clevertec.checksystem.core.utils.FileUtils;
import ru.clevertec.normalino.list.NormalinoList;

@CheckService
@AroundExecutionLog
public class CheckPrintingService implements ICheckPrintingService {

    @Override
    public void printToHtmlFile(NormalinoList<Check> checks, String destPath) throws Exception {
        var checkPrinter = new CheckPrinter(new NormalinoList<>(checks), new HtmlCheckPrintStrategy());
        FileUtils.writeBytesToFile(checkPrinter.printRaw(), destPath);
    }

    @Override
    public String printToHtmlString(NormalinoList<Check> checks) throws Exception {
        var checkPrinter = new CheckPrinter(new NormalinoList<>(checks), new HtmlCheckPrintStrategy());
        return new String(checkPrinter.printRaw());
    }

    @Override
    public void printToPdfFile(NormalinoList<Check> checks, String destPath) throws Exception {
        printToPdfFile(checks, destPath, null, 0);
    }

    @Override
    public void printToPdfFile(NormalinoList<Check> checks, String destPath, String templatePath, int templateOffset)
            throws Exception {
        var checkPrinter = createPdfPrinter(checks, templatePath, templateOffset);
        FileUtils.writeBytesToFile(checkPrinter.printRaw(), destPath);
    }

    @BeforeExecutionLog(level = LogLevel.NONE)
    @AfterExecutionLog(level = LogLevel.NONE)
    @AroundExecutionLog(level = LogLevel.NONE)
    @Override
    public byte[] printToPdfBytes(NormalinoList<Check> checks) throws Exception {
        return printToPdfBytes(checks, null, 0);
    }

    @BeforeExecutionLog(level = LogLevel.NONE)
    @AfterExecutionLog(level = LogLevel.NONE)
    @AroundExecutionLog(level = LogLevel.NONE)
    @Override
    public byte[] printToPdfBytes(NormalinoList<Check> checks, String templatePath, int templateOffset)
            throws Exception {
        var checkPrinter = createPdfPrinter(checks, templatePath, templateOffset);
        return checkPrinter.printRaw();
    }

    @Override
    public void printToTextFile(NormalinoList<Check> checks, String destPath) throws Exception {
        var checkPrinter = new CheckPrinter(new NormalinoList<>(checks), new TextCheckPrintStrategy());
        FileUtils.writeBytesToFile(checkPrinter.printRaw(), destPath);
    }

    @BeforeExecutionLog(level = LogLevel.NONE)
    @AfterExecutionLog(level = LogLevel.NONE)
    @AroundExecutionLog(level = LogLevel.NONE)
    @Override
    public byte[] printToTextBytes(NormalinoList<Check> checks) throws Exception {
        var checkPrinter = new CheckPrinter(new NormalinoList<>(checks), new TextCheckPrintStrategy());
        return checkPrinter.printRaw();
    }

    @Override
    public String printToTextString(NormalinoList<Check> checks) throws Exception {
        var checkPrinter = new CheckPrinter(new NormalinoList<>(checks), new TextCheckPrintStrategy());
        return new String(checkPrinter.printRaw());
    }

    private CheckPrinter createPdfPrinter(NormalinoList<Check> checks, String templatePath, int templateOffset)
            throws Exception {

        var checkPrinter = new CheckPrinter(new NormalinoList<>(checks));
        var strategy = new PdfCheckPrintStrategy();
        if (templatePath != null) {
            strategy.setTemplate(new FilePrintPdfTemplate(templatePath, templateOffset));
        }
        checkPrinter.setStrategy(strategy);
        return checkPrinter;
    }
}
