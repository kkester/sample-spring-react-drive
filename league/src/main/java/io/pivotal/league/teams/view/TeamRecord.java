package io.pivotal.league.teams.view;

import com.kjetland.jackson.jsonSchema.annotations.JsonSchemaBool;
import com.kjetland.jackson.jsonSchema.annotations.JsonSchemaInject;
import com.kjetland.jackson.jsonSchema.annotations.JsonSchemaTitle;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class TeamRecord {
    @JsonSchemaTitle("Games Played")
    @JsonSchemaInject(bools = {@JsonSchemaBool(path = "readOnly", value = true)})
    private int total;

    @JsonSchemaInject(bools = {@JsonSchemaBool(path = "readOnly", value = true)})
    @JsonSchemaTitle("Wins")
    private int wins;

    @JsonSchemaInject(bools = {@JsonSchemaBool(path = "readOnly", value = true)})
    @JsonSchemaTitle("Loses")
    private int loses;

    @JsonSchemaInject(bools = {@JsonSchemaBool(path = "readOnly", value = true)})
    @JsonSchemaTitle("Ties")
    private int ties;

    @JsonSchemaInject(bools = {@JsonSchemaBool(path = "readOnly", value = true)})
    @JsonSchemaTitle("Points")
    private Integer points;
}
