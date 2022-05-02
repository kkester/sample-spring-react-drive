package io.pivotal.league.games;

import io.pivotal.league.games.view.*;
import io.pivotal.league.model.GameEntity;
import io.pivotal.league.model.PlayEntity;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class GameViewConverter {
    public TeamGameSummary convertToTeamGameSummary(UUID teamId, GameEntity gameEntity) {
        return TeamGameSummary.builder()
                .id(gameEntity.getId())
                .result(determineResult(teamId, gameEntity))
                .home(gameEntity.getHomeTeam().getName())
                .homePoints(gameEntity.getHomeTeamPoints())
                .visitor(gameEntity.getVisitingTeam().getName())
                .visitorPoints(gameEntity.getVisitingTeamPoints())
                .gameTime(gameEntity.getGameTime())
                .build();
    }

    private String determineResult(UUID teamId, GameEntity gameEntity) {
        if (gameEntity.getHomeTeamPoints() == gameEntity.getVisitingTeamPoints()) {
            return "T";
        } else if (teamId.equals(gameEntity.getHomeTeam().getId())) {
            return gameEntity.getHomeTeamPoints() > gameEntity.getVisitingTeamPoints() ? "W" : "L";
        }
        return gameEntity.getVisitingTeamPoints() > gameEntity.getHomeTeamPoints() ? "W" : "L";
    }

    public GameSummary convertToGameSummary(GameEntity gameEntity) {
        return GameSummary.builder()
                .id(gameEntity.getId())
                .home(gameEntity.getHomeTeam().getName())
                .homeTeamId(gameEntity.getHomeTeam().getId())
                .homePoints(gameEntity.getHomeTeamPoints())
                .visitor(gameEntity.getVisitingTeam().getName())
                .visitorTeamId(gameEntity.getVisitingTeam().getId())
                .visitorPoints(gameEntity.getVisitingTeamPoints())
                .gameTime(gameEntity.getGameTime())
                .build();
    }

    public GameDetails convertToGameDetails(GameEntity gameEntity, List<PlayEntity> plays) {
        UUID homeTeamId = gameEntity.getHomeTeam().getId();
        UUID visitorTeamId = gameEntity.getVisitingTeam().getId();
        return GameDetails.builder()
                .teams(Teams.builder()
                        .home(gameEntity.getHomeTeam().getName())
                        .visitor(gameEntity.getVisitingTeam().getName())
                        .build())
                .score(Score.builder()
                        .homePoints(gameEntity.getHomeTeamPoints())
                        .visitorPoints(gameEntity.getVisitingTeamPoints())
                        .build())
                .homePlays(plays.stream()
                        .filter(playEntity -> homeTeamId.equals(playEntity.getPlayer().getTeam().getId()))
                        .map(this::convertToPlaySummary)
                        .collect(Collectors.toList()))
                .visitorPlays(plays.stream()
                        .filter(playEntity -> visitorTeamId.equals(playEntity.getPlayer().getTeam().getId()))
                        .map(this::convertToPlaySummary)
                        .collect(Collectors.toList()))
                .build();
    }

    private PlaySummary convertToPlaySummary(PlayEntity playEntity) {
        return PlaySummary.builder()
                .playerName(playEntity.getPlayer().getName())
                .description(playEntity.isScored() ?
                        "Shoots and scores with a rating of " + playEntity.getPlayerRating() + " on a chance of " + playEntity.getChance() :
                        "Shoots and misses with a rating of " + playEntity.getPlayerRating() + " on a chance of " + playEntity.getChance())
                .build();
    }

    public Game convertToGame(GameEntity gameEntity) {
        if (gameEntity == null) {
            return null;
        }
        return Game.builder()
                .home(GameTeam.builder()
                        .team(gameEntity.getHomeTeam().getName())
                        .score(gameEntity.getHomeTeamPoints())
                        .build())
                .visitor(GameTeam.builder()
                        .team(gameEntity.getVisitingTeam().getName())
                        .score(gameEntity.getVisitingTeamPoints())
                        .build())
                .build();
    }

    public GamesPage convertToGamesPage(List<GameEntity> gameEntities) {
        Map<String,GameEntity> gameEntitiesMap = gameEntities.stream()
                .collect(Collectors.toMap(value -> "game"+gameEntities.indexOf(value), value -> value));
        LatestGames latestGames = LatestGames.builder()
                .game1(convertToGame(gameEntitiesMap.get("game0")))
                .game2(convertToGame(gameEntitiesMap.get("game1")))
                .game3(convertToGame(gameEntitiesMap.get("game2")))
                .game4(convertToGame(gameEntitiesMap.get("game3")))
                .game5(convertToGame(gameEntitiesMap.get("game4")))
                .build();
        return GamesPage.builder().latestGames(Collections.singletonList(latestGames)).build();
    }
}
