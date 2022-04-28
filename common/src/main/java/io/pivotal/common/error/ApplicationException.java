package io.pivotal.common.error;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ApplicationException extends RuntimeException {

    private final HttpStatus status;

    private final transient ApiErrors errors;

    public static ApplicationException resourceNotFound(String description) {
        return new ApplicationException(HttpStatus.NOT_FOUND, ApiErrors.builder()
                .code("resource-not-found")
                .description(description)
                .build());
    }

    public ApplicationException(HttpStatus status, ApiErrors errors) {
        this.status = status;
        this.errors = errors;
    }
}
