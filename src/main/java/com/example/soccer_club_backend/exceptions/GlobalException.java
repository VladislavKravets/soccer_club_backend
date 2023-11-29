package com.example.soccer_club_backend.exceptions;

import com.example.soccer_club_backend.models.AppError;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.nio.file.AccessDeniedException;
import java.util.List;

@RestControllerAdvice
public class GlobalException {
    @ExceptionHandler
    public AppError handleResourceNotFoundException(ResourceNotFoundException ex) {
        return new AppError(
                HttpStatus.NO_CONTENT.value(),
                ex.getMessage()
        );
    }

    @ExceptionHandler
    public AppError handleNoDataFoundException(NoDataFoundException ex) {
        return new AppError(
                HttpStatus.NO_CONTENT.value(),
                ex.getMessage()
        );
    }

    @ExceptionHandler
    public ResponseEntity<AppError> handIllegalArgument(java.lang.IllegalArgumentException ex) {
        return ResponseEntity.ok(new AppError(
                HttpStatus.BAD_REQUEST.value(),
                ex.getMessage())
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public AppError handleValidationExceptions(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        List<String> errorMessages = result.getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toList();
        return new AppError(HttpStatus.BAD_REQUEST.value(),
                "Validation failed" + errorMessages);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public AppError handleMethodNotAllowedException(HttpRequestMethodNotSupportedException ex) {
        return new AppError(
                HttpStatus.NO_CONTENT.value(),
                "Method Not Allowed. Supported methods are " + ex.getSupportedHttpMethods()
        );
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public AppError handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        return new AppError(
                HttpStatus.BAD_REQUEST.value(),
                "Bad Request: Required request body is missing"
        );
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public AppError handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        return new AppError(
                HttpStatus.BAD_REQUEST.value(),
                "Invalid argument: " + ex.getName()
        );
    }

    @ExceptionHandler(DataAccessException.class)
    public AppError handleDatabaseException(DataAccessException ex) {
        return new AppError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Database error occurred: " + ex.getMessage());
    }

    @ExceptionHandler(AccessDeniedException.class)
    public AppError handleAccessDeniedException(AccessDeniedException ex) {
        return new AppError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Access Denied error occurred: " + ex.getMessage());
    }

//    @ExceptionHandler(IllegalArgumentException.class)
//    public AppError handleIllegalException(IllegalArgumentException ex) {
//        return new AppError(HttpStatus.OK.value(), "Exeption: " + ex.getMessage());
//    }
}
