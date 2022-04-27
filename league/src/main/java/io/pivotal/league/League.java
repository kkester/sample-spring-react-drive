package io.pivotal.league;

import com.kjetland.jackson.jsonSchema.annotations.JsonSchemaTitle;
import io.pivotal.league.games.view.GameSummary;
import io.pivotal.league.players.view.PlayerStatsSummary;
import io.pivotal.league.standings.view.TeamStanding;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@Builder
@ToString
public class League {
    @JsonSchemaTitle("Top Teams")
    private List<TeamStanding> topTeams;
    @JsonSchemaTitle("Top Players")
    private List<PlayerStatsSummary> topPlayers;
    @JsonSchemaTitle("Latest Results")
    private List<GameSummary> latestResults;
}
