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

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.HashMap;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private final ErrorAttributes errorAttributes;

    @Autowired
    public GlobalExceptionHandler(ErrorAttributes errorAttributes) {
        this.errorAttributes = errorAttributes;
    }

    @ExceptionHandler({EntityNotFoundException.class, EntityExistsException.class})
    protected ResponseEntity<Object> handleEntityExceptions(WebRequest request) {
        return handleBadRequest(request);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleIllegalArgumentExceptions(WebRequest request) {
        return handleBadRequest(request);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationExceptions(MethodArgumentNotValidException ex) {

        var errors = new HashMap<String, String>();

        ex.getBindingResult().getAllErrors().forEach((error) -> {
            var fieldName = ((FieldError) error).getField();
            var errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<Object> handleInternalServerErrorExceptions(WebRequest request) {
        return handleInternalServerError(request);
    }

    private ResponseEntity<Object> handleBadRequest(WebRequest request) {

        var badRequestStatus = HttpStatus.BAD_REQUEST;

        var body = errorAttributes.getErrorAttributes(request, ErrorAttributeOptions.of(ErrorAttributeOptions.Include.MESSAGE));
        body.put("error", HttpStatus.BAD_REQUEST.getReasonPhrase());
        body.put("status", badRequestStatus.value());

        return new ResponseEntity<>(body, badRequestStatus);
    }

    private ResponseEntity<Object> handleInternalServerError(WebRequest request) {

        var internalServerErrorStatus = HttpStatus.INTERNAL_SERVER_ERROR;

        var body = errorAttributes.getErrorAttributes(request, ErrorAttributeOptions.of(ErrorAttributeOptions.Include.MESSAGE));
        body.put("error", HttpStatus.BAD_REQUEST.getReasonPhrase());
        body.put("status", internalServerErrorStatus.value());

        return new ResponseEntity<>(body, internalServerErrorStatus);
    }
}
