package ru.clevertec.checksystem.webuiservlet.servlet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import ru.clevertec.checksystem.core.helper.FormatHelpers;
import ru.clevertec.checksystem.core.io.FormatType;
import ru.clevertec.checksystem.core.repository.ReceiptRepository;
import ru.clevertec.checksystem.core.service.IIoReceiptService;
import ru.clevertec.checksystem.webuiservlet.ReceiptDataSource;
import ru.clevertec.checksystem.webuiservlet.constant.Parameters;
import ru.clevertec.checksystem.webuiservlet.constant.Servlets;
import ru.clevertec.checksystem.webuiservlet.constant.Sources;
import ru.clevertec.checksystem.webuiservlet.constant.UrlPatterns;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
@WebServlet(
        name = Servlets.DOWNLOAD_SERVLET,
        urlPatterns = UrlPatterns.DOWNLOAD_PATTERN
)
public class DownloadServlet extends ApplicationServlet {

    private ReceiptRepository receiptRepository;
    private IIoReceiptService ioReceiptService;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, config.getServletContext());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        try {
            validate(req, Parameters.SOURCE_PARAMETER, Parameters.FORMAT_TYPE_PARAMETER);
        } catch (RuntimeException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
            return;
        }

        var source = req.getParameter(Parameters.SOURCE_PARAMETER) != null
                ? req.getParameter(Parameters.SOURCE_PARAMETER)
                : Sources.DATABASE;
        var formatType = req.getParameter(Parameters.FORMAT_TYPE_PARAMETER);
        var ids = getReceiptIds(req);

        var receipts = new ReceiptDataSource(receiptRepository, req.getSession())
                .findAllById(source, ids);

        if (receipts == null || receipts.size() == 0) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        var _formatType = FormatType.parse(formatType);
        setHeaders(resp, _formatType.getFormat());

        try {
            ioReceiptService.write(receipts, resp.getOutputStream(), _formatType);
        } catch (Exception e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        resp.setStatus(HttpServletResponse.SC_OK);
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
    public void setIoReceiptService(IIoReceiptService ioReceiptService) {
        this.ioReceiptService = ioReceiptService;
    }

    private static void setHeaders(HttpServletResponse resp, String format) {
        resp.setContentType(FormatHelpers.contentTypeByFormat(format));
        resp.setHeader("Content-disposition", "attachment; filename=receipts"
                + FormatHelpers.extensionByFormat(format, true));
    }
}
