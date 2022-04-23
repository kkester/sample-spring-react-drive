package io.pivotal.drive.league.players;

import io.pivotal.drive.league.model.PlayerEntity;
import io.pivotal.drive.league.repositories.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlayersService {

    private final PlayerRepository playerRepository;

    public PlayerSummaries getLatestGames() {
        List<PlayerSummary> players = playerRepository.findAll(Sort.by(Sort.Direction.DESC, "points")).stream()
                .map(playerEntity -> PlayerSummary.builder()
                        .name(playerEntity.getName())
                        .teamName(playerEntity.getTeam().getName())
                        .points(playerEntity.getPoints())
                        .rating(playerEntity.getRating())
                        .highestRating(playerEntity.getHighestRating())
                        .lowestRating(playerEntity.getLowestRating())
                        .build())
                .collect(Collectors.toList());
        return PlayerSummaries.builder()
                .players(players)
                .build();
    }

    public List<PlayerEntity> getPlayersForTeam(UUID teamId) {
        return null;
    }
}
