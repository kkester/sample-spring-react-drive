package io.pivotal.league.teams;

import io.pivotal.common.error.ApplicationException;
import io.pivotal.league.model.GameEntity;
import io.pivotal.league.model.TeamEntity;
import io.pivotal.league.players.PlayerViewConverter;
import io.pivotal.league.players.PlayersService;
import io.pivotal.league.players.view.TeamPlayerStatsSummary;
import io.pivotal.league.repositories.GameRepository;
import io.pivotal.league.repositories.TeamRepository;
import io.pivotal.league.teams.view.Team;
import io.pivotal.league.teams.view.TeamSummaries;
import io.pivotal.league.teams.view.TeamSummary;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static io.pivotal.league.games.GamesService.GAME_TIME_PROPERTY_NAME;

@Service
@RequiredArgsConstructor
public class TeamService {

    private final TeamViewConverter teamViewConverter;
    private final PlayerViewConverter playerViewConverter;
    private final TeamRepository teamRepository;
    private final GameRepository gameRepository;
    private final PlayersService playersService;

    public Team getTeamById(UUID teamId) {
        TeamEntity teamEntity = teamRepository.findById(teamId)
                .orElseThrow(() -> ApplicationException.resourceNotFound("Team Not Found"));

        List<GameEntity> games = gameRepository.findAllByHomeTeamIdOrVisitingTeamId(teamId, teamId, Sort.by(Sort.Direction.DESC, GAME_TIME_PROPERTY_NAME));
        List<TeamPlayerStatsSummary> topPlayers = playersService.getTopPlayerForTeam(teamId).stream()
                .map(playerViewConverter::convertToTeamPlayerStatsSummary)
                .collect(Collectors.toList());
        double avgRating = playersService.getAveragePlayerRating(teamId);
        return teamViewConverter.convertToTeam(teamEntity, games, topPlayers, avgRating);
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
        List<GameEntity> games = gameRepository.findAllByHomeTeamIdOrVisitingTeamId(teamId, teamId, Sort.by(Sort.Direction.DESC, GAME_TIME_PROPERTY_NAME));
        double avgRating = playersService.getAveragePlayerRating(teamId);
        return teamViewConverter.convertToTeamSummary(teamEntity, games, avgRating);
    }
}
