package io.pivotal.league.players;

import io.pivotal.league.model.PlayEntity;
import io.pivotal.league.model.PlayerEntity;
import io.pivotal.league.players.view.*;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PlayerViewConverter {
    public TeamPlayerStatsSummary convertToTeamPlayerStatsSummary(PlayerEntity playerEntity) {
        return TeamPlayerStatsSummary.builder()
                .id(playerEntity.getId())
                .player(playerEntity.getName())
                .points(playerEntity.getPoints())
                .rating(playerEntity.getRating())
                .highestRating(playerEntity.getHighestRating())
                .lowestRating(playerEntity.getLowestRating())
                .build();
    }

    public PlayerStatsSummary convertToPlayerStatsSummary(PlayerEntity playerEntity, Integer gamesPlayed) {
        return PlayerStatsSummary.builder()
                .id(playerEntity.getId())
                .player(playerEntity.getName())
                .teamName(playerEntity.getTeam().getName())
                .gamesPlayed(gamesPlayed)
                .points(playerEntity.getPoints())
                .rating(playerEntity.getRating())
                .build();
    }

    public PlayerSummary convertToPlayerSummary(PlayerEntity playerEntity) {
        return PlayerSummary.builder()
                .id(playerEntity.getId())
                .player(playerEntity.getName())
                .homeTown(playerEntity.getHomeTown())
                .homeState(playerEntity.getHomeState())
                .birthDate(playerEntity.getBirthDate())
                .build();
    }

    public Player convertToPlayer(PlayerEntity playerEntity, List<PlayEntity> plays) {
        return Player.builder()
                .teamId(playerEntity.getTeam().getId())
                .teamName(playerEntity.getTeam().getName())
                .info(PlayerInfo.builder()
                        .name(playerEntity.getName())
                        .homeTown(playerEntity.getHomeTown())
                        .homeState(playerEntity.getHomeState())
                        .birthDate(playerEntity.getBirthDate())
                        .build())
                .stats(PlayerStats.builder()
                        .gamesPlayed(plays.size())
                        .points(playerEntity.getPoints())
                        .rating(playerEntity.getRating())
                        .highestRating(playerEntity.getHighestRating())
                        .lowestRating(playerEntity.getLowestRating())
                        .build())
                .games(plays.stream()
                        .map(play -> PlayerGameStats.builder()
                                .opponent(play.getGame().getOpponentFor(playerEntity.getTeam()).getName())
                                .points(play.isScored() ? 1 : 0)
                                .gameTime(play.getGame().getGameTime())
                                .build())
                        .sorted(Comparator.comparing(PlayerGameStats::getGameTime).reversed())
                        .collect(Collectors.toList()))
                .build();
    }
}
