package io.pivotal.places.view;

import com.kjetland.jackson.jsonSchema.annotations.JsonSchemaTitle;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PlaceSummaries {
    @JsonSchemaTitle("Places")
    private List<PlaceSummary> places;
}
