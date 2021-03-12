package ru.clevertec.checksystem.webuiservlet.servlet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import ru.clevertec.checksystem.core.common.service.IIoReceiptService;
import ru.clevertec.checksystem.core.common.service.IPrintingReceiptService;
import ru.clevertec.checksystem.core.entity.receipt.Receipt;
import ru.clevertec.checksystem.core.factory.service.ServiceFactory;
import ru.clevertec.checksystem.core.helper.FormatHelpers;
import ru.clevertec.checksystem.core.repository.ReceiptRepository;
import ru.clevertec.checksystem.core.service.IoReceiptService;
import ru.clevertec.checksystem.core.service.PrintingReceiptService;
import ru.clevertec.checksystem.core.util.CollectionUtils;
import ru.clevertec.checksystem.webuiservlet.ReceiptDataSource;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static ru.clevertec.checksystem.core.Constants.Types;
import static ru.clevertec.checksystem.webuiservlet.Constants.*;

@Component
@WebServlet(
        name = ServletNames.DOWNLOAD_SERVLET,
        urlPatterns = UrlPatterns.DOWNLOAD_PATTERN
)
public class DownloadServlet extends ApplicationServlet {

    private ReceiptRepository receiptRepository;
    private ServiceFactory serviceFactory;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, config.getServletContext());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        try {
            verifyForRequired(req, Parameters.TYPE_PARAMETER, Parameters.FORMAT_PARAMETER);
            verifyForSuitable(req, Parameters.TYPE_PARAMETER, Parameters.SOURCE_PARAMETER, Parameters.FORMAT_PARAMETER);
        } catch (RuntimeException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
            return;
        }

        var source = req.getParameter(Parameters.SOURCE_PARAMETER) != null
                ? req.getParameter(Parameters.SOURCE_PARAMETER)
                : Sources.DATABASE;
        var type = req.getParameter(Parameters.TYPE_PARAMETER);
        var format = req.getParameter(Parameters.FORMAT_PARAMETER);
        var ids = getReceiptIds(req);

        var receipts = new ReceiptDataSource(receiptRepository, req.getSession(), Sessions.RECEIPTS_SESSION)
                .findAllById(source, ids);

        if (CollectionUtils.isNullOrEmpty(receipts)) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        setHeaders(resp, format);

        if (!download(resp, type, format, receipts)) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        resp.setStatus(HttpServletResponse.SC_OK);
    }

    private boolean download(HttpServletResponse resp, String type, String format, Collection<Receipt> receipts) throws IOException {
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
        resp.setHeader("Content-disposition", "attachment; filename=checks"
                + FormatHelpers.extensionByFormat(format, true));
    }

    private static List<Long> getReceiptIds(HttpServletRequest req) {
        return Arrays.stream(req.getParameterMap().get(Parameters.ID_PARAMETER))
                .mapToLong(Long::parseLong)
                .boxed()
                .collect(Collectors.toList());
    }

    @Autowired
    public void setCheckRepository(ReceiptRepository receiptRepository) {
        this.receiptRepository = receiptRepository;
    }

    @Autowired
    public void setServiceFactory(ServiceFactory serviceFactory) {
        this.serviceFactory = serviceFactory;
    }
}
