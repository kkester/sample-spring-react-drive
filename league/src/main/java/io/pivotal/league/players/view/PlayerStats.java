package io.pivotal.league.players.view;

import com.kjetland.jackson.jsonSchema.annotations.JsonSchemaBool;
import com.kjetland.jackson.jsonSchema.annotations.JsonSchemaInject;
import com.kjetland.jackson.jsonSchema.annotations.JsonSchemaTitle;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class PlayerStats {
    @JsonSchemaTitle("Games Played")
    @JsonSchemaInject(bools = {@JsonSchemaBool(path = "readOnly", value = true)})
    private int gamesPlayed;

    @JsonSchemaTitle("Points")
    @JsonSchemaInject(bools = {@JsonSchemaBool(path = "readOnly", value = true)})
    private int points;

    @JsonSchemaTitle("Rating")
    @JsonSchemaInject(bools = {@JsonSchemaBool(path = "readOnly", value = true)})
    private int rating;

    @JsonSchemaTitle("Highest Rating")
    @JsonSchemaInject(bools = {@JsonSchemaBool(path = "readOnly", value = true)})
    private int highestRating;

    @JsonSchemaTitle("Lowest Rating")
    @JsonSchemaInject(bools = {@JsonSchemaBool(path = "readOnly", value = true)})
    private int lowestRating;
}
