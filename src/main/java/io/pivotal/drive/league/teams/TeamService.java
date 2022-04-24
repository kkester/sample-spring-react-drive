package io.pivotal.drive.league.teams;

import io.pivotal.drive.error.ApiError;
import io.pivotal.drive.error.ApplicationException;
import io.pivotal.drive.league.model.GameEntity;
import io.pivotal.drive.league.model.TeamEntity;
import io.pivotal.drive.league.players.PlayerStatsSummary;
import io.pivotal.drive.league.players.PlayersService;
import io.pivotal.drive.league.repositories.GameRepository;
import io.pivotal.drive.league.repositories.PlayerRepository;
import io.pivotal.drive.league.repositories.TeamRepository;
import io.pivotal.drive.league.standings.TeamStanding;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

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
        int pointsFor = games.stream()
                .map(game -> game.getHomeTeam().getId().equals(teamId) ? game.getHomeTeamPoints() : game.getVisitingTeamPoints())
                .mapToInt(Integer::intValue)
                .sum();
        int pointsAgainst = games.stream()
                .map(game -> game.getHomeTeam().getId().equals(teamId) ? game.getVisitingTeamPoints() : game.getHomeTeamPoints())
                .mapToInt(Integer::intValue)
                .sum();

        List<PlayerStatsSummary> topPlayers = playersService.getTopPlayerForTeam(teamId);
        return Team.builder()
                .name(teamEntity.getName())
                .city(teamEntity.getCity())
                .pointsFor(pointsFor)
                .pointsAgainst(pointsAgainst)
                .topPlayers(topPlayers)
                .standing(TeamStanding.builder()
                        .teamName(teamEntity.getName())
                        .wins(teamEntity.getWins())
                        .loses(teamEntity.getLosses())
                        .ties(teamEntity.getTies())
                        .points((teamEntity.getWins() * 2) + teamEntity.getTies())
                        .total(teamEntity.getWins() + teamEntity.getLosses() + teamEntity.getTies())
                        .build())
                .build();
    }
}
