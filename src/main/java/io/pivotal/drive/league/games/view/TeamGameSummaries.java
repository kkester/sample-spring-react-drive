package io.pivotal.drive.league.games.view;

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
public class TeamGameSummaries {
    @JsonSchemaTitle("Team")
    private String teamName;
    @JsonSchemaTitle("Team Games")
    private List<TeamGameSummary> games;
}
