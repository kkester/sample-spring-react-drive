package io.pivotal.league.standings.view;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kjetland.jackson.jsonSchema.annotations.JsonSchemaTitle;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.UUID;

@Getter
@Builder
@ToString
public class TeamStanding {
    @JsonIgnore
    private UUID teamId;
    @JsonSchemaTitle("Team Name")
    private String team;
    @JsonSchemaTitle("GP")
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
