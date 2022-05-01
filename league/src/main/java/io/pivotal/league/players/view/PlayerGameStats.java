package io.pivotal.league.players.view;

import com.kjetland.jackson.jsonSchema.annotations.JsonSchemaTitle;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Builder
@ToString
public class PlayerGameStats {
    @JsonSchemaTitle("Opponent")
    private String opponent;

    @JsonSchemaTitle("Points")
    private int points;

    @JsonSchemaTitle("Game Time")
    private LocalDateTime gameTime;
}
