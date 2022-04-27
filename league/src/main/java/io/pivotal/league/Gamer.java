package io.pivotal.league;

import com.github.javafaker.Faker;
import io.pivotal.league.model.GameEntity;
import io.pivotal.league.model.PlayEntity;
import io.pivotal.league.model.PlayerEntity;
import io.pivotal.league.model.TeamEntity;
import io.pivotal.league.repositories.GameRepository;
import io.pivotal.league.repositories.PlayRepository;
import io.pivotal.league.repositories.PlayerRepository;
import io.pivotal.league.repositories.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static io.pivotal.league.games.GamesService.GAME_TIME_PROPERTY_NAME;

@Component
@RequiredArgsConstructor
public class Gamer {

    private final TeamRepository teamRepository;
    private final PlayerRepository playerRepository;
    private final GameRepository gameRepository;
    private final PlayRepository playRepository;

    @Scheduled(fixedDelay = 7500, initialDelay = 15000)
    public void scheduleFixedDelayTask() {
        GameEntity game = selectTeams();
        if (game == null) {
            return;
        }

        List<PlayerEntity> homePlayers = playerRepository.findAllByTeamId(game.getHomeTeam().getId(), Sort.by(Sort.Direction.DESC, "points"));
        List<PlayerEntity> visitingPlayers = playerRepository.findAllByTeamId(game.getVisitingTeam().getId(), Sort.by(Sort.Direction.DESC, "points"));

        playGame(homePlayers, visitingPlayers, game);

        recordResults(game.getHomeTeam(), game.getVisitingTeam(), game);
    }

    private GameEntity selectTeams() {
        List<TeamEntity> teams = teamRepository.findAll(Sort.by("totalGames"));
        TeamEntity homeTeamEntity = teams.get(0);
        teams.remove(homeTeamEntity);
        if (homeTeamEntity.getTotalGames() >= 50) {
            return null;
        }
        UUID homeTeamEntityId = homeTeamEntity.getId();
        List<GameEntity> games = gameRepository.findAllByHomeTeamIdOrVisitingTeamId(homeTeamEntityId, homeTeamEntityId, Sort.by(Sort.Direction.DESC, GAME_TIME_PROPERTY_NAME));
        final List<TeamEntity> lastTeams;
        if (!games.isEmpty()) {
            games = games.subList(0, Math.min(games.size(), 8));
            lastTeams = games.stream()
                    .map(game -> game.getHomeTeam().getId().equals(homeTeamEntityId) ? game.getVisitingTeam() : game.getHomeTeam())
                    .collect(Collectors.toList());
        } else {
            lastTeams = Collections.emptyList();
        }
        teams.removeAll(lastTeams);

        TeamEntity visitingTeamEntity = teams.get(0);
        GameEntity gameEntity = GameEntity.builder()
                .homeTeam(homeTeamEntity)
                .visitingTeam(visitingTeamEntity)
                .gameTime(LocalDateTime.now())
                .build();
        return gameRepository.save(gameEntity);
    }

    private void playGame(List<PlayerEntity> homePlayers, List<PlayerEntity> visitingPlayers, GameEntity game) {
        homePlayers.forEach(player -> playPlayer(game, player, true));
        visitingPlayers.forEach(player -> playPlayer(game, player, false));
    }

    private void playPlayer(GameEntity game, PlayerEntity player, boolean home) {
        Faker faker = new Faker();
        Integer chance = faker.random().nextInt(0, 10);
        PlayEntity play = PlayEntity.builder()
                .game(game)
                .player(player)
                .playerRating(player.getRating())
                .chance(chance)
                .scored(chance < player.getRating())
                .build();
        if (play.isScored()) {
            processScored(game, player, home);
        } else {
            processMissedShot(player);
        }
        playerRepository.save(player);
        playRepository.save(play);
    }

    private void processScored(GameEntity game, PlayerEntity player, boolean home) {
        if (player.getRating() == 2) {
            player.setRating(5);
        } else if (player.isScored() && player.getRating() < 8) {
            player.setRating(player.getRating() + 1);
        }
        if (player.getHighestRating() < player.getRating()) {
            player.setHighestRating(player.getRating());
        }

        player.setPoints(player.getPoints() + 1);
        if (home) {
            game.setHomeTeamPoints(game.getHomeTeamPoints() + 1);
        } else {
            game.setVisitingTeamPoints(game.getVisitingTeamPoints() + 1);
        }
        player.setScored(true);
    }

    private void processMissedShot(PlayerEntity player) {
        if (player.getRating() == 8) {
            player.setRating(5);
            player.setScored(true);
        } else if (!player.isScored() && player.getRating() > 2) {
            player.setRating(player.getRating() - 1);
            player.setScored(false);
        }
        if (player.getLowestRating() > player.getRating()) {
            player.setLowestRating(player.getRating());
        }
    }

    private void recordResults(TeamEntity homeTeamEntity, TeamEntity visitingTeamEntity, GameEntity game) {
        homeTeamEntity.setTotalGames(homeTeamEntity.getTotalGames() + 1);
        visitingTeamEntity.setTotalGames(visitingTeamEntity.getTotalGames() + 1);
        if (game.getHomeTeamPoints() > game.getVisitingTeamPoints()) {
            homeTeamEntity.setWins(homeTeamEntity.getWins() + 1);
            visitingTeamEntity.setLosses(visitingTeamEntity.getLosses() + 1);
        } else if (game.getHomeTeamPoints() == game.getVisitingTeamPoints()) {
            homeTeamEntity.setTies(homeTeamEntity.getTies() + 1);
            visitingTeamEntity.setTies(visitingTeamEntity.getTies() + 1);
        } else {
            homeTeamEntity.setLosses(homeTeamEntity.getLosses() + 1);
            visitingTeamEntity.setWins(visitingTeamEntity.getWins() + 1);
        }
        teamRepository.save(homeTeamEntity);
        teamRepository.save(visitingTeamEntity);
        gameRepository.save(game);
    }
}
