package ru.clevertec.checksystem.webuiservlet.servlet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import ru.clevertec.checksystem.core.io.FormatType;
import ru.clevertec.checksystem.core.repository.ReceiptRepository;
import ru.clevertec.checksystem.core.service.common.IIoReceiptService;
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
        name = Servlets.MAIL_SERVLET,
        urlPatterns = UrlPatterns.MAIL_PATTERN
)
public class MailServlet extends ApplicationServlet {

    private ReceiptRepository receiptRepository;
    private IIoReceiptService receiptService;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, config.getServletContext());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        try {
            validate(req, Parameters.SOURCE_PARAMETER, Parameters.FORMAT_TYPE_PARAMETER,
                    Parameters.EMAIL_SUBJECT_PARAMETER, Parameters.EMAIL_ADDRESS_PARAMETER);
        } catch (RuntimeException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
            return;
        }

        var source = req.getParameter(Parameters.SOURCE_PARAMETER) != null
                ? req.getParameter(Parameters.SOURCE_PARAMETER)
                : Sources.DATABASE;
        var formatType = req.getParameter(Parameters.FORMAT_TYPE_PARAMETER);
        var subject = req.getParameter(Parameters.EMAIL_SUBJECT_PARAMETER);
        var address = req.getParameter(Parameters.EMAIL_ADDRESS_PARAMETER);
        var ids = getReceiptIds(req);

        var receipts = new ReceiptDataSource(receiptRepository, req.getSession())
                .findAllById(source, ids);

        if (receipts == null || receipts.size() == 0) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        try {
            receiptService.sendToEmail(subject, receipts, FormatType.parse(formatType), address);
            resp.setStatus(HttpServletResponse.SC_OK);
        } catch (Throwable e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        }
    }

    private static List<Long> getReceiptIds(HttpServletRequest req) {
        return Arrays.stream(req.getParameterMap().get(Parameters.ID_PARAMETER))
                .mapToLong(Long::parseLong)
                .boxed()
                .collect(Collectors.toList());
    }

    @Autowired
    public void setReceiptRepository(ReceiptRepository receiptRepository) {
        this.receiptRepository = receiptRepository;
    }

    @Autowired
    public void setReceiptService(IIoReceiptService receiptService) {
        this.receiptService = receiptService;
    }
}
