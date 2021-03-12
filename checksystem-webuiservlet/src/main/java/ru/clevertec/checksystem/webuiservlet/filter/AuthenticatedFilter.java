package ru.clevertec.checksystem.webuiservlet.filter;

import ru.clevertec.checksystem.webuiservlet.Authentication;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static ru.clevertec.checksystem.webuiservlet.Constants.*;

@WebFilter(
        filterName = FilterNames.AUTHENTICATED_FILTER,
        servletNames = {
                ServletNames.HOME_SERVLET,
                ServletNames.DOWNLOAD_SERVLET,
                ServletNames.MAIL_SERVLET,
                ServletNames.UPLOAD_SERVLET,
                ServletNames.LOGOUT_SERVLET
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
