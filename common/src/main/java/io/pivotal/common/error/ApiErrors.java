package io.pivotal.common.error;

import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
@Builder
public class ApiErrors {
    private String code;
    private String description;
    private Map<String,ApiError> errors;
}
