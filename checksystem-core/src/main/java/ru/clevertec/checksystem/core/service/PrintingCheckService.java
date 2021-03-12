package ru.clevertec.checksystem.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.clevertec.checksystem.core.Constants.Format;
import ru.clevertec.checksystem.core.annotation.execution.AroundExecutionLog;
import ru.clevertec.checksystem.core.annotation.subscribe.Subscribe;
import ru.clevertec.checksystem.core.common.service.IPrintingCheckService;
import ru.clevertec.checksystem.core.data.Mail;
import ru.clevertec.checksystem.core.entity.check.Check;
import ru.clevertec.checksystem.core.event.EmailSender;
import ru.clevertec.checksystem.core.event.EventEmitter;
import ru.clevertec.checksystem.core.event.EventType;
import ru.clevertec.checksystem.core.factory.io.CheckPrinterFactory;
import ru.clevertec.checksystem.core.helper.FormatHelpers;
import ru.clevertec.checksystem.core.io.print.layout.PdfCheckLayout;
import ru.clevertec.checksystem.core.log.LogLevel;
import ru.clevertec.checksystem.core.repository.EventEmailRepository;
import ru.clevertec.checksystem.core.template.pdf.FilePdfTemplate;
import ru.clevertec.checksystem.core.util.CollectionUtils;
import ru.clevertec.custom.list.SinglyLinkedList;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Collection;
import java.util.stream.Collectors;

@Subscribe(eventType = EventType.PrintEnd, listenerClass = EmailSender.class)
@Service
public class PrintingCheckService extends EventEmitter<Object> implements IPrintingCheckService {

    private final CheckPrinterFactory checkPrinterFactory;
    private final EventEmailRepository eventEmailRepository;

    @Autowired
    public PrintingCheckService(CheckPrinterFactory checkPrinterFactory, EventEmailRepository eventEmailRepository) {
        this.checkPrinterFactory = checkPrinterFactory;
        this.eventEmailRepository = eventEmailRepository;
    }

    @Override
    public void print(Collection<Check> checks, File destinationFile, String format) throws IOException {
        checkPrinterFactory.instance(format, checks).print(destinationFile);
        emitPrintOver(checks, destinationFile);
    }

    @Override
    public void print(Collection<Check> checks, OutputStream os, String format) throws IOException {
        checkPrinterFactory.instance(format, checks).print(os);
        emitPrintOver(checks, format);
    }

    @Override
    public void printToHtml(Collection<Check> checks, File destinationFile) throws IOException {
        checkPrinterFactory.instance(Format.HTML, checks).print(destinationFile);
        emitPrintOver(checks, destinationFile);
    }

    @Override
    public void printToHtml(Collection<Check> checks, OutputStream os) throws IOException {
        checkPrinterFactory.instance(Format.HTML, checks).print(os);
        emitPrintOver(checks, Format.HTML);
    }

    @Override
    public String printToHtml(Collection<Check> checks) throws IOException {
        var bytes = checkPrinterFactory.instance(Format.HTML, checks).print();
        emitPrintOver(checks, Format.HTML);
        return new String(bytes);
    }

    @Override
    public void printToPdf(Collection<Check> checks, File destinationFile) throws IOException {
        checkPrinterFactory.instance(Format.PDF, checks).print(destinationFile);
        emitPrintOver(checks, destinationFile);
    }

    @Override
    public void printToPdf(Collection<Check> checks, OutputStream os) throws IOException {
        checkPrinterFactory.instance(Format.PDF, checks).print(os);
        emitPrintOver(checks, Format.PDF);
    }

    @Override
    public byte[] printToPdf(Collection<Check> checks) throws IOException {
        var bytes = checkPrinterFactory.instance(Format.PDF, checks).print();
        emitPrintOver(checks, Format.PDF);
        return bytes;
    }

    @Override
    public void printWithTemplateToPdf(Collection<Check> checks, File destinationFile, File templateFile) throws IOException {
        printWithTemplateToPdf(checks, destinationFile, templateFile, 0);
    }

    @Override
    public void printWithTemplateToPdf(Collection<Check> checks, OutputStream os, File templateFile) throws IOException {
        printWithTemplateToPdf(checks, os, templateFile, 0);
    }

    @Override
    public void printWithTemplateToPdf(Collection<Check> checks, File destinationFile, File templateFile, int templateTopOffset) throws IOException {
        var printer = checkPrinterFactory.instance(Format.PDF, checks);
        ((PdfCheckLayout) printer.getLayout())
                .setTemplate(new FilePdfTemplate(templateFile, templateTopOffset));
        printer.print(destinationFile);

        emitPrintOver(checks, destinationFile);
    }

    @Override
    public void printWithTemplateToPdf(Collection<Check> checks, OutputStream os, File templateFile, int templateTopOffset) throws IOException {
        var printer = checkPrinterFactory.instance(Format.PDF, checks);
        ((PdfCheckLayout) printer.getLayout())
                .setTemplate(new FilePdfTemplate(templateFile, templateTopOffset));
        printer.print(os);

        emitPrintOver(checks, Format.PDF);
    }

    @AroundExecutionLog(level = LogLevel.NONE)
    @Override
    public byte[] printWithTemplateToPdf(Collection<Check> checks, File templateFile) throws IOException {
        return printWithTemplateToPdf(checks, templateFile, 0);
    }

    @AroundExecutionLog(level = LogLevel.NONE)
    @Override
    public byte[] printWithTemplateToPdf(Collection<Check> checks, File templateFile, int templateTopOffset) throws IOException {
        var checkPrinter = checkPrinterFactory.instance(Format.PDF, checks);
        ((PdfCheckLayout) checkPrinter.getLayout())
                .setTemplate(new FilePdfTemplate(templateFile, templateTopOffset));
        var bytes = checkPrinter.print();

        emitPrintOver(checks, Format.PDF);

        return bytes;
    }

    @Override
    public void printToText(Collection<Check> checks, File destinationFile) throws IOException {
        checkPrinterFactory.instance(Format.TEXT, checks).print(destinationFile);
        emitPrintOver(checks, destinationFile);
    }

    @Override
    public void printToText(Collection<Check> checks, OutputStream os) throws IOException {
        checkPrinterFactory.instance(Format.TEXT, checks).print(os);
        emitPrintOver(checks);
    }

    @Override
    public String printToText(Collection<Check> checks) throws IOException {
        var bytes = checkPrinterFactory.instance(Format.TEXT, checks).print();
        emitPrintOver(checks, Format.TEXT);
        return new String(bytes);
    }

    private void emitPrintOver(Collection<Check> checks, String format) throws IOException {

        var tmpFile = File.createTempFile("checks", FormatHelpers.extensionByFormat(format, true));

        try (var fos = new FileOutputStream(tmpFile)) {
            checkPrinterFactory.instance(format, checks).print(fos);
        }

        emitPrintOver(checks, tmpFile);
    }

    private void emitPrintOver(Collection<Check> checks, File... files) throws IOException {

        var bytes = checkPrinterFactory.instance(Format.HTML, checks).print();
        var html = new String(bytes);

        var eventEmails = eventEmailRepository.findAllByEventType(EventType.PrintEnd);

        var addresses = CollectionUtils.asCollection(eventEmails).stream()
                .map(a -> a.getEmail().toInternetAddress())
                .collect(Collectors.toCollection(SinglyLinkedList::new));

        emit(EventType.PrintEnd, new Mail("Printing is over!", html, addresses, files));
    }
}
