package io.pivotal.league.games.view;

import com.kjetland.jackson.jsonSchema.annotations.JsonSchemaTitle;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class PlaySummary {
    @JsonSchemaTitle("Player")
    private String playerName;
    @JsonSchemaTitle("Play")
    private String description;
}
