package ru.clevertec.checksystem.webuiservlet.exception;

public class QueryParameterRequiredException extends RuntimeException {

    private static final String MESSAGE_FORMAT = "Query parameter '%s' is required";

    public QueryParameterRequiredException(String parameterName) {
        super(String.format(MESSAGE_FORMAT, parameterName));
    }
}
