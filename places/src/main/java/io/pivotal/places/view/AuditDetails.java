package io.pivotal.places.view;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Builder
@ToString
public class AuditDetails {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDate createdDate;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDate lastUpdatedDate;
}
