package io.pivotal.places.view;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kjetland.jackson.jsonSchema.annotations.JsonSchemaBool;
import com.kjetland.jackson.jsonSchema.annotations.JsonSchemaInject;
import com.kjetland.jackson.jsonSchema.annotations.JsonSchemaTitle;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Builder
@ToString
public class AuditDetails {
    @JsonSchemaTitle("Date Visited")
    private LocalDate visited;

    @JsonSchemaTitle("Planned Visit Date")
    @NotNull(message = "Planned Visit Date is required")
    private LocalDate plannedVisitDate;

    @JsonSchemaTitle("Created On")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JsonSchemaInject(bools = {@JsonSchemaBool(path = "readOnly", value = true)})
    private LocalDate createdDate;

    @JsonSchemaTitle("Last Updated")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JsonSchemaInject(bools = {@JsonSchemaBool(path = "readOnly", value = true)})
    private LocalDate lastUpdatedDate;
}
