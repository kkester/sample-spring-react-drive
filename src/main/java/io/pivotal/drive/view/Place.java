package io.pivotal.drive.view;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Builder
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
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDate createdDate;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDate lastUpdatedDate;
}
