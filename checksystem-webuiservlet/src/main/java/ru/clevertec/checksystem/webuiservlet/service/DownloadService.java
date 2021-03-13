package ru.clevertec.checksystem.webuiservlet.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.clevertec.checksystem.core.common.service.IIoReceiptService;
import ru.clevertec.checksystem.core.common.service.IPrintingReceiptService;
import ru.clevertec.checksystem.core.entity.receipt.Receipt;
import ru.clevertec.checksystem.core.factory.service.ServiceFactory;
import ru.clevertec.checksystem.core.helper.FormatHelpers;
import ru.clevertec.checksystem.core.service.IoReceiptService;
import ru.clevertec.checksystem.core.service.PrintingReceiptService;
import ru.clevertec.checksystem.core.util.CollectionUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

import static ru.clevertec.checksystem.core.Constants.Types;

@Component
public class DownloadService {

    private final ServiceFactory serviceFactory;

    @Autowired
    public DownloadService(ServiceFactory serviceFactory) {
        this.serviceFactory = serviceFactory;
    }

    public boolean download(HttpServletResponse resp, Collection<Receipt> receipts, String type, String format) throws IOException {
        setHeaders(resp, format);
        switch (type) {
            case Types.PRINT -> print(resp, CollectionUtils.asCollection(receipts), format);
            case Types.SERIALIZE -> serialize(resp, CollectionUtils.asCollection(receipts), format);
            default -> {
                return false;
            }
        }
        return true;
    }

    private void print(HttpServletResponse resp, Collection<Receipt> receipts, String format) throws IOException {
        IPrintingReceiptService printingReceiptService = serviceFactory.instance(PrintingReceiptService.class);
        printingReceiptService.print(receipts, resp.getOutputStream(), format);
    }

    private void serialize(HttpServletResponse resp, Collection<Receipt> receipts, String format) throws IOException {
        IIoReceiptService ioCheckService = serviceFactory.instance(IoReceiptService.class);
        ioCheckService.serialize(receipts, resp.getOutputStream(), format);
    }

    private static void setHeaders(HttpServletResponse resp, String format) {
        resp.setContentType(FormatHelpers.contentTypeByFormat(format));
        resp.setHeader("Content-disposition", "attachment; filename=receipts"
                + FormatHelpers.extensionByFormat(format, true));
    }
}
