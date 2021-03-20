package ru.clevertec.checksystem.webuiservlet.filter;

import ru.clevertec.checksystem.webuiservlet.Authentication;
import ru.clevertec.checksystem.webuiservlet.constant.Sessions;

import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;

public abstract class AuthenticationFilter extends HttpFilter {
    protected static void ensureSessionCreated(HttpServletRequest req) {
        if (!(req.getSession().getAttribute(Sessions.AUTHENTICATION_SESSION) instanceof Authentication))
            req.getSession().setAttribute(Sessions.AUTHENTICATION_SESSION, Authentication.Anonymous());
    }
}
