package ru.clevertec.checksystem.webuiservlet.servlet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import ru.clevertec.checksystem.core.common.service.IIoCheckService;
import ru.clevertec.checksystem.core.common.service.IPrintingCheckService;
import ru.clevertec.checksystem.core.entity.check.Check;
import ru.clevertec.checksystem.core.factory.service.ServiceFactory;
import ru.clevertec.checksystem.core.helper.FormatHelpers;
import ru.clevertec.checksystem.core.repository.CheckRepository;
import ru.clevertec.checksystem.core.service.IoCheckService;
import ru.clevertec.checksystem.core.service.PrintingCheckService;
import ru.clevertec.checksystem.core.util.CollectionUtils;
import ru.clevertec.checksystem.webuiservlet.ChecksDataSource;

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

    private CheckRepository checkRepository;
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
        var ids = getCheckIds(req);

        var checks = new ChecksDataSource(checkRepository, req.getSession(), Sessions.CHECKS_SESSION)
                .findAllById(source, ids);

        if (CollectionUtils.isNullOrEmpty(checks)) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        setHeaders(resp, format);

        if (!download(resp, type, format, checks)) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        resp.setStatus(HttpServletResponse.SC_OK);
    }

    private boolean download(HttpServletResponse resp, String type, String format, Collection<Check> checks) throws IOException {
        switch (type) {
            case Types.PRINT -> print(resp, CollectionUtils.asCollection(checks), format);
            case Types.SERIALIZE -> serialize(resp, CollectionUtils.asCollection(checks), format);
            default -> {
                return false;
            }
        }
        return true;
    }

    private void print(HttpServletResponse resp, Collection<Check> checks, String format) throws IOException {
        IPrintingCheckService printingCheckService = serviceFactory.instance(PrintingCheckService.class);
        printingCheckService.print(checks, resp.getOutputStream(), format);
    }

    private void serialize(HttpServletResponse resp, Collection<Check> checks, String format) throws IOException {
        IIoCheckService ioCheckService = serviceFactory.instance(IoCheckService.class);
        ioCheckService.serialize(checks, resp.getOutputStream(), format);
    }

    private static void setHeaders(HttpServletResponse resp, String format) {
        resp.setContentType(FormatHelpers.contentTypeByFormat(format));
        resp.setHeader("Content-disposition", "attachment; filename=checks"
                + FormatHelpers.extensionByFormat(format, true));
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
