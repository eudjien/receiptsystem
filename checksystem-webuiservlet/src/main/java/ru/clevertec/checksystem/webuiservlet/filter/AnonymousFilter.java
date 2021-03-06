package ru.clevertec.checksystem.webuiservlet.filter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static ru.clevertec.checksystem.webuiservlet.Constants.*;

@WebFilter(
        filterName = "Anonymous",
        servletNames = {
                ServletNames.AUTHENTICATION_SERVLET
        }
)
public class AnonymousFilter extends HttpFilter {

    @Override
    protected void doFilter(
            HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {

        ensureSessionCreated(req);

        var authenticated = (boolean) req.getSession().getAttribute(Sessions.AUTHENTICATED);

        if (authenticated)
            res.sendRedirect(req.getContextPath() + UrlPatterns.ROOT_PATTERN);
        else
            chain.doFilter(req, res);
    }

    private static void ensureSessionCreated(HttpServletRequest req) {
        if (req.getSession().getAttribute(Sessions.AUTHENTICATED) == null)
            req.getSession().setAttribute(Sessions.AUTHENTICATED, false);
    }
}
