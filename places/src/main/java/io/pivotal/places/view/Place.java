package io.pivotal.places.view;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kjetland.jackson.jsonSchema.annotations.JsonSchemaTitle;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Builder
@ToString
public class Place {
    @JsonIgnore
    private UUID id;

    @JsonSchemaTitle("City")
    @NotEmpty(message = "City is required")
    private String city;

    @JsonSchemaTitle("State")
    @NotNull(message = "State is required")
    private States state;

    @JsonSchemaTitle("Country")
    @NotEmpty(message = "City is required")
    private String country;

    @JsonSchemaTitle("Description")
    private String description;

    @Valid
    @NotNull
    private AuditDetails auditDetails;
}
