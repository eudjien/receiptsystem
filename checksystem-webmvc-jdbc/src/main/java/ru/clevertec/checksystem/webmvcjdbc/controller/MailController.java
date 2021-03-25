package ru.clevertec.checksystem.webmvcjdbc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.clevertec.checksystem.core.io.FormatType;
import ru.clevertec.checksystem.core.service.IIoReceiptService;
import ru.clevertec.checksystem.webmvcjdbc.ReceiptDataSource;
import ru.clevertec.checksystem.webmvcjdbc.constant.Parameters;
import ru.clevertec.checksystem.webmvcjdbc.model.MailModel;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/mail")
public class MailController {

    private final IIoReceiptService ioReceiptService;
    private final ReceiptDataSource receiptDataSource;

    @Autowired
    public MailController(IIoReceiptService ioReceiptService, ReceiptDataSource receiptDataSource) {
        this.ioReceiptService = ioReceiptService;
        this.receiptDataSource = receiptDataSource;
    }

    @GetMapping
    public ResponseEntity<String> mail(@Valid MailModel mailModel, @RequestParam(name = Parameters.ID_PARAMETER) List<Long> ids) throws IOException, MessagingException {
        var receipts = receiptDataSource.findAllById(mailModel.getSource(), ids);
        var formatType = FormatType.parse(mailModel.getFormatType());
        ioReceiptService.sendToEmail(mailModel.getSubject(), receipts, formatType, mailModel.getEmail());
        return ResponseEntity.ok("Mail was sent successfully.");
    }
}
