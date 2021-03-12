package ru.clevertec.checksystem.webuiservlet.servlet;

import ru.clevertec.checksystem.webuiservlet.Authentication;
import ru.clevertec.checksystem.webuiservlet.ParameterVerifier;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

import static ru.clevertec.checksystem.webuiservlet.Constants.Sessions;

public abstract class ApplicationServlet extends HttpServlet {

    private final static ParameterVerifier parameterVerifier = new ParameterVerifier();

    protected Authentication authentication(HttpServletRequest request) {
        return (Authentication) request.getSession().getAttribute(Sessions.AUTHENTICATION_SESSION);
    }

    protected void logIn(HttpServletRequest request, String question, String answer) {
        request.getSession().setAttribute(Sessions.AUTHENTICATION_SESSION, Authentication.Authenticated(question, answer));
    }

    protected void logOut(HttpServletRequest request) {
        request.getSession().setAttribute(Sessions.AUTHENTICATION_SESSION, Authentication.Anonymous());
    }

    protected void verifyForRequired(HttpServletRequest request, String... parameterNames) {
        parameterVerifier.verifyForRequired(request, parameterNames);
    }

    protected void verifyForSuitable(HttpServletRequest request, String... parameterNames) {
        parameterVerifier.verifyForSuitable(request, parameterNames);
    }

    protected void isKnownParameter(String parameterName) {
        parameterVerifier.verifyForKnown(parameterName);
    }

    protected void verifyForKnownAndSuitable(String parameterName, String parameterValue) {
        parameterVerifier.verifyForKnownAndSuitable(parameterName, parameterValue);
    }
}
