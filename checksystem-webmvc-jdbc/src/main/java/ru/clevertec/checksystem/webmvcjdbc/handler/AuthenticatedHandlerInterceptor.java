package ru.clevertec.checksystem.webmvcjdbc.handler;

import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.lang.NonNull;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;
import ru.clevertec.checksystem.webmvcjdbc.Authentication;
import ru.clevertec.checksystem.webmvcjdbc.constant.Sessions;
import ru.clevertec.checksystem.webmvcjdbc.controller.AuthenticationController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class AuthenticatedHandlerInterceptor extends AuthenticationHandlerInterceptor {

    @Override
    public boolean preHandle(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull Object handler) throws Exception {

        ensureSessionCreated(request);

        var authentication = (Authentication) request.getSession().getAttribute(Sessions.AUTHENTICATION_SESSION);

        if (authentication.isAuthenticated()
                || ((handler instanceof HandlerMethod)
                && (((HandlerMethod) handler).getBeanType().isAssignableFrom(AuthenticationController.class)
                || ((HandlerMethod) handler).getBeanType().isAssignableFrom(BasicErrorController.class)))
                || handler instanceof ResourceHttpRequestHandler) {
            return true;
        } else {
            response.sendRedirect("/authentication/login");
            return false;
        }
    }
}
