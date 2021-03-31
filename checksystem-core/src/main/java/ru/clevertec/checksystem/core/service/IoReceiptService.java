package ru.clevertec.checksystem.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import ru.clevertec.checksystem.core.common.builder.IReceiptBuilder;
import ru.clevertec.checksystem.core.data.generate.ReceiptGenerate;
import ru.clevertec.checksystem.core.data.generate.ReceiptItemGenerate;
import ru.clevertec.checksystem.core.entity.BaseEntity;
import ru.clevertec.checksystem.core.entity.discount.receipt.ReceiptDiscount;
import ru.clevertec.checksystem.core.entity.discount.receiptitem.ReceiptItemDiscount;
import ru.clevertec.checksystem.core.entity.receipt.Receipt;
import ru.clevertec.checksystem.core.entity.receipt.ReceiptItem;
import ru.clevertec.checksystem.core.event.EventEmitter;
import ru.clevertec.checksystem.core.event.EventType;
import ru.clevertec.checksystem.core.event.MailSendListener;
import ru.clevertec.checksystem.core.event.Subscribe;
import ru.clevertec.checksystem.core.exception.EmitEventException;
import ru.clevertec.checksystem.core.helper.FormatHelpers;
import ru.clevertec.checksystem.core.io.FormatType;
import ru.clevertec.checksystem.core.io.IoType;
import ru.clevertec.checksystem.core.io.factory.*;
import ru.clevertec.checksystem.core.io.format.GenerateFormat;
import ru.clevertec.checksystem.core.io.format.PrintFormat;
import ru.clevertec.checksystem.core.io.format.StructureFormat;
import ru.clevertec.checksystem.core.io.print.layout.PdfReceiptLayout;
import ru.clevertec.checksystem.core.log.LogLevel;
import ru.clevertec.checksystem.core.log.annotation.AroundExecutionLog;
import ru.clevertec.checksystem.core.repository.EventEmailRepository;
import ru.clevertec.checksystem.core.repository.ProductRepository;
import ru.clevertec.checksystem.core.repository.ReceiptDiscountRepository;
import ru.clevertec.checksystem.core.repository.ReceiptItemDiscountRepository;
import ru.clevertec.checksystem.core.service.common.IIoReceiptService;
import ru.clevertec.checksystem.core.template.pdf.FilePdfTemplate;

import javax.mail.MessagingException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@AroundExecutionLog
@Subscribe(eventType = EventType.PrintEnd, listenerClass = MailSendListener.class)
public class IoReceiptService extends EventEmitter<Object> implements IIoReceiptService {

    private final EmailService mailService;

    private final ReceiptReaderFactory receiptReaderFactory;
    private final ReceiptWriterFactory receiptWriterFactory;
    private final ReceiptPrinterFactory receiptPrinterFactory;
    private final ReceiptGenerateWriterFactory receiptGenerateWriterFactory;
    private final ReceiptGenerateReaderFactory receiptGenerateReaderFactory;

    private final ProductRepository productRepository;
    private final EventEmailRepository eventEmailRepository;
    private final ReceiptDiscountRepository receiptDiscountRepository;
    private final ReceiptItemDiscountRepository receiptItemDiscountRepository;

    @Autowired
    public IoReceiptService(
            EmailService mailService,
            ReceiptReaderFactory receiptReaderFactory,
            ReceiptWriterFactory receiptWriterFactory,
            ReceiptPrinterFactory receiptPrinterFactory,
            ReceiptGenerateWriterFactory receiptGenerateWriterFactory,
            ReceiptGenerateReaderFactory receiptGenerateReaderFactory,
            ProductRepository productRepository,
            EventEmailRepository eventEmailRepository,
            ReceiptDiscountRepository receiptDiscountRepository,
            ReceiptItemDiscountRepository receiptItemDiscountRepository,
            ApplicationContext applicationContext) {
        super(applicationContext);
        this.mailService = mailService;
        this.receiptReaderFactory = receiptReaderFactory;
        this.receiptWriterFactory = receiptWriterFactory;
        this.receiptPrinterFactory = receiptPrinterFactory;
        this.receiptGenerateWriterFactory = receiptGenerateWriterFactory;
        this.receiptGenerateReaderFactory = receiptGenerateReaderFactory;
        this.productRepository = productRepository;
        this.eventEmailRepository = eventEmailRepository;
        this.receiptDiscountRepository = receiptDiscountRepository;
        this.receiptItemDiscountRepository = receiptItemDiscountRepository;
    }

