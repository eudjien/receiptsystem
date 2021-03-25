package ru.clevertec.checksystem.webmvcjdbc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import ru.clevertec.checksystem.core.helper.FormatHelpers;
import ru.clevertec.checksystem.core.io.FormatType;
import ru.clevertec.checksystem.core.io.format.StructureFormat;
import ru.clevertec.checksystem.core.service.IIoReceiptService;
import ru.clevertec.checksystem.webmvcjdbc.ReceiptDataSource;
import ru.clevertec.checksystem.webmvcjdbc.constant.Parameters;
import ru.clevertec.checksystem.webmvcjdbc.constant.Sessions;
import ru.clevertec.checksystem.webmvcjdbc.model.DownloadModel;

import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/files")
public class FileController {

    private static final String CHECKS_FILE_PART_NAME = "checksFile";

    private final IIoReceiptService ioReceiptService;
    private final ReceiptDataSource receiptDataSource;

    @Autowired
    public FileController(IIoReceiptService ioReceiptService, ReceiptDataSource receiptDataSource) {
        this.ioReceiptService = ioReceiptService;
        this.receiptDataSource = receiptDataSource;
    }

    @GetMapping("/download")
    public ResponseEntity<Resource> download(@Valid DownloadModel downloadModel, @RequestParam(name = Parameters.ID_PARAMETER) List<Long> ids) throws IOException {

        var receipts = receiptDataSource.findAllById(downloadModel.getSource(), ids);
        var formatType = FormatType.parse(downloadModel.getFormatType());

        var os = new ByteArrayOutputStream();
        ioReceiptService.write(receipts, os, formatType);

        var headers = new HttpHeaders();
        headers.set("Content-Disposition", "attachment; filename=receipts"
                + FormatHelpers.extensionByFormat(formatType.getFormat(), true));

        var resource = new ByteArrayResource(os.toByteArray());

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(os.size())
                .contentType(MediaType.parseMediaType(FormatHelpers.contentTypeByFormat(formatType.getFormat())))
                .body(resource);
    }

    @PostMapping("/upload")
    public ModelAndView upload(
            HttpSession httpSession,
            @RequestParam(CHECKS_FILE_PART_NAME) MultipartFile file,
            @RequestParam(Parameters.RETURN_URL_PARAMETER) String returnUrl) throws ServletException, IOException {
        var format = FormatHelpers.formatByContentType(file.getContentType());
        var receipts = ioReceiptService.deserialize(file.getInputStream(), StructureFormat.parse(format));
        httpSession.setAttribute(Sessions.RECEIPTS_SESSION, receipts);
        return new ModelAndView("redirect:" + returnUrl);
    }
}
