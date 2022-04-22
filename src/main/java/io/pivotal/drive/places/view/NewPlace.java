package io.pivotal.drive.places.view;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class NewPlace {
    @JsonProperty(required = true)
    private String city;
    @JsonProperty(required = true)
    private States state;
    @JsonProperty(required = true)
    private String country;
    private String description;
}
