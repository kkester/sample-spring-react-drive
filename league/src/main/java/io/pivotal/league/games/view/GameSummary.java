package io.pivotal.league.games.view;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kjetland.jackson.jsonSchema.annotations.JsonSchemaTitle;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
@ToString
public class GameSummary {
    @JsonIgnore
    private UUID id;
    @JsonSchemaTitle("Home")
    private String home;
    @JsonIgnore
    private UUID homeTeamId;
    @JsonSchemaTitle("Home Points")
    private int homePoints;
    @JsonSchemaTitle("Visitor")
    private String visitor;
    @JsonIgnore
    private UUID visitorTeamId;
    @JsonSchemaTitle("Visitor Points")
    private int visitorPoints;
    @JsonSchemaTitle("Game Time")
    private LocalDateTime gameTime;
}
