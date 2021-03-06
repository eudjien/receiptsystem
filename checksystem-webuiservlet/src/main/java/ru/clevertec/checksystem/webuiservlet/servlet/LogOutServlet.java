package ru.clevertec.checksystem.webuiservlet.servlet;

import org.springframework.stereotype.Component;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

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
        name = ServletNames.LOGOUT_SERVLET,
        urlPatterns = UrlPatterns.LOGOUT_PATTERN
)
public class LogOutServlet extends HttpServlet {

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, config.getServletContext());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getSession().setAttribute(Sessions.AUTHENTICATED, false);
        resp.sendRedirect(req.getContextPath() + UrlPatterns.ROOT_PATTERN);
    }
}
