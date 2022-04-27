package io.pivotal.places.view;

import com.kjetland.jackson.jsonSchema.annotations.JsonSchemaTitle;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Getter
@Builder
@ToString
public class NewPlace {
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
}
