package ru.clevertec.checksystem.webmvcjdbc.handler;

import org.springframework.web.servlet.HandlerInterceptor;
import ru.clevertec.checksystem.webmvcjdbc.Authentication;
import ru.clevertec.checksystem.webmvcjdbc.constant.Sessions;

import javax.servlet.http.HttpServletRequest;

public abstract class AuthenticationHandlerInterceptor implements HandlerInterceptor {
    protected static void ensureSessionCreated(HttpServletRequest req) {
        if (!(req.getSession().getAttribute(Sessions.AUTHENTICATION_SESSION) instanceof Authentication))
            req.getSession().setAttribute(Sessions.AUTHENTICATION_SESSION, Authentication.Anonymous());
    }
}
