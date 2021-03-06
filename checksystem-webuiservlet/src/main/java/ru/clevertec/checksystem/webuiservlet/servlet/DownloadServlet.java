package ru.clevertec.checksystem.webuiservlet.servlet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import ru.clevertec.checksystem.core.common.service.IIoCheckService;
import ru.clevertec.checksystem.core.common.service.IPrintingCheckService;
import ru.clevertec.checksystem.core.entity.check.Check;
import ru.clevertec.checksystem.core.factory.service.ServiceFactory;
import ru.clevertec.checksystem.core.repository.CheckRepository;
import ru.clevertec.checksystem.core.service.IoCheckService;
import ru.clevertec.checksystem.core.service.PrintingCheckService;
import ru.clevertec.checksystem.core.util.CollectionUtils;
import ru.clevertec.checksystem.webuiservlet.ChecksDataSource;
import ru.clevertec.checksystem.webuiservlet.Helpers;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static ru.clevertec.checksystem.webuiservlet.Constants.*;


@Component
@WebServlet(
        name = ServletNames.DOWNLOAD_SERVLET,
        urlPatterns = UrlPatterns.DOWNLOAD_PATTERN
)
public class DownloadServlet extends HttpServlet {

    private CheckRepository checkRepository;
    private ServiceFactory serviceFactory;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, config.getServletContext());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        var checkIds = getCheckIds(req);
        var type = req.getParameter(Parameters.TYPE);
        var format = req.getParameter(Parameters.FORMAT);
        var source = Helpers.sourceByParameter(req.getParameter(Parameters.SOURCE_PARAMETER));

        var checks = new ChecksDataSource(checkRepository, req.getSession(), Sessions.CHECKS_SESSION)
                .findAllById(source, checkIds);

        if (CollectionUtils.isNullOrEmpty(checks)) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        } else {
            setHeaders(resp, format);
            switch (type) {
                case Types.PRINT -> print(resp, CollectionUtils.asCollection(checks), format);
                case Types.SERIALIZE -> serialize(resp, CollectionUtils.asCollection(checks), format);
                default -> resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            }
        }
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
        resp.setContentType(Helpers.contentTypeByFormat(format));
        resp.setHeader("Content-disposition", "attachment; filename=checks."
                + Helpers.extensionByFormat(format));
    }

    private static List<Long> getCheckIds(HttpServletRequest req) {
        return Arrays.stream(req.getParameterMap().get(Parameters.ID))
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
