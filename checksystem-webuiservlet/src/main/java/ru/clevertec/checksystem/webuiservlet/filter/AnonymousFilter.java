package ru.clevertec.checksystem.webuiservlet.filter;

import ru.clevertec.checksystem.webuiservlet.Authentication;
import ru.clevertec.checksystem.webuiservlet.constant.Filters;
import ru.clevertec.checksystem.webuiservlet.constant.Servlets;
import ru.clevertec.checksystem.webuiservlet.constant.Sessions;
import ru.clevertec.checksystem.webuiservlet.constant.UrlPatterns;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(
        filterName = Filters.ANONYMOUS_FILTER,
        servletNames = {
                Servlets.LOGIN_SERVLET
        }
)
public class AnonymousFilter extends AuthenticationFilter {

    @Override
    protected void doFilter(
            HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {

        ensureSessionCreated(req);

        var authentication = (Authentication) req.getSession().getAttribute(Sessions.AUTHENTICATION_SESSION);

        if (authentication.isAuthenticated())
            res.sendRedirect(req.getContextPath() + UrlPatterns.ROOT_PATTERN);
        else
            chain.doFilter(req, res);
    }
}
