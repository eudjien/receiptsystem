package ru.clevertec.checksystem.webuiservlet.filter;

import ru.clevertec.checksystem.webuiservlet.Authentication;
import ru.clevertec.checksystem.webuiservlet.constant.Filters;
import ru.clevertec.checksystem.webuiservlet.constant.Servlets;
import ru.clevertec.checksystem.webuiservlet.constant.Sessions;
import ru.clevertec.checksystem.webuiservlet.constant.UrlPatterns;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(
        filterName = Filters.AUTHENTICATED_FILTER,
        servletNames = {
                Servlets.HOME_SERVLET,
                Servlets.DOWNLOAD_SERVLET,
                Servlets.MAIL_SERVLET,
                Servlets.UPLOAD_SERVLET,
                Servlets.LOGOUT_SERVLET
        },
        initParams = @WebInitParam(name = "enabled", value = "true")
)
public class AuthenticatedFilter extends AuthenticationFilter {

    @Override
    protected void doFilter(
            HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {

        ensureSessionCreated(req);

        if (!Boolean.parseBoolean(getFilterConfig().getInitParameter("enabled"))) {
            chain.doFilter(req, res);
            return;
        }

        var authentication = (Authentication) req.getSession().getAttribute(Sessions.AUTHENTICATION_SESSION);

        if (authentication.isAuthenticated())
            chain.doFilter(req, res);
        else
            res.sendRedirect(req.getContextPath() + UrlPatterns.LOGIN_PATTERN);
    }
}
