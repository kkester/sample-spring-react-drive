package io.pivotal.drive.places;

import com.fasterxml.jackson.databind.JsonNode;
import com.kjetland.jackson.jsonSchema.JsonSchemaGenerator;
import io.pivotal.drive.places.view.NewPlace;
import io.pivotal.drive.places.view.Place;
import io.pivotal.drive.places.view.PlaceSummaries;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PlaceTypesSchemaGenerator {

    private final JsonSchemaGenerator jsonSchemaGenerator;

    @SneakyThrows
    public JsonNode getPlaceSummariesSchema() {
        return jsonSchemaGenerator.generateJsonSchema(PlaceSummaries.class);
    }

    @SneakyThrows
    public JsonNode getPlaceSchema() {
        return jsonSchemaGenerator.generateJsonSchema(Place.class);
    }

    @SneakyThrows
    public JsonNode getNewPlaceSchema() {
        return jsonSchemaGenerator.generateJsonSchema(NewPlace.class);
    }
}
