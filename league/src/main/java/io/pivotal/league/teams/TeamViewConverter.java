package io.pivotal.league.teams;

import io.pivotal.league.LeagueCalculator;
import io.pivotal.league.games.GameViewConverter;
import io.pivotal.league.model.GameEntity;
import io.pivotal.league.model.TeamEntity;
import io.pivotal.league.players.view.TeamPlayerStatsSummary;
import io.pivotal.league.teams.view.Team;
import io.pivotal.league.teams.view.TeamRecord;
import io.pivotal.league.teams.view.TeamSummary;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static io.pivotal.league.LeagueCalculator.*;

@Component
@RequiredArgsConstructor
public class TeamViewConverter {

    private final GameViewConverter gameViewConverter;

    public Team convertToTeam(TeamEntity teamEntity, List<GameEntity> games, List<TeamPlayerStatsSummary> topPlayers, double avgRating) {
        UUID teamId = teamEntity.getId();
        int pointsFor = sumPointsFor(games, teamId);
        int pointsAgainst = sumPointsAgainst(games, teamId);
        List<GameEntity> latestGames = games.size() > 2 ? games.subList(0, 3) : games;

        return Team.builder()
                .name(teamEntity.getName())
                .city(teamEntity.getCity())
                .pointsFor(pointsFor)
                .pointsAgainst(pointsAgainst)
                .topPlayers(topPlayers)
                .averagePlayerRating(avgRating)
                .standing(TeamRecord.builder()
                        .wins(teamEntity.getWins())
                        .loses(teamEntity.getLosses())
                        .ties(teamEntity.getTies())
                        .points(LeagueCalculator.calculatePoints(teamEntity))
                        .total(teamEntity.getWins() + teamEntity.getLosses() + teamEntity.getTies())
                        .build())
                .latestResults(latestGames.stream()
                        .map(gameEntity -> gameViewConverter.convertToTeamGameSummary(teamId, gameEntity))
                        .collect(Collectors.toList()))
                .build();
    }

    public TeamSummary convertToTeamSummary(TeamEntity teamEntity, List<GameEntity> games, double avgRating) {
        UUID teamId = teamEntity.getId();
        int pointsFor = sumPointsFor(games, teamId);
        int pointsAgainst = sumPointsAgainst(games, teamId);
        String latestRecord = calculateLatestResults(games, teamId);

        return TeamSummary.builder()
                .id(teamEntity.getId())
                .team(teamEntity.getName())
                .gamesPlayed(teamEntity.getWins() + teamEntity.getLosses() + teamEntity.getTies())
                .points(LeagueCalculator.calculatePoints(teamEntity))
                .pointsFor(pointsFor)
                .pointsAgainst(pointsAgainst)
                .averagePlayerRating(avgRating)
                .latestRecord(latestRecord)
                .build();
    }
}
