package com.metaway.petshop.configurations.exceptions;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestControllerAdvice
public class ExceptionGlobalHandler {

    private final MessageSource messageSource;

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<?> handleValidationException(final ValidationException e) {
        var details = new ExceptionDetails(HttpStatus.BAD_REQUEST.getReasonPhrase(), e.getMessage());
        return new ResponseEntity<>(List.of(details), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> handleNotFoundException(final NotFoundException e) {
        var details = new ExceptionDetails(HttpStatus.NOT_FOUND.getReasonPhrase(), e.getMessage());
        return new ResponseEntity<>(List.of(details), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<?> handleForbiddenException(final ForbiddenException e) {
        var details = new ExceptionDetails(HttpStatus.FORBIDDEN.getReasonPhrase(), e.getMessage());
        return new ResponseEntity<>(List.of(details), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<?> handleUnauthorizedException(final UnauthorizedException e) {
        var details = new ExceptionDetails(HttpStatus.UNAUTHORIZED.getReasonPhrase(), e.getMessage());
        return new ResponseEntity<>(List.of(details), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValidException(final MethodArgumentNotValidException e) {
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        List<ExceptionDetails> details = new ArrayList<>();

        fieldErrors.forEach(fieldError -> {
            String messageError = messageSource.getMessage(fieldError, LocaleContextHolder.getLocale());
            var detail = new ExceptionDetails(fieldError.getField(), messageError);
            details.add(detail);
        });

        return new ResponseEntity<>(details, HttpStatus.BAD_REQUEST);
    }

}
