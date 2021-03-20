package ru.clevertec.checksystem.webuiservlet.servlet;


import org.springframework.beans.factory.annotation.Autowired;
import ru.clevertec.checksystem.webuiservlet.Authentication;
import ru.clevertec.checksystem.webuiservlet.constant.Sessions;
import ru.clevertec.checksystem.webuiservlet.validation.ParameterValidatorFactory;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;


public abstract class ApplicationServlet extends HttpServlet {

    private ParameterValidatorFactory parameterValidatorFactory;

    protected Authentication authentication(HttpServletRequest request) {
        return (Authentication) request.getSession().getAttribute(Sessions.AUTHENTICATION_SESSION);
    }

    protected void logIn(HttpServletRequest request, String question, String answer) {
        request.getSession().setAttribute(Sessions.AUTHENTICATION_SESSION, Authentication.Authenticated(question, answer));
    }

    protected void logOut(HttpServletRequest request) {
        request.getSession().setAttribute(Sessions.AUTHENTICATION_SESSION, Authentication.Anonymous());
    }

    protected void validate(HttpServletRequest req, String... parameterNames) {
        for (var parameterName : parameterNames)
            parameterValidatorFactory.instance(parameterName).validate(req.getParameterMap());
    }

    @Autowired
    public void setParameterValidatorFactory(ParameterValidatorFactory parameterValidatorFactory) {
        this.parameterValidatorFactory = parameterValidatorFactory;
    }
}
