package io.pivotal.league.games.view;

import com.kjetland.jackson.jsonSchema.annotations.JsonSchemaBool;
import com.kjetland.jackson.jsonSchema.annotations.JsonSchemaInject;
import com.kjetland.jackson.jsonSchema.annotations.JsonSchemaTitle;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@Builder
@ToString
@JsonSchemaInject(bools = {@JsonSchemaBool(path = "readOnly", value = true)})
public class Game {
    private Teams teams;
    private Score score;
    @JsonSchemaTitle("Home Plays")
    private List<PlaySummary> homePlays;
    @JsonSchemaTitle("Visitor Plays")
    private List<PlaySummary> visitorPlays;
}
