package com.core.exception;

import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.core.exception.application.AppAuthenticationException;
import com.core.exception.application.ApplicationException;
import com.core.exception.application.DataNotFoundException;
import com.core.exception.application.InvalidDataException;
import com.core.exception.model.Error;
import com.core.exception.model.Errors;
import com.core.exception.model.ValidationErrorDTO;

@ControllerAdvice
public class ExceptionControllerHandler extends ResponseEntityExceptionHandler {

    @Autowired
    private MessageSource messageSource;

    @Value("${locale}")
    private String locale;

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {
        BindingResult result = ex.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();
        ValidationErrorDTO dto = new ValidationErrorDTO();

        for (FieldError fieldError : fieldErrors) {
            String localizedErrorMessage = resolveLocalizedErrorMessage(fieldError.getCode(),
                    fieldError.getArguments());
            dto.addFieldError(fieldError.getField(), localizedErrorMessage);
        }

        return new ResponseEntity<Object>(dto, headers, status);
    }

    @ExceptionHandler({ AppAuthenticationException.class })
    public ResponseEntity<Object> handleException(AppAuthenticationException ex) {
        String localizedErrorMessage = resolveLocalizedErrorMessage(ex.getMessage(), ex.getArgs());
        Error error = mapExceptionToError(localizedErrorMessage, "");
        Errors errors = new Errors();
        errors.getErrors().add(error);
        return new ResponseEntity<>(errors, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler({ DataNotFoundException.class })
    public ResponseEntity<Object> handleDataException(DataNotFoundException ex) {
        String localizedErrorMessage = resolveLocalizedErrorMessage(ex.getMessage(), ex.getArgs());
        Error error = mapExceptionToError(localizedErrorMessage, "error");
        Errors errors = new Errors();
        errors.getErrors().add(error);
        return new ResponseEntity<>(errors, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({ InvalidDataException.class })
    public ResponseEntity<Object> handleDataException(InvalidDataException ex) {
        String localizedErrorMessage = resolveLocalizedErrorMessage(ex.getMessage(), ex.getArgs());
        Error error = mapExceptionToError(localizedErrorMessage, "");
        Errors errors = new Errors();
        errors.getErrors().add(error);
        return new ResponseEntity<>(errors, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    
    @ExceptionHandler({ ApplicationException.class })
    public ResponseEntity<Object> handleException(ApplicationException ex) {
        String localizedErrorMessage = resolveLocalizedErrorMessage(ex.getMessage(), ex.getArgs());
        Error error = mapExceptionToError(localizedErrorMessage, "");
        Errors errors = new Errors();
        errors.getErrors().add(error);
        return new ResponseEntity<Object>(errors, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private String resolveLocalizedErrorMessage(String fieldErrorCode, Object[] args) {
        return messageSource.getMessage(fieldErrorCode, args, new Locale(locale));
    }

    private Error mapExceptionToError(String errorMessage, String errorKey) {
        Error error = new Error();
        error.setMessage(errorMessage);
        error.setErrorKey(errorKey);
        return error;
    }

}