package io.pivotal.league.players.view;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kjetland.jackson.jsonSchema.annotations.JsonSchemaTitle;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@Builder
@ToString
public class TeamPlayerStatsSummaries {
    @JsonIgnore
    private String teamName;
    @JsonSchemaTitle("Team Players")
    private List<TeamPlayerStatsSummary> players;
}
