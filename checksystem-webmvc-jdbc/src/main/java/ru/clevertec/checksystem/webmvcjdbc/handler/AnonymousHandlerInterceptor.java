package ru.clevertec.checksystem.webmvcjdbc.handler;


import org.springframework.lang.NonNull;
import ru.clevertec.checksystem.webmvcjdbc.Authentication;
import ru.clevertec.checksystem.webmvcjdbc.constant.Sessions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class AnonymousHandlerInterceptor extends AuthenticationHandlerInterceptor {
    @Override
    public boolean preHandle(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull Object handler) throws Exception {

        ensureSessionCreated(request);

        var authentication = (Authentication) request.getSession().getAttribute(Sessions.AUTHENTICATION_SESSION);

        if (!authentication.isAuthenticated())
            return true;

        response.sendRedirect("/");
        return false;
    }
}
