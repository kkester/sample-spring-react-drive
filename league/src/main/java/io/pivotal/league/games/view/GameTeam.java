package io.pivotal.league.games.view;

import com.kjetland.jackson.jsonSchema.annotations.JsonSchemaBool;
import com.kjetland.jackson.jsonSchema.annotations.JsonSchemaInject;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@JsonSchemaInject(bools = {@JsonSchemaBool(path = "readOnly", value = true)})
public class GameTeam {
    private String team;
    private Integer score;
}
