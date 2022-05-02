package io.pivotal.league.players.view;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kjetland.jackson.jsonSchema.annotations.JsonSchemaTitle;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.UUID;

@Getter
@Builder
@ToString
public class PlayerStatsSummary {
    @JsonIgnore
    private UUID id;
    @JsonSchemaTitle("Player Name")
    private String player;
    @JsonSchemaTitle("Team Name")
    private String teamName;
    @JsonSchemaTitle("GP")
    private Integer gamesPlayed;
    @JsonSchemaTitle("Points")
    private Integer points;
    @JsonSchemaTitle("Rating")
    private int rating;
}
