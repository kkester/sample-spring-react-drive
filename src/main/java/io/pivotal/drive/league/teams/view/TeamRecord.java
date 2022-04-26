package io.pivotal.drive.league.teams.view;

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
public class TeamRecord {
    @JsonSchemaTitle("Games Played")
    private int total;
    @JsonSchemaTitle("Wins")
    private int wins;
    @JsonSchemaTitle("Loses")
    private int loses;
    @JsonSchemaTitle("Ties")
    private int ties;
    @JsonSchemaTitle("Points")
    private Integer points;
}
