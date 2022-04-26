package io.pivotal.drive.league.teams;

import io.pivotal.drive.error.ApiError;
import io.pivotal.drive.error.ApplicationException;
import io.pivotal.drive.league.model.GameEntity;
import io.pivotal.drive.league.model.TeamEntity;
import io.pivotal.drive.league.players.PlayersService;
import io.pivotal.drive.league.players.view.TeamPlayerStatsSummary;
import io.pivotal.drive.league.repositories.GameRepository;
import io.pivotal.drive.league.repositories.TeamRepository;
import io.pivotal.drive.league.teams.view.Team;
import io.pivotal.drive.league.teams.view.TeamRecord;
import io.pivotal.drive.league.teams.view.TeamSummaries;
import io.pivotal.drive.league.teams.view.TeamSummary;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TeamService {

    private final TeamRepository teamRepository;
    private final GameRepository gameRepository;
    private final PlayersService playersService;

    public Team getTeamById(UUID teamId) {
        TeamEntity teamEntity = teamRepository.findById(teamId)
                .orElseThrow(() -> new ApplicationException(HttpStatus.NOT_FOUND, ApiError.builder().build()));

        List<GameEntity> games = gameRepository.findAllByHomeTeamIdOrVisitingTeamId(teamId, teamId, Sort.unsorted());
        int pointsFor = sumPointsFor(games, teamId);
        int pointsAgainst = sumPointsAgainst(games, teamId);

        List<TeamPlayerStatsSummary> topPlayers = playersService.getTopPlayerForTeam(teamId).stream()
                .map(player -> TeamPlayerStatsSummary.builder()
                        .id(player.getId())
                        .highestRating(player.getHighestRating())
                        .lowestRating(player.getLowestRating())
                        .rating(player.getRating())
                        .name(player.getName())
                        .points(player.getPoints())
                        .build())
                .collect(Collectors.toList());
        double avgRating = playersService.getAveragePlayerRating(teamId);
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
                        .points((teamEntity.getWins() * 2) + teamEntity.getTies())
                        .total(teamEntity.getWins() + teamEntity.getLosses() + teamEntity.getTies())
                        .build())
                .build();
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

    public TeamSummaries getAllTeams() {
        List<TeamSummary> teams = teamRepository.findAll().stream()
                .map(this::convertToTeamSummary)
                .sorted(Comparator.comparing(TeamSummary::getPoints).reversed())
                .collect(Collectors.toList());
        return TeamSummaries.builder().teams(teams).build();
    }

    private TeamSummary convertToTeamSummary(TeamEntity teamEntity) {
        UUID teamId = teamEntity.getId();
        List<GameEntity> games = gameRepository.findAllByHomeTeamIdOrVisitingTeamId(teamId, teamId, Sort.unsorted());
        int pointsFor = sumPointsFor(games, teamId);
        int pointsAgainst = sumPointsAgainst(games, teamId);
        double avgRating = playersService.getAveragePlayerRating(teamId);
        return TeamSummary.builder()
                .id(teamId)
                .name(teamEntity.getName())
                .gamesPlayed(teamEntity.getWins() + teamEntity.getLosses() + teamEntity.getTies())
                .points((teamEntity.getWins() * 2) + teamEntity.getTies())
                .pointsFor(pointsFor)
                .pointsAgainst(pointsAgainst)
                .averagePlayerRating(avgRating)
                .build();
    }
}
