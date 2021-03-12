package ru.clevertec.checksystem.webuiservlet.exception;

public class QueryParameterIncorrectException extends RuntimeException {

    private static final String MESSAGE_FORMAT = "Query parameter '%s' is incorrect";

    public QueryParameterIncorrectException(String parameterName) {
        super(String.format(MESSAGE_FORMAT, parameterName));
    }
}
