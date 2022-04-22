package io.pivotal.drive.error;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;

//@ControllerAdvice
public class ControllerErrorHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {ApplicationException.class})
    public ResponseEntity<List<ApiError>> handleValidationFailures(ApplicationException ex) {
        return new ResponseEntity<>(ex.getErrors(), ex.getStatus());
    }

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<List<ApiError>> handleError(Exception ex) {
        List<ApiError> errors = List.of(ApiError.builder().code("operation-failed").description("an unexpected error occurred").build());
        return new ResponseEntity<>(errors, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<ApiError> errors = List.of(ApiError.builder().code("invalid-request").description(ex.getMessage()).build());
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<ApiError> errors = List.of(ApiError.builder().code("resource-not-found").build());
        return new ResponseEntity<>(errors, HttpStatus.NOT_FOUND);
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<ApiError> errors = List.of(ApiError.builder().code("invalid-method").build());
        return new ResponseEntity<>(errors, HttpStatus.METHOD_NOT_ALLOWED);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<ApiError> errors = List.of(ApiError.builder().code("unsupported-request-media-type").description(ex.getMessage()).build());
        return new ResponseEntity<>(errors, HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }
}