    @Override
    public void write(Collection<Receipt> receipts, OutputStream os, FormatType formatType) throws IOException {
        switch (IoType.parse(formatType.getType())) {
            case PRINT -> print(receipts, os, PrintFormat.parse(formatType.getFormat()), false);
            case STRUCTURE -> serialize(receipts, os, StructureFormat.parse(formatType.getFormat()));
            case GENERATE -> toGenerate(receipts, os, GenerateFormat.parse(formatType.getFormat()));
        }
    }

    @Override
    public void write(Collection<Receipt> receipts, File destinationFile, FormatType formatType) throws IOException {
        switch (IoType.parse(formatType.getType())) {
            case PRINT -> print(receipts, destinationFile, PrintFormat.parse(formatType.getFormat()), false);
            case STRUCTURE -> serialize(receipts, destinationFile, StructureFormat.parse(formatType.getFormat()));
            case GENERATE -> toGenerate(receipts, destinationFile, GenerateFormat.parse(formatType.getFormat()));
        }
    }

    @Override
    public void serialize(Collection<Receipt> receipts, File destinationFile, StructureFormat structureFormat) throws IOException {
        receiptWriterFactory.instance(structureFormat).write(receipts, destinationFile);
    }

    @Override
    public void serialize(Collection<Receipt> receipts, OutputStream os, StructureFormat structureFormat) throws IOException {
        receiptWriterFactory.instance(structureFormat).write(receipts, os);
    }

    @AroundExecutionLog(level = LogLevel.OFF)
    @Override
    public Collection<Receipt> deserialize(File sourceFile, StructureFormat structureFormat) throws IOException {
        return receiptReaderFactory.instance(structureFormat).read(sourceFile);
    }

    @Override
    public Collection<Receipt> deserialize(InputStream is, StructureFormat structureFormat) throws IOException {
        return receiptReaderFactory.instance(structureFormat).read(is);
    }

    @Override
    public void print(Collection<Receipt> receipts, File destinationFile, PrintFormat printFormat) throws IOException {
        print(receipts, destinationFile, printFormat, true);
    }

    private void print(Collection<Receipt> receipts, File destinationFile, PrintFormat printFormat, boolean emitPrintOver) throws IOException {
        receiptPrinterFactory.instance(printFormat, receipts).print(destinationFile);
        if (emitPrintOver)
            emitPrintOver(receipts, destinationFile);
    }

    @Override
    public void print(Collection<Receipt> receipts, OutputStream os, PrintFormat printFormat) throws IOException {
        print(receipts, os, printFormat, true);
    }

    private void print(Collection<Receipt> receipts, OutputStream os, PrintFormat printFormat, boolean emitPrintOver) throws IOException {
        receiptPrinterFactory.instance(printFormat, receipts).print(os);
        if (emitPrintOver)
            emitPrintOver(receipts, printFormat);
    }

    private String printToHtml(Collection<Receipt> receipts, boolean emitPrintOver) throws IOException {
        var bytes = receiptPrinterFactory.instance(PrintFormat.HTML, receipts).print();
        if (emitPrintOver)
            emitPrintOver(receipts, PrintFormat.HTML);
        return new String(bytes, StandardCharsets.UTF_8);
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
        var printer = receiptPrinterFactory.instance(PrintFormat.PDF, receipts);
        ((PdfReceiptLayout) printer.getLayout())
                .setTemplate(new FilePdfTemplate(templateFile, templateTopOffset));
        printer.print(destinationFile);

        emitPrintOver(receipts, destinationFile);
    }

