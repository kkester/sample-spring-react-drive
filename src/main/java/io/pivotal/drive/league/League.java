package io.pivotal.drive.league;

import io.pivotal.drive.league.games.GameSummary;
import io.pivotal.drive.league.players.PlayerStatsSummary;
import io.pivotal.drive.league.standings.TeamStanding;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@Builder
@ToString
public class League {
    List<TeamStanding> topTeams;
    List<PlayerStatsSummary> topPlayers;
    List<GameSummary> latestResults;
}
