package io.pivotal.drive.league.games;

import io.pivotal.drive.error.ApiError;
import io.pivotal.drive.error.ApplicationException;
import io.pivotal.drive.league.model.GameEntity;
import io.pivotal.drive.league.model.PlayEntity;
import io.pivotal.drive.league.repositories.GameRepository;
import io.pivotal.drive.league.repositories.PlayRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GamesService {

    private final GameRepository gameRepository;
    private final PlayRepository playRepository;

    public List<GameEntity> getTeamGames(UUID teamId) {
        return null;
    }

    public GameSummaries getLatestGames() {
        Pageable pageable = PageRequest.of(0, 20, Sort.by(Sort.Direction.DESC, "gameTime"));
        Page<GameSummary> gamesPage = gameRepository.findAll(pageable)
                .map(gameEntity -> GameSummary.builder()
                        .id(gameEntity.getId())
                        .home(gameEntity.getHomeTeam().getName())
                        .homePoints(gameEntity.getHomeTeamPoints())
                        .visitor(gameEntity.getVisitingTeam().getName())
                        .visitorPoints(gameEntity.getVisitingTeamPoints())
                        .gameTime(gameEntity.getGameTime())
                        .build());
        return GameSummaries.builder()
                .games(gamesPage.getContent())
                .build();
    }

    public Game getGameById(UUID gameId) {
        GameEntity gameEntity = gameRepository.findById(gameId).orElseThrow(() -> new ApplicationException(HttpStatus.NOT_FOUND, ApiError.builder().build()));
        List<PlayEntity> plays = playRepository.findAllByGameId(gameId);
        return Game.builder()
                .teams(Teams.builder()
                        .home(gameEntity.getHomeTeam().getName())
                        .visitor(gameEntity.getVisitingTeam().getName())
                        .build())
                .score(Score.builder()
                        .homePoints(gameEntity.getHomeTeamPoints())
                        .visitorPoints(gameEntity.getVisitingTeamPoints())
                        .build())
                .plays(plays.stream()
                        .map(playEntity -> PlaySummary.builder()
                                .team(playEntity.getPlayer().getTeam().getName())
                                .playerName(playEntity.getPlayer().getName())
                                .description(playEntity.isScored() ?
                                        "Shoots and scores with a rating of " + playEntity.getPlayerRating() + " on a chance of " + playEntity.getChance() :
                                        "Shoots and misses with a rating of " + playEntity.getPlayerRating() + " on a chance of " + playEntity.getChance())
                                .build())
                        .collect(Collectors.toList()))
                .build();
    }
}
