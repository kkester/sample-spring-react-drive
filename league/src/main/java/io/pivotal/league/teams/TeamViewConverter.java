package io.pivotal.league.teams;

import io.pivotal.league.PointsCalculater;
import io.pivotal.league.games.GameViewConverter;
import io.pivotal.league.games.view.GameSummary;
import io.pivotal.league.model.GameEntity;
import io.pivotal.league.model.PlayerEntity;
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
                        .points(PointsCalculater.calculatePoints(teamEntity))
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
                .name(teamEntity.getName())
                .gamesPlayed(teamEntity.getWins() + teamEntity.getLosses() + teamEntity.getTies())
                .points(PointsCalculater.calculatePoints(teamEntity))
                .pointsFor(pointsFor)
                .pointsAgainst(pointsAgainst)
                .averagePlayerRating(avgRating)
                .latestRecord(latestRecord)
                .build();
    }

    private String calculateLatestResults(List<GameEntity> games, UUID teamId) {
        int wins = 0;
        int losses = 0;
        int ties = 0;
        for (GameEntity gameEntity : games) {
            if (gameEntity.getHomeTeamPoints() == gameEntity.getVisitingTeamPoints()) {
                ties = ties  + 1;
            } else if (gameEntity.getHomeTeam().getId().equals(teamId)) {
                if (gameEntity.getHomeTeamPoints() > gameEntity.getVisitingTeamPoints()) {
                    wins = wins + 1;
                } else {
                    losses = losses + 1;
                }
            } else {
                if (gameEntity.getHomeTeamPoints() > gameEntity.getVisitingTeamPoints()) {
                    losses = losses + 1;
                } else {
                    wins = wins + 1;
                }
            }
            if (wins + losses + ties >= 10) {
                break;
            }
        }
        return "" + wins + "-" + losses + "-" + ties;
    }

    private int sumPointsAgainst(List<GameEntity> games, UUID teamId) {
        return games.stream()
                .map(game -> game.getHomeTeam().getId().equals(teamId) ? game.getVisitingTeamPoints() : game.getHomeTeamPoints())
                .mapToInt(Integer::intValue)
                .sum();
    }

    private int sumPointsFor(List<GameEntity> games, UUID teamId) {
        return games.stream()
                .map(game -> game.getHomeTeam().getId().equals(teamId) ? game.getHomeTeamPoints() : game.getVisitingTeamPoints())
                .mapToInt(Integer::intValue)
                .sum();
    }
}
