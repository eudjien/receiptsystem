package ru.clevertec.checksystem.webuiservlet.servlet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import ru.clevertec.checksystem.core.common.service.IMailService;
import ru.clevertec.checksystem.core.factory.service.ServiceFactory;
import ru.clevertec.checksystem.core.repository.ReceiptRepository;
import ru.clevertec.checksystem.core.service.MailService;
import ru.clevertec.checksystem.core.util.CollectionUtils;
import ru.clevertec.checksystem.webuiservlet.ReceiptDataSource;

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
        name = ServletNames.MAIL_SERVLET,
        urlPatterns = UrlPatterns.MAIL_PATTERN
)
public class MailServlet extends ApplicationServlet {

    private ReceiptRepository receiptRepository;
    private ServiceFactory serviceFactory;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, config.getServletContext());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        try {
            verifyForRequired(req, Parameters.TYPE_PARAMETER, Parameters.FORMAT_PARAMETER, Parameters.SUBJECT_PARAMETER, Parameters.ADDRESS_PARAMETER);
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
        var subject = req.getParameter(Parameters.SUBJECT_PARAMETER);
        var address = req.getParameter(Parameters.ADDRESS_PARAMETER);
        var ids = getReceiptIds(req);

        var receipts = new ReceiptDataSource(receiptRepository, req.getSession(), Sessions.RECEIPTS_SESSION)
                .findAllById(source, ids);

        if (CollectionUtils.isNullOrEmpty(receipts)) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        IMailService emailService = serviceFactory.instance(MailService.class);
        try {
            emailService.sendReceiptEmail(subject, address, receipts, type, format);
            resp.setStatus(HttpServletResponse.SC_OK);
        } catch (Throwable e) {
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
    public void setServiceFactory(ServiceFactory serviceFactory) {
        this.serviceFactory = serviceFactory;
    }
}
