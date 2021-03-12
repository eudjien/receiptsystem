package ru.clevertec.checksystem.webuiservlet.servlet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import ru.clevertec.checksystem.core.common.service.IEmailService;
import ru.clevertec.checksystem.core.factory.service.ServiceFactory;
import ru.clevertec.checksystem.core.repository.CheckRepository;
import ru.clevertec.checksystem.core.service.EmailService;
import ru.clevertec.checksystem.core.util.CollectionUtils;
import ru.clevertec.checksystem.webuiservlet.ChecksDataSource;

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

    private CheckRepository checkRepository;
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
        var ids = getCheckIds(req);

        var checks = new ChecksDataSource(checkRepository, req.getSession(), Sessions.CHECKS_SESSION)
                .findAllById(source, ids);

        if (CollectionUtils.isNullOrEmpty(checks)) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        try {
            IEmailService emailService = serviceFactory.instance(EmailService.class);
            emailService.sendEmail(subject, address, checks, type, format);
            resp.setStatus(HttpServletResponse.SC_OK);
        } catch (Throwable e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        }
    }

    private static List<Long> getCheckIds(HttpServletRequest req) {
        return Arrays.stream(req.getParameterMap().get(Parameters.ID_PARAMETER))
                .mapToLong(Long::parseLong)
                .boxed()
                .collect(Collectors.toList());
    }

    @Autowired
    public void setCheckRepository(CheckRepository checkRepository) {
        this.checkRepository = checkRepository;
    }

    @Autowired
    public void setServiceFactory(ServiceFactory serviceFactory) {
        this.serviceFactory = serviceFactory;
    }
}
