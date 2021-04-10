package ru.clevertec.checksystem.webapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.clevertec.checksystem.core.helper.FormatHelpers;
import ru.clevertec.checksystem.core.io.FormatType;
import ru.clevertec.checksystem.core.repository.ReceiptRepository;
import ru.clevertec.checksystem.core.service.common.IIoReceiptService;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(value = "/files", produces = MediaType.APPLICATION_JSON_VALUE)
public class FileController {

    private final IIoReceiptService ioReceiptService;
    private final ReceiptRepository receiptRepository;

    @Autowired
    public FileController(IIoReceiptService ioReceiptService, ReceiptRepository receiptRepository) {
        this.ioReceiptService = ioReceiptService;
        this.receiptRepository = receiptRepository;
    }

    @GetMapping
    public ResponseEntity<Resource> download(@RequestParam("formatType") String formatType, @RequestParam("id") List<Long> ids) throws IOException {

        var formatTypeEnum = FormatType.parse(formatType);

        var os = new ByteArrayOutputStream();
        ioReceiptService.write(receiptRepository.findAllById(ids), os, formatTypeEnum);

        var headers = new HttpHeaders();
        headers.set("Content-Disposition", "attachment; filename=receipts" + FormatHelpers.extensionByFormat(formatTypeEnum.getFormat(), true));

        var resource = new ByteArrayResource(os.toByteArray());

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(os.size())
                .contentType(MediaType.parseMediaType(FormatHelpers.contentTypeByFormat(formatTypeEnum.getFormat())))
                .body(resource);
    }
}
