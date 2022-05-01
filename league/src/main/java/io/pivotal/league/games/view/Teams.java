package io.pivotal.league.games.view;

import com.kjetland.jackson.jsonSchema.annotations.JsonSchemaBool;
import com.kjetland.jackson.jsonSchema.annotations.JsonSchemaInject;
import com.kjetland.jackson.jsonSchema.annotations.JsonSchemaTitle;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class Teams {
    @JsonSchemaTitle("Home")
    @JsonSchemaInject(bools = {@JsonSchemaBool(path = "readOnly", value = true)})
    private String home;

    @JsonSchemaTitle("Visitor")
    @JsonSchemaInject(bools = {@JsonSchemaBool(path = "readOnly", value = true)})
    private String visitor;
}
