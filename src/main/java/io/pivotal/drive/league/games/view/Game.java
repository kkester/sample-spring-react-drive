package io.pivotal.drive.league.games.view;

import com.kjetland.jackson.jsonSchema.annotations.JsonSchemaTitle;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@Builder
@ToString
public class Game {
    private Teams teams;
    private Score score;
    @JsonSchemaTitle("Game Plays")
    private List<PlaySummary> plays;
}
