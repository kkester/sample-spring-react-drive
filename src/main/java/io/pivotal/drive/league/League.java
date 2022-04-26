package io.pivotal.drive.league;

import com.kjetland.jackson.jsonSchema.annotations.JsonSchemaTitle;
import io.pivotal.drive.league.games.view.GameSummary;
import io.pivotal.drive.league.players.view.PlayerStatsSummary;
import io.pivotal.drive.league.standings.view.TeamStanding;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@Builder
@ToString
public class League {
    @JsonSchemaTitle("Top Teams")
    List<TeamStanding> topTeams;
    @JsonSchemaTitle("Top Players")
    List<PlayerStatsSummary> topPlayers;
    @JsonSchemaTitle("Latest Results")
    List<GameSummary> latestResults;
}
