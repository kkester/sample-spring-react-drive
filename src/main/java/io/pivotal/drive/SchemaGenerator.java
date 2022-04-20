package io.pivotal.drive;

import com.fasterxml.jackson.module.jsonSchema.JsonSchema;
import com.fasterxml.jackson.module.jsonSchema.JsonSchemaGenerator;
import io.pivotal.drive.view.Place;
import io.pivotal.drive.view.PlaceSummaries;
import io.pivotal.drive.view.PlaceSummary;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class SchemaGenerator {

    private final JsonSchemaGenerator schemaGenerator;

    @SneakyThrows
    public JsonSchema getPlaceSummariesSchema() {
        JsonSchema jsonSchema = schemaGenerator.generateSchema(PlaceSummaries.class);
        jsonSchema.setReadonly(true);
        return jsonSchema;
    }

    @SneakyThrows
    public JsonSchema getPlaceSchema() {
        return schemaGenerator.generateSchema(Place.class);
    }
}
