package io.pivotal.drive.error;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.List;

@Getter
public class ApplicationException extends RuntimeException {

    private HttpStatus status;

    private List<ApiError> errors;

    public static final ApplicationException resourceNotFound(String description) {
        return new ApplicationException(HttpStatus.NOT_FOUND, ApiError.builder()
                .code("resource-not-found")
                .description(description)
                .build());
    }

    public ApplicationException(HttpStatus status, ApiError error) {
        this(status, List.of(error));
    }

    public ApplicationException(HttpStatus status, List<ApiError> errors) {
        this.status = status;
        this.errors = errors;
    }
}
