package io.pivotal.league.games.view;

import com.kjetland.jackson.jsonSchema.annotations.JsonSchemaTitle;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class Game {
    @JsonSchemaTitle("Home")
    private GameTeam home;
    @JsonSchemaTitle("Visitor")
    private GameTeam visitor;
}
