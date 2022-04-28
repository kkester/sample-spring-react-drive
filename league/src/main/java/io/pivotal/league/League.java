package io.pivotal.league;

import com.kjetland.jackson.jsonSchema.annotations.JsonSchemaTitle;
import io.pivotal.league.games.view.LatestResultsSummaries;
import io.pivotal.league.players.view.PlayerStatsSummary;
import io.pivotal.league.standings.view.TeamStanding;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class League {
    @JsonSchemaTitle("Top Teams")
    private List<TeamStanding> topTeams;
    @JsonSchemaTitle("Top Players")
    private List<PlayerStatsSummary> topPlayers;
    private LatestResultsSummaries latestResults;
}
