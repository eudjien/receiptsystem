package ru.clevertec.checksystem.webuiservlet.filter;

import ru.clevertec.checksystem.webuiservlet.Authentication;

import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;

import static ru.clevertec.checksystem.webuiservlet.Constants.Sessions;

public abstract class AuthenticationFilter extends HttpFilter {
    protected static void ensureSessionCreated(HttpServletRequest req) {
        if (!(req.getSession().getAttribute(Sessions.AUTHENTICATION_SESSION) instanceof Authentication))
            req.getSession().setAttribute(Sessions.AUTHENTICATION_SESSION, Authentication.Anonymous());
    }
}
