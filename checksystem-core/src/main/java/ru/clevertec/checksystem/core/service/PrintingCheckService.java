package ru.clevertec.checksystem.core.service;

import ru.clevertec.checksystem.core.Constants;
import ru.clevertec.checksystem.core.data.DataSeed;
import ru.clevertec.checksystem.core.event.EventTypes;
import ru.clevertec.checksystem.core.dto.Mail;
import ru.clevertec.checksystem.core.annotation.execution.AroundExecutionLog;
import ru.clevertec.checksystem.core.annotation.subscribe.Subscribe;
import ru.clevertec.checksystem.core.entity.check.Check;
import ru.clevertec.checksystem.core.common.service.IPrintingCheckService;
import ru.clevertec.checksystem.core.event.EventEmitter;
import ru.clevertec.checksystem.core.factory.io.CheckPrinterFactory;
import ru.clevertec.checksystem.core.io.print.strategy.PdfCheckPrintStrategy;
import ru.clevertec.checksystem.core.log.LogLevel;
import ru.clevertec.checksystem.core.template.pdf.FilePdfTemplate;
import ru.clevertec.checksystem.core.util.FileUtils;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Collection;

//@MailingSubscribe(eventType = EventTypes.PrintEnd)
@Subscribe(eventType = EventTypes.PrintEnd, listenerClass = MailSenderService.class)
public class PrintingCheckService extends EventEmitter<Object> implements IPrintingCheckService {

    @Override
    public void printToHtml(Collection<Check> checkCollection, File destinationFile)
            throws IllegalArgumentException, IOException {
        var checkPrinter = CheckPrinterFactory.create(Constants.Format.Print.HTML, checkCollection);
        FileUtils.writeBytesToFile(checkPrinter.printRaw(), destinationFile);
        emitPrintOver(checkCollection, destinationFile);
    }

    @Override
    public void printToHtml(Collection<Check> checkCollection, OutputStream outputStream)
            throws IllegalArgumentException, IOException {
        var checkPrinter = CheckPrinterFactory.create(Constants.Format.Print.HTML, checkCollection);
        outputStream.write(checkPrinter.printRaw());
        emitPrintOver(checkCollection);
    }

    @Override
    public String printToHtml(Collection<Check> checkCollection)
            throws IllegalArgumentException, IOException {
        var checkPrinter = CheckPrinterFactory.create(Constants.Format.Print.HTML, checkCollection);
        var html = new String(checkPrinter.printRaw());
        emitPrintOver(checkCollection);
        return html;
    }

    @Override
    public void printToPdf(Collection<Check> checkCollection, File destinationFile)
            throws IllegalArgumentException, IOException {
        var checkPrinter = CheckPrinterFactory.create(Constants.Format.Print.PDF, checkCollection);
        FileUtils.writeBytesToFile(checkPrinter.printRaw(), destinationFile);
        emitPrintOver(checkCollection, destinationFile);
    }

    @Override
    public void printToPdf(Collection<Check> checkCollection, OutputStream outputStream)
            throws IllegalArgumentException, IOException {
        var checkPrinter = CheckPrinterFactory.create(Constants.Format.Print.PDF, checkCollection);
        outputStream.write(checkPrinter.printRaw());
        emitPrintOver(checkCollection);
    }

    @Override
    public byte[] printToPdf(Collection<Check> checkCollection)
            throws IllegalArgumentException, IOException {
        var checkPrinter = CheckPrinterFactory.create(Constants.Format.Print.PDF, checkCollection);
        var pdfBytes = checkPrinter.printRaw();
        emitPrintOver(checkCollection);
        return pdfBytes;
    }

    @Override
    public void printWithTemplateToPdf(Collection<Check> checkCollection, File destinationFile, File templateFile)
            throws IllegalArgumentException, IOException {
        printWithTemplateToPdf(checkCollection, destinationFile, templateFile, 0);
    }

    @Override
    public void printWithTemplateToPdf(Collection<Check> checkCollection, OutputStream outputStream, File templateFile)
            throws IllegalArgumentException, IOException {
        printWithTemplateToPdf(checkCollection, outputStream, templateFile, 0);
    }

    @Override
    public void printWithTemplateToPdf(Collection<Check> checkCollection, File destinationFile,
                                       File templateFile, int templateTopOffset)
            throws IllegalArgumentException, IOException {

        var checkPrinter = CheckPrinterFactory.create(Constants.Format.Print.PDF, checkCollection);

        ((PdfCheckPrintStrategy) checkPrinter.getStrategy())
                .setTemplate(new FilePdfTemplate(templateFile, templateTopOffset));

        checkPrinter.printRaw(destinationFile);

        emitPrintOver(checkCollection, destinationFile);
    }

    @Override
    public void printWithTemplateToPdf(Collection<Check> checkCollection, OutputStream outputStream,
                                       File templateFile, int templateTopOffset)
            throws IllegalArgumentException, IOException {

        var checkPrinter = CheckPrinterFactory.create(Constants.Format.Print.PDF, checkCollection);

        ((PdfCheckPrintStrategy) checkPrinter.getStrategy())
                .setTemplate(new FilePdfTemplate(templateFile, templateTopOffset));

        checkPrinter.printRaw(outputStream);

        emitPrintOver(checkCollection);
    }

    @AroundExecutionLog(level = LogLevel.NONE)
    @Override
    public byte[] printWithTemplateToPdf(Collection<Check> checkCollection, File templateFile)
            throws IllegalArgumentException, IOException {

        return printWithTemplateToPdf(checkCollection, templateFile, 0);
    }

    @AroundExecutionLog(level = LogLevel.NONE)
    @Override
    public byte[] printWithTemplateToPdf(Collection<Check> checkCollection, File templateFile, int templateTopOffset)
            throws IllegalArgumentException, IOException {

        var checkPrinter = CheckPrinterFactory.create(Constants.Format.Print.PDF, checkCollection);

        ((PdfCheckPrintStrategy) checkPrinter.getStrategy())
                .setTemplate(new FilePdfTemplate(templateFile, templateTopOffset));

        var pdfData = checkPrinter.printRaw();

        emitPrintOver(checkCollection);

        return pdfData;
    }

    @Override
    public void printToText(Collection<Check> checkCollection, File destinationFile)
            throws IllegalArgumentException, IOException {
        var checkPrinter = CheckPrinterFactory.create(Constants.Format.Print.TEXT, checkCollection);
        FileUtils.writeBytesToFile(checkPrinter.printRaw(), destinationFile);
        emitPrintOver(checkCollection, destinationFile);
    }

    @Override
    public void printToText(Collection<Check> checkCollection, OutputStream outputStream)
            throws IllegalArgumentException, IOException {
        var checkPrinter = CheckPrinterFactory.create(Constants.Format.Print.TEXT, checkCollection);
        outputStream.write(checkPrinter.printRaw());
        emitPrintOver(checkCollection);
    }

    @Override
    public String printToText(Collection<Check> checkCollection)
            throws IllegalArgumentException, IOException {
        var checkPrinter = CheckPrinterFactory.create(Constants.Format.Print.TEXT, checkCollection);
        var text = new String(checkPrinter.printRaw());
        emitPrintOver(checkCollection);
        return text;
    }

    private void emitPrintOver(Collection<Check> checkCollection, File... files) throws IOException {
        var checkPrinter = CheckPrinterFactory.create(Constants.Format.Print.HTML, checkCollection);
        var html = new String(checkPrinter.printRaw());
        emit(EventTypes.PrintEnd, new Mail("Printing is over!", html, DataSeed.emailAddresses(), files));
    }
}
