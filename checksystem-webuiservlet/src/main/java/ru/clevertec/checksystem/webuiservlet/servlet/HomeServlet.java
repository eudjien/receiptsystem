package ru.clevertec.checksystem.webuiservlet.servlet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import ru.clevertec.checksystem.core.repository.CheckRepository;
import ru.clevertec.checksystem.webuiservlet.ChecksDataSource;
import ru.clevertec.checksystem.webuiservlet.Helpers;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static ru.clevertec.checksystem.webuiservlet.Constants.*;

@Component
@WebServlet(
        name = ServletNames.HOME_SERVLET,
        urlPatterns = UrlPatterns.HOME_PATTERN
)
public class HomeServlet extends HttpServlet {

    private CheckRepository checkRepository;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, config.getServletContext());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        var source = Helpers.sourceByParameter(req.getParameter(Parameters.SOURCE_PARAMETER));

        var checks = new ChecksDataSource(checkRepository, req.getSession(), Sessions.CHECKS_SESSION)
                .findAll(source);

        req.setAttribute(Attributes.CHECKS_ATTRIBUTE, checks);
        req.setAttribute(Attributes.SOURCE_ATTRIBUTE, source);

        req.getRequestDispatcher(Pages.HOME_PAGE).forward(req, resp);
    }

    @Autowired
    public void setCheckRepository(CheckRepository checkRepository) {
        this.checkRepository = checkRepository;
    }
}
