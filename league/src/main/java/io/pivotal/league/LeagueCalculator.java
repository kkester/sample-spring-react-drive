package io.pivotal.league;

import io.pivotal.league.model.GameEntity;
import io.pivotal.league.model.TeamEntity;

import java.util.List;
import java.util.UUID;

public class LeagueCalculator {
    public static int calculatePoints(TeamEntity teamEntity) {
        return (teamEntity.getWins() * 3) + teamEntity.getTies();
    }

    public static String calculateLatestResults(List<GameEntity> games, UUID teamId) {
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

    public static int sumPointsAgainst(List<GameEntity> games, UUID teamId) {
        return games.stream()
                .map(game -> game.getHomeTeam().getId().equals(teamId) ? game.getVisitingTeamPoints() : game.getHomeTeamPoints())
                .mapToInt(Integer::intValue)
                .sum();
    }

    public static int sumPointsFor(List<GameEntity> games, UUID teamId) {
        return games.stream()
                .map(game -> game.getHomeTeam().getId().equals(teamId) ? game.getHomeTeamPoints() : game.getVisitingTeamPoints())
                .mapToInt(Integer::intValue)
                .sum();
    }

    private LeagueCalculator() {
    }
}
