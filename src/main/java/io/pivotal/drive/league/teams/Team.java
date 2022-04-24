package io.pivotal.drive.league.teams;

import io.pivotal.drive.league.players.PlayerStatsSummary;
import io.pivotal.drive.league.standings.TeamStanding;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@Builder
@ToString
public class Team {
    private String name;
    private String city;
    private TeamStanding standing;
    private int pointsFor;
    private int pointsAgainst;
    private List<PlayerStatsSummary> topPlayers;
}
