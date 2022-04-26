package io.pivotal.drive.league.teams.view;

import com.kjetland.jackson.jsonSchema.annotations.JsonSchemaBool;
import com.kjetland.jackson.jsonSchema.annotations.JsonSchemaInject;
import com.kjetland.jackson.jsonSchema.annotations.JsonSchemaTitle;
import io.pivotal.drive.league.players.view.TeamPlayerStatsSummary;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@Builder
@ToString
@JsonSchemaInject(bools = {@JsonSchemaBool(path = "readOnly", value = true)})
public class Team {
    @JsonSchemaTitle("Name")
    private String name;
    @JsonSchemaTitle("City")
    private String city;
    private TeamRecord standing;
    @JsonSchemaTitle("Points For")
    private int pointsFor;
    @JsonSchemaTitle("Points Against")
    private int pointsAgainst;
    @JsonSchemaTitle("Avg Player Rating")
    private double averagePlayerRating;
    @JsonSchemaTitle("Top Players")
    private List<TeamPlayerStatsSummary> topPlayers;
}
