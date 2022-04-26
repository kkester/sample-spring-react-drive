package io.pivotal.drive.league.players.view;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kjetland.jackson.jsonSchema.annotations.JsonSchemaTitle;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.UUID;

@Getter
@Builder
@ToString
public class TeamPlayerStatsSummary {
    @JsonIgnore
    private UUID id;
    @JsonSchemaTitle("Player Name")
    private String name;
    @JsonSchemaTitle("Points")
    private Integer points;
    @JsonSchemaTitle("Rating")
    private int rating;
    @JsonSchemaTitle("Highest Rating")
    private int highestRating;
    @JsonSchemaTitle("Lowest Rating")
    private int lowestRating;
}
