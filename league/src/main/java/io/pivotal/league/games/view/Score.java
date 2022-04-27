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
@JsonSchemaInject(bools = {@JsonSchemaBool(path = "readOnly", value = true)})
public class Score {
    @JsonSchemaTitle("Score")
    private int homePoints;
    @JsonSchemaTitle("Score")
    private int visitorPoints;
}
