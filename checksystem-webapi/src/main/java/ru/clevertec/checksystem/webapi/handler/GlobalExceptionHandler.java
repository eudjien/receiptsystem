package ru.clevertec.checksystem.webapi.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import ru.clevertec.checksystem.core.exception.ValidationException;
import ru.clevertec.checksystem.webapi.dto.ErrorDto;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.Collections;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private final ErrorAttributes errorAttributes;

    @Autowired
    public GlobalExceptionHandler(ErrorAttributes errorAttributes) {
        this.errorAttributes = errorAttributes;
    }

    @ExceptionHandler({EntityNotFoundException.class, EntityExistsException.class, IllegalArgumentException.class})
    public ResponseEntity<ErrorDto> handleBadRequestExceptions(WebRequest request) {

        var status = HttpStatus.BAD_REQUEST;
        var attributes = errorAttributes.getErrorAttributes(request, ErrorAttributeOptions.of(ErrorAttributeOptions.Include.MESSAGE));

        return createResponseEntity(status, (Date) attributes.get("timestamp"), Collections.singleton((String) attributes.get("message")));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDto> handleValidationExceptions(MethodArgumentNotValidException ex, WebRequest request) {

        var messages = ex.getBindingResult().getAllErrors().stream()
                .map(error -> ((FieldError) error).getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.toSet());

        var attributes = errorAttributes.getErrorAttributes(request, ErrorAttributeOptions.of(ErrorAttributeOptions.Include.MESSAGE));

        return createResponseEntity(HttpStatus.BAD_REQUEST, (Date) attributes.get("timestamp"), messages);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ErrorDto> handleManualValidationExceptions(ValidationException ex, WebRequest request) {

        var messages = ex.getErrors().stream()
                .map(a -> a.getProperty() + ": " + a.getMessage())
                .collect(Collectors.toSet());

        var attributes = errorAttributes.getErrorAttributes(request, ErrorAttributeOptions.of(ErrorAttributeOptions.Include.MESSAGE));

        return createResponseEntity(HttpStatus.BAD_REQUEST, (Date) attributes.get("timestamp"), messages);
    }

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<ErrorDto> handleInternalServerErrorExceptions(WebRequest request) {

        var internalServerErrorStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        var attributes = errorAttributes.getErrorAttributes(request, ErrorAttributeOptions.of(ErrorAttributeOptions.Include.MESSAGE));

        return createResponseEntity(internalServerErrorStatus, (Date) attributes.get("timestamp"), Collections.singleton((String) attributes.get("message")));
    }

    private static ResponseEntity<ErrorDto> createResponseEntity(HttpStatus httpStatus, Date date, Set<String> messages) {
        var errorDto = new ErrorDto(date.getTime(), httpStatus.value(), httpStatus.getReasonPhrase(), messages);
        return new ResponseEntity<>(errorDto, httpStatus);
    }
}
