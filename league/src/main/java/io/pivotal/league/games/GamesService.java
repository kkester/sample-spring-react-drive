package io.pivotal.league.games;

import io.pivotal.common.error.ApplicationException;
import io.pivotal.league.games.view.*;
import io.pivotal.league.model.GameEntity;
import io.pivotal.league.model.PlayEntity;
import io.pivotal.league.model.TeamEntity;
import io.pivotal.league.repositories.GameRepository;
import io.pivotal.league.repositories.PlayRepository;
import io.pivotal.league.repositories.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GamesService {

    public static final String GAME_TIME_PROPERTY_NAME = "gameTime";

    private final GameViewConverter gameViewConverter;
    private final TeamRepository teamRepository;
    private final GameRepository gameRepository;
    private final PlayRepository playRepository;

    public TeamGameSummaries getTeamGames(UUID teamId) {
        TeamEntity teamEntity = teamRepository.findById(teamId)
                .orElseThrow(() -> ApplicationException.resourceNotFound("Team Not Found"));
        List<TeamGameSummary> games = gameRepository.findAllByHomeTeamIdOrVisitingTeamId(teamId, teamId, Sort.by(Sort.Direction.DESC, GAME_TIME_PROPERTY_NAME)).stream()
                .map(gameEntity -> gameViewConverter.convertToTeamGameSummary(teamId, gameEntity))
                .collect(Collectors.toList());
        return TeamGameSummaries.builder()
                .teamName(teamEntity.getName())
                .games(games)
                .build();
    }

    public GameSummaries getLatestGames() {
        Pageable pageable = PageRequest.of(0, 20, Sort.by(Sort.Direction.DESC, GAME_TIME_PROPERTY_NAME));
        Page<GameSummary> gamesPage = gameRepository.findAll(pageable)
                .map(gameViewConverter::convertToGameSummary);
        return GameSummaries.builder()
                .games(gamesPage.getContent())
                .build();
    }

    public Game getGameById(UUID gameId) {
        GameEntity gameEntity = gameRepository.findById(gameId)
                .orElseThrow(() -> ApplicationException.resourceNotFound("Game Not Found"));
        List<PlayEntity> plays = playRepository.findAllByGameId(gameId);
        return gameViewConverter.convertToGame(gameEntity, plays);
    }

    public List<GameSummary> getLatestResults() {
        Pageable pageable = PageRequest.of(0, 5, Sort.by(Sort.Direction.DESC, GAME_TIME_PROPERTY_NAME));
        Page<GameSummary> gamesPage = gameRepository.findAll(pageable)
                .map(gameViewConverter::convertToGameSummary);
        return gamesPage.getContent();
    }

    public String getTeamNameByTeamId(UUID teamId) {
        return teamRepository.findById(teamId)
                .map(TeamEntity::getName)
                .orElseThrow(() -> ApplicationException.resourceNotFound("Team Not Found"));
    }
}
