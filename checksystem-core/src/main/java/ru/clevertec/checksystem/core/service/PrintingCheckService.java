package ru.clevertec.checksystem.core.service;

import ru.clevertec.checksystem.core.check.Check;
import ru.clevertec.checksystem.core.log.LogLevel;
import ru.clevertec.checksystem.core.log.execution.AfterExecutionLog;
import ru.clevertec.checksystem.core.log.execution.AroundExecutionLog;
import ru.clevertec.checksystem.core.log.execution.BeforeExecutionLog;
import ru.clevertec.checksystem.core.print.CheckPrinter;
import ru.clevertec.checksystem.core.print.strategy.HtmlCheckPrintStrategy;
import ru.clevertec.checksystem.core.print.strategy.PdfCheckPrintStrategy;
import ru.clevertec.checksystem.core.print.strategy.TextCheckPrintStrategy;
import ru.clevertec.checksystem.core.print.template.pdf.FilePrintPdfTemplate;
import ru.clevertec.checksystem.core.utils.FileUtils;
import ru.clevertec.normalino.list.NormalinoList;

import java.util.Collection;

@CheckService
@AroundExecutionLog
public class PrintingCheckService implements IPrintingCheckService {

    @Override
    public void printToHtmlFile(Collection<Check> checks, String destPath) throws Exception {
        var checkPrinter = new CheckPrinter(new NormalinoList<>(checks), new HtmlCheckPrintStrategy());
        FileUtils.writeBytesToFile(checkPrinter.printRaw(), destPath);
    }

    @Override
    public String printToHtmlString(Collection<Check> checks) throws Exception {
        var checkPrinter = new CheckPrinter(new NormalinoList<>(checks), new HtmlCheckPrintStrategy());
        return new String(checkPrinter.printRaw());
    }

    @Override
    public void printToPdfFile(Collection<Check> checks, String destPath) throws Exception {
        printToPdfFile(checks, destPath, null, 0);
    }

    @Override
    public void printToPdfFile(Collection<Check> checks, String destPath, String templatePath, int templateOffset)
            throws Exception {
        var checkPrinter = createPdfPrinter(checks, templatePath, templateOffset);
        FileUtils.writeBytesToFile(checkPrinter.printRaw(), destPath);
    }

    @BeforeExecutionLog(level = LogLevel.NONE)
    @AfterExecutionLog(level = LogLevel.NONE)
    @AroundExecutionLog(level = LogLevel.NONE)
    @Override
    public byte[] printToPdfBytes(Collection<Check> checks) throws Exception {
        return printToPdfBytes(checks, null, 0);
    }

    @BeforeExecutionLog(level = LogLevel.NONE)
    @AfterExecutionLog(level = LogLevel.NONE)
    @AroundExecutionLog(level = LogLevel.NONE)
    @Override
    public byte[] printToPdfBytes(Collection<Check> checks, String templatePath, int templateOffset)
            throws Exception {
        var checkPrinter = createPdfPrinter(checks, templatePath, templateOffset);
        return checkPrinter.printRaw();
    }

    @Override
    public void printToTextFile(Collection<Check> checks, String destPath) throws Exception {
        var checkPrinter = new CheckPrinter(new NormalinoList<>(checks), new TextCheckPrintStrategy());
        FileUtils.writeBytesToFile(checkPrinter.printRaw(), destPath);
    }

    @BeforeExecutionLog(level = LogLevel.NONE)
    @AfterExecutionLog(level = LogLevel.NONE)
    @AroundExecutionLog(level = LogLevel.NONE)
    @Override
    public byte[] printToTextBytes(Collection<Check> checks) throws Exception {
        var checkPrinter = new CheckPrinter(new NormalinoList<>(checks), new TextCheckPrintStrategy());
        return checkPrinter.printRaw();
    }

    @Override
    public String printToTextString(Collection<Check> checks) throws Exception {
        var checkPrinter = new CheckPrinter(new NormalinoList<>(checks), new TextCheckPrintStrategy());
        return new String(checkPrinter.printRaw());
    }

    private CheckPrinter createPdfPrinter(Collection<Check> checks, String templatePath, int templateOffset)
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
