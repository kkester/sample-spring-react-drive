package io.pivotal.league.games.view;

import com.kjetland.jackson.jsonSchema.annotations.JsonSchemaTitle;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@Builder
@ToString
public class GameDetails {
    private Teams teams;
    private Score score;
    @JsonSchemaTitle("Home Plays")
    private List<PlaySummary> homePlays;
    @JsonSchemaTitle("Visitor Plays")
    private List<PlaySummary> visitorPlays;
}
