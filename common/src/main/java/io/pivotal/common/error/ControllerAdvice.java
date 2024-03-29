package io.pivotal.common.error;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class ControllerAdvice {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    ApiErrors handleUnexpectedError(Exception error) {
        log.error("Unexpected error occurred", error);
        return ApiErrors.builder()
                .code("operation-failed")
                .description("An unexpected error occurred")
                .build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ApiErrors handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        BindingResult bindingResult = exception.getBindingResult();
        Map<String, ApiError> errors = bindingResult.getFieldErrors().stream()
                .collect(Collectors.toMap(FieldError::getField, fieldError -> ApiError.builder()
                        .description(fieldError.getDefaultMessage())
                        .build()));

        return ApiErrors.builder()
                .code("invalid-request")
                .description("Missing Required Fields")
                .errors(errors)
                .build();
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    ResponseEntity<ApiErrors> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException exception) {
        String[] supportedMethods = exception.getSupportedMethods();
        ApiErrors apiErrors = ApiErrors.builder()
                .code("method-not-allowed")
                .build();
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED)
                .header(HttpHeaders.ALLOW, supportedMethods)
                .body(apiErrors);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    ResponseEntity<ApiErrors> handleHttpMessageNotReadableException(HttpMessageNotReadableException exception) {
        ApiErrors apiErrors = ApiErrors.builder()
                .code("invalid-request")
                .description(exception.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(apiErrors);
    }
}
