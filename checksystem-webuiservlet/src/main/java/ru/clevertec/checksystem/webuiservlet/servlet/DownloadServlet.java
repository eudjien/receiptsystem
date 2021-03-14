package ru.clevertec.checksystem.webuiservlet.servlet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import ru.clevertec.checksystem.core.repository.ReceiptRepository;
import ru.clevertec.checksystem.core.util.CollectionUtils;
import ru.clevertec.checksystem.webuiservlet.ReceiptDataSource;
import ru.clevertec.checksystem.webuiservlet.service.DownloadService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static ru.clevertec.checksystem.webuiservlet.Constants.*;

@Component
@WebServlet(
        name = ServletNames.DOWNLOAD_SERVLET,
        urlPatterns = UrlPatterns.DOWNLOAD_PATTERN
)
public class DownloadServlet extends ApplicationServlet {

    private ReceiptRepository receiptRepository;
    private DownloadService downloadService;

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

        if (!downloadService.download(resp, receipts, type, format)) {
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
    public void setDownloadService(DownloadService downloadService) {
        this.downloadService = downloadService;
    }
}
