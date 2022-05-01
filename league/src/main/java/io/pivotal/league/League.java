package io.pivotal.league;

import com.kjetland.jackson.jsonSchema.annotations.JsonSchemaTitle;
import io.pivotal.league.games.view.GameSummaries;
import io.pivotal.league.games.view.GamesPage;
import io.pivotal.league.players.view.PlayerStatsSummary;
import io.pivotal.league.standings.view.TeamStanding;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class League {
    private GamesPage latestResults;
    @JsonSchemaTitle("Top Teams")
    private List<TeamStanding> topTeams;
    @JsonSchemaTitle("Top Players")
    private List<PlayerStatsSummary> topPlayers;
}
