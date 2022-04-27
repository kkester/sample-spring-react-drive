package io.pivotal.places.view;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kjetland.jackson.jsonSchema.annotations.JsonSchemaTitle;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
public class PlaceSummary {
    @JsonIgnore
    private UUID id;
    @JsonSchemaTitle("City")
    private String city;
    @JsonSchemaTitle("State")
    private States state;
    @JsonSchemaTitle("Country")
    private String country;
    @JsonSchemaTitle("Date Visited")
    private LocalDate visited;
}
