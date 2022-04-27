package io.pivotal.league.players;

import io.pivotal.common.error.ApplicationException;
import io.pivotal.league.model.PlayEntity;
import io.pivotal.league.model.PlayerEntity;
import io.pivotal.league.players.view.*;
import io.pivotal.league.repositories.PlayRepository;
import io.pivotal.league.repositories.PlayerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;

@Service
@RequiredArgsConstructor
@Slf4j
public class PlayersService {

    public static final Sort POINTS_SORT = Sort.by(Sort.Direction.DESC, "points");

    private final PlayerViewConverter playerViewConverter;
    private final PlayerRepository playerRepository;
    private final PlayRepository playRepository;

    public PlayerStatsSummaries getAllPlayers() {
        List<PlayerStatsSummary> players = playerRepository.findAll(POINTS_SORT).stream()
                .map(playerEntity -> playerViewConverter.convertToPlayerStatsSummary(
                        playerEntity,
                        playRepository.countByPlayerId(playerEntity.getId())))
                .collect(Collectors.toList());
        return PlayerStatsSummaries.builder()
                .players(players)
                .build();
    }

    public TeamPlayerStatsSummaries getPlayersForTeam(UUID teamId) {
        List<TeamPlayerStatsSummary> players = playerRepository.findAllByTeamId(teamId, POINTS_SORT).stream()
                .map(playerViewConverter::convertToTeamPlayerStatsSummary)
                .collect(Collectors.toList());
        return TeamPlayerStatsSummaries.builder()
                .players(players)
                .build();
    }

    public PlayerSummaries getRosterForTeam(UUID teamId) {
        List<PlayerSummary> players = playerRepository.findAllByTeamId(teamId, POINTS_SORT).stream()
                .map(playerViewConverter::convertToPlayerSummary)
                .collect(Collectors.toList());
        return PlayerSummaries.builder()
                .players(players)
                .build();
    }

    public Player getPlayerById(UUID playerId) {
        PlayerEntity playerEntity = playerRepository.findById(playerId)
                .orElseThrow(() -> ApplicationException.resourceNotFound("Player Not Found"));
        List<PlayEntity> plays = playRepository.findAllByPlayerId(playerId);
        return playerViewConverter.convertToPlayer(playerEntity, plays);
    }

    public List<PlayerEntity> getTopPlayerForTeam(UUID teamId) {
        List<PlayerEntity> players = playerRepository.findAllByTeamId(teamId, POINTS_SORT);
        return asList(players.get(0), players.get(1), players.get(2));
    }

    public List<PlayerStatsSummary> getTopPlayers() {
        List<PlayerStatsSummary> players = getAllPlayers().getPlayers();
        return asList(players.get(0), players.get(1), players.get(2));
    }

    public double getAveragePlayerRating(UUID teamId) {
        BigDecimal totalRating = BigDecimal.valueOf(getPlayersForTeam(teamId).getPlayers().stream()
                .map(TeamPlayerStatsSummary::getRating)
                .mapToInt(Integer::intValue)
                .sum());
        return totalRating.divide(BigDecimal.valueOf(10), 1, RoundingMode.HALF_DOWN).doubleValue();
    }
}