    @Override
    public void printWithTemplateToPdf(Collection<Receipt> receipts, OutputStream os, File templateFile, long templateTopOffset) throws IOException {
        var printer = receiptPrinterFactory.instance(PrintFormat.PDF, receipts);
        ((PdfReceiptLayout) printer.getLayout())
                .setTemplate(new FilePdfTemplate(templateFile, templateTopOffset));
        printer.print(os);

        emitPrintOver(receipts, PrintFormat.PDF);
    }

    @Override
    public void sendToEmail(
            String subject, Collection<Receipt> receipts, FormatType formatType, String... addresses) throws IOException, MessagingException {

        var attachmentFile = createTempFile(formatType.getFormat());
        writeReceiptsToFile(receipts, attachmentFile, formatType);

        var htmlBody = printToHtml(receipts, false);

        mailService.sendEmail(subject, htmlBody, true, Collections.singleton(attachmentFile), addresses);
    }

    @Override
    public Collection<ReceiptGenerate> toGenerate(Collection<Receipt> receipts) {

        var receiptGenerateArray = new ArrayList<ReceiptGenerate>();

        for (var receipt : receipts) {

            var newReceiptGenerate = new ReceiptGenerate(
                    receipt.getId(),
                    receipt.getName(),
                    receipt.getDescription(),
                    receipt.getAddress(),
                    receipt.getPhoneNumber(),
                    receipt.getCashier(),
                    receipt.getDate());

            newReceiptGenerate.setDiscountIds(getReceiptDiscountIds(receipt.getDiscounts()));
            newReceiptGenerate.setReceiptItems(getReceiptItemGenerates(receipt.getReceiptItems()));

            receiptGenerateArray.add(newReceiptGenerate);
        }

        return receiptGenerateArray;
    }

    @Override
    public void toGenerate(Collection<Receipt> receipts, File destinationFile, GenerateFormat generateFormat) throws IOException {
        var receiptGenerates = toGenerate(receipts);
        receiptGenerateWriterFactory.instance(generateFormat).write(receiptGenerates, destinationFile);
    }

    @Override
    public void toGenerate(Collection<Receipt> receipts, OutputStream os, GenerateFormat generateFormat) throws IOException {
        var receiptGenerates = toGenerate(receipts);
        receiptGenerateWriterFactory.instance(generateFormat).write(receiptGenerates, os);
    }

    @Override
    public Collection<Receipt> fromGenerate(File sourceFile, GenerateFormat generateFormat) throws IOException {
        var receiptGenerates = receiptGenerateReaderFactory.instance(generateFormat).read(sourceFile);
        return fromGenerate(receiptGenerates);
    }

    @Override
    public Collection<Receipt> fromGenerate(byte[] bytes, GenerateFormat generateFormat) throws IOException {
        var receiptGenerates = receiptGenerateReaderFactory.instance(generateFormat).read(bytes);
        return fromGenerate(receiptGenerates);
    }

    @Override
    public Collection<Receipt> fromGenerate(Collection<ReceiptGenerate> receiptGenerates) {

        var receipts = new ArrayList<Receipt>();

        for (var receiptGenerate : receiptGenerates) {

            var receiptBuilder = new Receipt.Builder()
                    .setId(receiptGenerate.getId())
                    .setName(receiptGenerate.getName())
                    .setDescription(receiptGenerate.getDescription())
                    .setAddress(receiptGenerate.getAddress())
                    .setPhoneNumber(receiptGenerate.getPhoneNumber())
                    .setCashier(receiptGenerate.getCashier())
                    .setDate(receiptGenerate.getDate());

            if (!receiptGenerate.getReceiptItemGenerates().isEmpty())
                addReceiptItemsToReceipt(receiptBuilder, receiptGenerate.getReceiptItemGenerates());

            if (!receiptGenerate.getDiscountIds().isEmpty())
                receiptBuilder.addDiscounts(StreamSupport.stream(receiptDiscountRepository.findAllById(receiptGenerate.getDiscountIds()).spliterator(), false)
                        .collect(Collectors.toList()));

            receipts.add(receiptBuilder.build());
        }

        return receipts;
    }

