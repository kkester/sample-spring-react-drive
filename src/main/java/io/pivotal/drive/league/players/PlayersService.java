package io.pivotal.drive.league.players;

import io.pivotal.drive.error.ApiError;
import io.pivotal.drive.error.ApplicationException;
import io.pivotal.drive.league.model.PlayEntity;
import io.pivotal.drive.league.model.PlayerEntity;
import io.pivotal.drive.league.repositories.PlayRepository;
import io.pivotal.drive.league.repositories.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;

@Service
@RequiredArgsConstructor
public class PlayersService {

    public static final Sort POINTS_SORT = Sort.by(Sort.Direction.DESC, "points");

    private final PlayerRepository playerRepository;
    private final PlayRepository playRepository;

    public PlayerStatsSummaries getAllPlayers() {
        List<PlayerStatsSummary> players = playerRepository.findAll(POINTS_SORT).stream()
                .map(this::convert)
                .collect(Collectors.toList());
        return PlayerStatsSummaries.builder()
                .players(players)
                .build();
    }

    public PlayerStatsSummaries getPlayersForTeam(UUID teamId) {
        List<PlayerStatsSummary> players = playerRepository.findAllByTeamId(teamId, POINTS_SORT).stream()
                .map(this::convert)
                .collect(Collectors.toList());
        return PlayerStatsSummaries.builder()
                .players(players)
                .build();
    }

    private PlayerStatsSummary convert(PlayerEntity playerEntity) {
        return PlayerStatsSummary.builder()
                .id(playerEntity.getId())
                .name(playerEntity.getName())
                .teamName(playerEntity.getTeam().getName())
                .points(playerEntity.getPoints())
                .rating(playerEntity.getRating())
                .highestRating(playerEntity.getHighestRating())
                .lowestRating(playerEntity.getLowestRating())
                .build();
    }

    public PlayerSummaries getRosterForTeam(UUID teamId) {
        List<PlayerSummary> players = playerRepository.findAllByTeamId(teamId, POINTS_SORT).stream()
                .map(playerEntity -> PlayerSummary.builder()
                        .id(playerEntity.getId())
                        .name(playerEntity.getName())
                        .homeTown(playerEntity.getHomeTown())
                        .homeState(playerEntity.getHomeState())
                        .birthDate(playerEntity.getBirthDate())
                        .build())
                .collect(Collectors.toList());
        return PlayerSummaries.builder()
                .players(players)
                .build();
    }

    public Player getPlayerById(UUID playerId) {
        PlayerEntity playerEntity = playerRepository.findById(playerId)
                .orElseThrow(() -> new ApplicationException(HttpStatus.NOT_FOUND, ApiError.builder().build()));
        List<PlayEntity> plays = playRepository.findAllByPlayerId(playerId);
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
                        .collect(Collectors.toList()))
                .build();
    }

    public List<PlayerStatsSummary> getTopPlayerForTeam(UUID teamId) {
        List<PlayerStatsSummary> players = getPlayersForTeam(teamId).getPlayers();
        return asList(players.get(0), players.get(1), players.get(2));
    }

    public List<PlayerStatsSummary> getTopPlayers() {
        List<PlayerStatsSummary> players = getAllPlayers().getPlayers();
        return asList(players.get(0), players.get(1), players.get(2));
    }
}
