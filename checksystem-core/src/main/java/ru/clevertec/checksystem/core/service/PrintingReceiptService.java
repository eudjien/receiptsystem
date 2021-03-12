package ru.clevertec.checksystem.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.clevertec.checksystem.core.Constants.Format;
import ru.clevertec.checksystem.core.annotation.execution.AroundExecutionLog;
import ru.clevertec.checksystem.core.annotation.subscribe.Subscribe;
import ru.clevertec.checksystem.core.common.service.IPrintingReceiptService;
import ru.clevertec.checksystem.core.data.Mail;
import ru.clevertec.checksystem.core.entity.receipt.Receipt;
import ru.clevertec.checksystem.core.event.EmailSender;
import ru.clevertec.checksystem.core.event.EventEmitter;
import ru.clevertec.checksystem.core.event.EventType;
import ru.clevertec.checksystem.core.factory.io.ReceiptPrinterFactory;
import ru.clevertec.checksystem.core.helper.FormatHelpers;
import ru.clevertec.checksystem.core.io.print.layout.PdfReceiptLayout;
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
public class PrintingReceiptService extends EventEmitter<Object> implements IPrintingReceiptService {

    private final ReceiptPrinterFactory receiptPrinterFactory;
    private final EventEmailRepository eventEmailRepository;

    @Autowired
    public PrintingReceiptService(ReceiptPrinterFactory receiptPrinterFactory, EventEmailRepository eventEmailRepository) {
        this.receiptPrinterFactory = receiptPrinterFactory;
        this.eventEmailRepository = eventEmailRepository;
    }

    @Override
    public void print(Collection<Receipt> receipts, File destinationFile, String format) throws IOException {
        receiptPrinterFactory.instance(format, receipts).print(destinationFile);
        emitPrintOver(receipts, destinationFile);
    }

    @Override
    public void print(Collection<Receipt> receipts, OutputStream os, String format) throws IOException {
        receiptPrinterFactory.instance(format, receipts).print(os);
        emitPrintOver(receipts, format);
    }

    @Override
    public void printToHtml(Collection<Receipt> receipts, File destinationFile) throws IOException {
        receiptPrinterFactory.instance(Format.HTML, receipts).print(destinationFile);
        emitPrintOver(receipts, destinationFile);
    }

    @Override
    public void printToHtml(Collection<Receipt> receipts, OutputStream os) throws IOException {
        receiptPrinterFactory.instance(Format.HTML, receipts).print(os);
        emitPrintOver(receipts, Format.HTML);
    }

    @Override
    public String printToHtml(Collection<Receipt> receipts) throws IOException {
        var bytes = receiptPrinterFactory.instance(Format.HTML, receipts).print();
        emitPrintOver(receipts, Format.HTML);
        return new String(bytes);
    }

    @Override
    public void printToPdf(Collection<Receipt> receipts, File destinationFile) throws IOException {
        receiptPrinterFactory.instance(Format.PDF, receipts).print(destinationFile);
        emitPrintOver(receipts, destinationFile);
    }

    @Override
    public void printToPdf(Collection<Receipt> receipts, OutputStream os) throws IOException {
        receiptPrinterFactory.instance(Format.PDF, receipts).print(os);
        emitPrintOver(receipts, Format.PDF);
    }

    @Override
    public byte[] printToPdf(Collection<Receipt> receipts) throws IOException {
        var bytes = receiptPrinterFactory.instance(Format.PDF, receipts).print();
        emitPrintOver(receipts, Format.PDF);
        return bytes;
    }

    @Override
    public void printWithTemplateToPdf(Collection<Receipt> receipts, File destinationFile, File templateFile) throws IOException {
        printWithTemplateToPdf(receipts, destinationFile, templateFile, 0);
    }

    @Override
    public void printWithTemplateToPdf(Collection<Receipt> receipts, OutputStream os, File templateFile) throws IOException {
        printWithTemplateToPdf(receipts, os, templateFile, 0);
    }

    @Override
    public void printWithTemplateToPdf(Collection<Receipt> receipts, File destinationFile, File templateFile, long templateTopOffset) throws IOException {
        var printer = receiptPrinterFactory.instance(Format.PDF, receipts);
        ((PdfReceiptLayout) printer.getLayout())
                .setTemplate(new FilePdfTemplate(templateFile, templateTopOffset));
        printer.print(destinationFile);

        emitPrintOver(receipts, destinationFile);
    }

    @Override
    public void printWithTemplateToPdf(Collection<Receipt> receipts, OutputStream os, File templateFile, long templateTopOffset) throws IOException {
        var printer = receiptPrinterFactory.instance(Format.PDF, receipts);
        ((PdfReceiptLayout) printer.getLayout())
                .setTemplate(new FilePdfTemplate(templateFile, templateTopOffset));
        printer.print(os);

        emitPrintOver(receipts, Format.PDF);
    }

    @AroundExecutionLog(level = LogLevel.NONE)
    @Override
    public byte[] printWithTemplateToPdf(Collection<Receipt> receipts, File templateFile) throws IOException {
        return printWithTemplateToPdf(receipts, templateFile, 0);
    }

    @AroundExecutionLog(level = LogLevel.NONE)
    @Override
    public byte[] printWithTemplateToPdf(Collection<Receipt> receipts, File templateFile, long templateTopOffset) throws IOException {
        var receiptPrinter = receiptPrinterFactory.instance(Format.PDF, receipts);
        ((PdfReceiptLayout) receiptPrinter.getLayout())
                .setTemplate(new FilePdfTemplate(templateFile, templateTopOffset));
        var bytes = receiptPrinter.print();

        emitPrintOver(receipts, Format.PDF);

        return bytes;
    }

    @Override
    public void printToText(Collection<Receipt> receipts, File destinationFile) throws IOException {
        receiptPrinterFactory.instance(Format.TEXT, receipts).print(destinationFile);
        emitPrintOver(receipts, destinationFile);
    }

    @Override
    public void printToText(Collection<Receipt> receipts, OutputStream os) throws IOException {
        receiptPrinterFactory.instance(Format.TEXT, receipts).print(os);
        emitPrintOver(receipts);
    }

    @Override
    public String printToText(Collection<Receipt> receipts) throws IOException {
        var bytes = receiptPrinterFactory.instance(Format.TEXT, receipts).print();
        emitPrintOver(receipts, Format.TEXT);
        return new String(bytes);
    }

    private void emitPrintOver(Collection<Receipt> receipts, String format) throws IOException {

        var tmpFile = File.createTempFile("receipts", FormatHelpers.extensionByFormat(format, true));

        try (var fos = new FileOutputStream(tmpFile)) {
            receiptPrinterFactory.instance(format, receipts).print(fos);
        }

        emitPrintOver(receipts, tmpFile);
    }

    private void emitPrintOver(Collection<Receipt> receipts, File... files) throws IOException {

        var bytes = receiptPrinterFactory.instance(Format.HTML, receipts).print();
        var html = new String(bytes);

        var eventEmails = eventEmailRepository.findAllByEventType(EventType.PrintEnd);

        var addresses = CollectionUtils.asCollection(eventEmails).stream()
                .map(a -> a.getEmail().toInternetAddress())
                .collect(Collectors.toCollection(SinglyLinkedList::new));

        emit(EventType.PrintEnd, new Mail("Printing is over!", html, addresses, files));
    }
}
