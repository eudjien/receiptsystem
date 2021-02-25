package ru.clevertec.checksystem.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.clevertec.checksystem.core.Constants;
import ru.clevertec.checksystem.core.annotation.execution.AroundExecutionLog;
import ru.clevertec.checksystem.core.annotation.subscribe.Subscribe;
import ru.clevertec.checksystem.core.common.service.IPrintingCheckService;
import ru.clevertec.checksystem.core.data.Mail;
import ru.clevertec.checksystem.core.entity.check.Check;
import ru.clevertec.checksystem.core.event.EmailSender;
import ru.clevertec.checksystem.core.event.EventEmitter;
import ru.clevertec.checksystem.core.event.EventType;
import ru.clevertec.checksystem.core.factory.io.CheckPrinterFactory;
import ru.clevertec.checksystem.core.io.print.layout.PdfCheckLayout;
import ru.clevertec.checksystem.core.log.LogLevel;
import ru.clevertec.checksystem.core.repository.EventEmailRepository;
import ru.clevertec.checksystem.core.template.pdf.FilePdfTemplate;
import ru.clevertec.checksystem.core.util.CollectionUtils;
import ru.clevertec.checksystem.core.util.FileUtils;
import ru.clevertec.custom.list.SinglyLinkedList;

import java.io.File;
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
    public void printToHtml(Collection<Check> checks, File destinationFile) throws IOException {
        var checkPrinter = checkPrinterFactory.create(Constants.Format.Print.HTML, checks);
        FileUtils.writeBytesToFile(checkPrinter.print(), destinationFile);
        emitPrintOver(checks, destinationFile);
    }

    @Override
    public void printToHtml(Collection<Check> checks, OutputStream outputStream) throws IOException {
        var checkPrinter = checkPrinterFactory.create(Constants.Format.Print.HTML, checks);
        outputStream.write(checkPrinter.print());
        emitPrintOver(checks);
    }

    @Override
    public String printToHtml(Collection<Check> checks) throws IOException {
        var checkPrinter = checkPrinterFactory.create(Constants.Format.Print.HTML, checks);
        var html = new String(checkPrinter.print());
        emitPrintOver(checks);
        return html;
    }

    @Override
    public void printToPdf(Collection<Check> checks, File destinationFile) throws IOException {
        var checkPrinter = checkPrinterFactory.create(Constants.Format.Print.PDF, checks);
        FileUtils.writeBytesToFile(checkPrinter.print(), destinationFile);
        emitPrintOver(checks, destinationFile);
    }

    @Override
    public void printToPdf(Collection<Check> checks, OutputStream outputStream) throws IOException {
        var checkPrinter = checkPrinterFactory.create(Constants.Format.Print.PDF, checks);
        outputStream.write(checkPrinter.print());
        emitPrintOver(checks);
    }

    @Override
    public byte[] printToPdf(Collection<Check> checks) throws IOException {
        var checkPrinter = checkPrinterFactory.create(Constants.Format.Print.PDF, checks);
        var pdfBytes = checkPrinter.print();
        emitPrintOver(checks);
        return pdfBytes;
    }

    @Override
    public void printWithTemplateToPdf(Collection<Check> checks, File destinationFile, File templateFile) throws IOException {
        printWithTemplateToPdf(checks, destinationFile, templateFile, 0);
    }

    @Override
    public void printWithTemplateToPdf(Collection<Check> checks, OutputStream outputStream, File templateFile) throws IOException {
        printWithTemplateToPdf(checks, outputStream, templateFile, 0);
    }

    @Override
    public void printWithTemplateToPdf(Collection<Check> checks, File destinationFile, File templateFile, int templateTopOffset) throws IOException {

        var checkPrinter = checkPrinterFactory.create(Constants.Format.Print.PDF, checks);

        ((PdfCheckLayout) checkPrinter.getLayout())
                .setTemplate(new FilePdfTemplate(templateFile, templateTopOffset));

        checkPrinter.print(destinationFile);

        emitPrintOver(checks, destinationFile);
    }

    @Override
    public void printWithTemplateToPdf(Collection<Check> checks, OutputStream outputStream, File templateFile, int templateTopOffset) throws IOException {

        var checkPrinter = checkPrinterFactory.create(Constants.Format.Print.PDF, checks);

        ((PdfCheckLayout) checkPrinter.getLayout())
                .setTemplate(new FilePdfTemplate(templateFile, templateTopOffset));

        checkPrinter.print(outputStream);

        emitPrintOver(checks);
    }

    @AroundExecutionLog(level = LogLevel.NONE)
    @Override
    public byte[] printWithTemplateToPdf(Collection<Check> checks, File templateFile) throws IOException {
        return printWithTemplateToPdf(checks, templateFile, 0);
    }

    @AroundExecutionLog(level = LogLevel.NONE)
    @Override
    public byte[] printWithTemplateToPdf(Collection<Check> checks, File templateFile, int templateTopOffset) throws IOException {

        var checkPrinter = checkPrinterFactory.create(Constants.Format.Print.PDF, checks);

        ((PdfCheckLayout) checkPrinter.getLayout())
                .setTemplate(new FilePdfTemplate(templateFile, templateTopOffset));

        var pdfData = checkPrinter.print();

        emitPrintOver(checks);

        return pdfData;
    }

    @Override
    public void printToText(Collection<Check> checks, File destinationFile) throws IOException {
        var checkPrinter = checkPrinterFactory.create(Constants.Format.Print.TEXT, checks);
        FileUtils.writeBytesToFile(checkPrinter.print(), destinationFile);
        emitPrintOver(checks, destinationFile);
    }

    @Override
    public void printToText(Collection<Check> checks, OutputStream outputStream) throws IOException {
        var checkPrinter = checkPrinterFactory.create(Constants.Format.Print.TEXT, checks);
        outputStream.write(checkPrinter.print());
        emitPrintOver(checks);
    }

    @Override
    public String printToText(Collection<Check> checks) throws IOException {
        var checkPrinter = checkPrinterFactory.create(Constants.Format.Print.TEXT, checks);
        var text = new String(checkPrinter.print());
        emitPrintOver(checks);
        return text;
    }

    private void emitPrintOver(Collection<Check> checks, File... files) throws IOException {
        var checkPrinter = checkPrinterFactory.create(Constants.Format.Print.HTML, checks);
        var html = new String(checkPrinter.print());

        var eventEmails = eventEmailRepository.findAllByEventType(EventType.PrintEnd);

        var addresses = CollectionUtils.asCollection(eventEmails).stream()
                .map(a -> a.getEmail().toInternetAddress())
                .collect(Collectors.toCollection(SinglyLinkedList::new));

        emit(EventType.PrintEnd, new Mail("Printing is over!", html, addresses, files));
    }
}
