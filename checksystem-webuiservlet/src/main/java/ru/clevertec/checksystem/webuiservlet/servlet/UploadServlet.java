package ru.clevertec.checksystem.webuiservlet.servlet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import ru.clevertec.checksystem.core.common.service.IIoReceiptService;
import ru.clevertec.checksystem.core.factory.service.ServiceFactory;
import ru.clevertec.checksystem.core.helper.FormatHelpers;
import ru.clevertec.checksystem.core.service.IoReceiptService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static ru.clevertec.checksystem.webuiservlet.Constants.*;

@Component
@WebServlet(
        name = ServletNames.UPLOAD_SERVLET,
        urlPatterns = UrlPatterns.UPLOAD_PATTERN
)
@MultipartConfig(fileSizeThreshold = 1024 * 1024)
public class UploadServlet extends ApplicationServlet {

    private ServiceFactory serviceFactory;

    private static final String CHECKS_FILE_PART_NAME = "checksFile";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, config.getServletContext());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        var part = req.getPart(CHECKS_FILE_PART_NAME);

        try {
            verifyForKnownAndSuitable(Parameters.FORMAT_PARAMETER, FormatHelpers.formatByContentType(part.getContentType()));
        } catch (RuntimeException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
            return;
        }

        var format = FormatHelpers.formatByContentType(part.getContentType());

        IIoReceiptService ioReceiptService = serviceFactory.instance(IoReceiptService.class);

        var receipts = ioReceiptService.deserialize(part.getInputStream(), format);
        req.getSession().setAttribute(Sessions.RECEIPTS_SESSION, receipts);

        var returnUrl = req.getParameter(Parameters.RETURN_URL_PARAMETER);
        resp.sendRedirect(returnUrl);
    }

    @Autowired
    public void setServiceFactory(ServiceFactory serviceFactory) {
        this.serviceFactory = serviceFactory;
    }
}
