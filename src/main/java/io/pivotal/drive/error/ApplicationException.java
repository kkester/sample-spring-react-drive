package io.pivotal.drive.error;

import org.springframework.http.HttpStatus;

import java.util.List;

public class ApplicationException extends RuntimeException {

    private HttpStatus status;

    private List<ApiError> errors;

    public ApplicationException(HttpStatus status, ApiError error) {
        this(status, List.of(error));
    }

    public ApplicationException(HttpStatus status, List<ApiError> errors) {
        this.status = status;
        this.errors = errors;
    }

    public List<ApiError> getErrors() {
        return errors;
    }

    public HttpStatus getStatus() { return status; }
}
