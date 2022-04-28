package io.pivotal.places.view;

import com.kjetland.jackson.jsonSchema.annotations.JsonSchemaTitle;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Builder
@ToString
public class NewPlace {
    @JsonSchemaTitle("City")
    @NotEmpty(message = "City is required")
    private String city;

    @JsonSchemaTitle("State")
    @NotNull(message = "State is required")
    private States state;

    @JsonSchemaTitle("Country")
    @NotEmpty(message = "Country is required")
    private String country;

    @JsonSchemaTitle("Description")
    private String description;
}
