package io.pivotal.league.teams.view;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kjetland.jackson.jsonSchema.annotations.JsonSchemaTitle;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.UUID;

@Getter
@Builder
@ToString
public class TeamSummary {
    @JsonIgnore
    private UUID id;
    @JsonSchemaTitle("Team Name")
    private String team;
    @JsonSchemaTitle("Games Played")
    private int gamesPlayed;
    @JsonSchemaTitle("Points")
    private int points;
    @JsonSchemaTitle("Points For")
    private int pointsFor;
    @JsonSchemaTitle("Points Against")
    private int pointsAgainst;
    @JsonSchemaTitle("Average Player Rating")
    private double averagePlayerRating;
    @JsonSchemaTitle("Last 10 Games")
    private String latestRecord;
}