    private void addReceiptItemsToReceipt(IReceiptBuilder receiptBuilder, Collection<ReceiptItemGenerate> receiptItemGenerates) {

        for (var receiptItemGenerate : receiptItemGenerates) {

            var product = productRepository.findById(receiptItemGenerate.getProductId()).orElseThrow();

            var receiptItemBuilder = new ReceiptItem.Builder()
                    .setProduct(product)
                    .setQuantity(receiptItemGenerate.getQuantity());

            var discountIds = receiptItemGenerate.getDiscountIds();
            if (discountIds != null)
                receiptItemBuilder.addDiscounts(
                        StreamSupport.stream(receiptItemDiscountRepository.findAllById(receiptItemGenerate.getDiscountIds()).spliterator(), false)
                                .collect(Collectors.toList())
                );

            receiptBuilder.addItem(receiptItemBuilder.build());
        }
    }

    private Collection<Long> getReceiptDiscountIds(Collection<ReceiptDiscount> receiptDiscounts) {
        return receiptDiscounts.stream()
                .map(BaseEntity::getId)
                .collect(Collectors.toList());
    }

    private Collection<Long> getReceiptItemDiscountIds(Collection<ReceiptItemDiscount> receiptItemDiscounts) {
        return receiptItemDiscounts.stream()
                .map(BaseEntity::getId)
                .collect(Collectors.toList());
    }

    private Collection<ReceiptItemGenerate> getReceiptItemGenerates(Collection<ReceiptItem> receiptItems) {
        return receiptItems.stream().map(receiptItem ->
                new ReceiptItemGenerate(
                        receiptItem.getProduct().getId(),
                        receiptItem.getQuantity(),
                        getReceiptItemDiscountIds(receiptItem.getDiscounts())))
                .collect(Collectors.toList());
    }

    private void writeReceiptsToFile(Collection<Receipt> receipts, File file, FormatType formatType) throws IOException {
        switch (IoType.parse(formatType.getType())) {
            case PRINT -> print(receipts, file, PrintFormat.parse(formatType.getFormat()), false);
            case STRUCTURE -> serialize(receipts, file, StructureFormat.parse(formatType.getFormat()));
        }
    }

    private void emitPrintOver(Collection<Receipt> receipts, PrintFormat printFormat) throws IOException {
        var tempFile = createTempFile(printFormat.toString());
        print(receipts, tempFile, printFormat, false);
        emitPrintOver(receipts, tempFile);
    }

    private void emitPrintOver(Collection<Receipt> receipts, File... attachments) throws IOException {

        var eventEmails = eventEmailRepository.findAllByEventType(EventType.PrintEnd);

        var addresses = StreamSupport.stream(eventEmails.spliterator(), false)
                .map(a -> a.getEmail().getAddress()).toArray(String[]::new);

        var htmlBody = printToHtml(receipts, false);

        try {
            var message = mailService.createMessage(
                    "Printing is over!",
                    htmlBody,
                    true,
                    Arrays.stream(attachments).collect(Collectors.toSet()),
                    addresses);
            emit(EventType.PrintEnd, message);
        } catch (MessagingException e) {
            throw new EmitEventException(e);
        }
    }

    private static File createTempFile(String format) throws IOException {
        return File.createTempFile("receipts", FormatHelpers.extensionByFormat(format, true));
    }
}
