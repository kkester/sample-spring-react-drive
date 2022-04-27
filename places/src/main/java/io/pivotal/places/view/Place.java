package io.pivotal.places.view;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Builder
@ToString
public class Place {
    @JsonIgnore
    private UUID id;
    @JsonProperty(required = true)
    private String city;
    @JsonProperty(required = true)
    private States state;
    @JsonProperty(required = true)
    private String country;
    private String description;
    private LocalDate visited;
    private AuditDetails auditDetails;
}
