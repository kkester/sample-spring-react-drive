package io.pivotal.drive.league;

import com.github.javafaker.Faker;
import io.pivotal.drive.league.model.GameEntity;
import io.pivotal.drive.league.model.PlayEntity;
import io.pivotal.drive.league.model.PlayerEntity;
import io.pivotal.drive.league.model.TeamEntity;
import io.pivotal.drive.league.repositories.GameRepository;
import io.pivotal.drive.league.repositories.PlayRepository;
import io.pivotal.drive.league.repositories.PlayerRepository;
import io.pivotal.drive.league.repositories.TeamRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class Gamer {

    private final TeamRepository teamRepository;
    private final PlayerRepository playerRepository;
    private final GameRepository gameRepository;
    private final PlayRepository playRepository;

    @Scheduled(fixedDelay = 5000, initialDelay = 15000)
    public void scheduleFixedDelayTask() {
        GameEntity game = selectTeams();
        if (game == null) {
            return;
        }
        log.info("Initiating game between {} and {} ", game.getHomeTeam(), game.getVisitingTeam());

        List<PlayerEntity> homePlayers = playerRepository.findAllByTeamId(game.getHomeTeam().getId());
        List<PlayerEntity> visitingPlayers = playerRepository.findAllByTeamId(game.getVisitingTeam().getId());

        playGame(homePlayers, visitingPlayers, game);

        recordResults(game.getHomeTeam(), game.getVisitingTeam(), game);
    }

    private GameEntity selectTeams() {
        List<TeamEntity> teams = teamRepository.findByOrderByTotalGames();
        TeamEntity homeTeamEntity = teams.get(0);
        teams.remove(homeTeamEntity);
        if (homeTeamEntity.getTotalGames() >= 50) {
            return null;
        }

        List<TeamEntity> visitors = teams.stream()
                .filter(team -> team.getTotalGames() <= homeTeamEntity.getTotalGames())
                .collect(Collectors.toList());

        Faker faker = new Faker();
        Integer index = faker.random().nextInt(0, visitors.size() - 1);
        TeamEntity visitingTeamEntity = teams.get(index);
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
            processScored(game, player, home, chance);
        } else {
            processMissedShot(player, home, chance);
        }
        playerRepository.save(player);
        playRepository.save(play);
    }

    private void processScored(GameEntity game, PlayerEntity player, boolean home, Integer chance) {
        log.info("{} Player {} with rating {} scores!!! on chance {}", home ? "Home" : "Visitor", player.getName(), player.getRating(), chance);
        player.setScored(true);
        if (player.getRating() == 2) {
            player.setRating(5);
        } else if (player.isScored() && player.getRating() < 8) {
            player.setRating(player.getRating() + 1);
            if (player.getHighestRating() < player.getRating()) {
                player.setHighestRating(player.getRating());
            }
        }
        player.setPoints(player.getPoints() + 1);
        if (home) {
            game.setHomeTeamPoints(game.getHomeTeamPoints() + 1);
        } else {
            game.setVisitingTeamPoints(game.getVisitingTeamPoints() + 1);
        }
    }

    private void processMissedShot(PlayerEntity player, boolean home, Integer chance) {
        log.info("{} Player {} with rating {} misses on chance {}", home ? "Home" : "Visitor", player.getName(), player.getRating(), chance);
        if (player.getRating() == 8) {
            player.setRating(5);
        } else if (!player.isScored() && player.getRating() > 2) {
            player.setRating(player.getRating() - 1);
            if (player.getLowestRating() > player.getRating()) {
                player.setLowestRating(player.getRating());
            }
        }
        player.setScored(false);
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
        log.info("Saving game {}", game);
    }
}
