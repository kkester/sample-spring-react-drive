package io.pivotal.league.teams.view;

import com.kjetland.jackson.jsonSchema.annotations.JsonSchemaBool;
import com.kjetland.jackson.jsonSchema.annotations.JsonSchemaInject;
import com.kjetland.jackson.jsonSchema.annotations.JsonSchemaTitle;
import io.pivotal.league.games.view.TeamGameSummary;
import io.pivotal.league.players.view.TeamPlayerStatsSummary;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@Builder
@ToString
public class Team {
    @JsonSchemaTitle("Name")
    @JsonSchemaInject(bools = {@JsonSchemaBool(path = "readOnly", value = true)})
    private String name;

    @JsonSchemaTitle("City")
    @JsonSchemaInject(bools = {@JsonSchemaBool(path = "readOnly", value = true)})
    private String city;

    private TeamRecord standing;

    @JsonSchemaTitle("Points For")
    @JsonSchemaInject(bools = {@JsonSchemaBool(path = "readOnly", value = true)})
    private int pointsFor;

    @JsonSchemaTitle("Points Against")
    @JsonSchemaInject(bools = {@JsonSchemaBool(path = "readOnly", value = true)})
    private int pointsAgainst;

    @JsonSchemaTitle("Avg Player Rating")
    @JsonSchemaInject(bools = {@JsonSchemaBool(path = "readOnly", value = true)})
    private double averagePlayerRating;

    @JsonSchemaTitle("Top Players")
    private List<TeamPlayerStatsSummary> topPlayers;

    @JsonSchemaTitle("Latest Results")
    private List<TeamGameSummary> latestResults;
}
