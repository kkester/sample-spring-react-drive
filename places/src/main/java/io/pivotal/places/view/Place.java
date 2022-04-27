package io.pivotal.places.view;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kjetland.jackson.jsonSchema.annotations.JsonSchemaTitle;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

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
    @NotNull
    private String city;

    @JsonSchemaTitle("State")
    @NotNull
    private States state;

    @JsonSchemaTitle("Country")
    @NotNull
    private String country;

    @JsonSchemaTitle("Description")
    private String description;

    @JsonSchemaTitle("Date Visited")
    private LocalDate visited;

    private AuditDetails auditDetails;
}
