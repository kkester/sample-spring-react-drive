package io.pivotal.drive.league;

import com.fasterxml.jackson.module.jsonSchema.JsonSchema;
import com.fasterxml.jackson.module.jsonSchema.JsonSchemaGenerator;
import io.pivotal.drive.places.view.NewPlace;
import io.pivotal.drive.places.view.Place;
import io.pivotal.drive.places.view.PlaceSummaries;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LeagueSchemaGenerator {

    private final JsonSchemaGenerator jsonSchemaGenerator;

    @SneakyThrows
    public JsonSchema getPlaceSummariesSchema() {
        JsonSchema jsonSchema = jsonSchemaGenerator.generateSchema(PlaceSummaries.class);
        jsonSchema.setReadonly(true);
        return jsonSchema;
    }

    @SneakyThrows
    public JsonSchema getPlaceSchema() {
        return jsonSchemaGenerator.generateSchema(Place.class);
    }

    @SneakyThrows
    public JsonSchema getNewPlaceSchema() {
        return jsonSchemaGenerator.generateSchema(NewPlace.class);
    }
}
