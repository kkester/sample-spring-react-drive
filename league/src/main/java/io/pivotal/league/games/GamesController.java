package io.pivotal.league.games;

import io.pivotal.league.games.view.Game;
import io.pivotal.league.games.view.GameSummaries;
import io.pivotal.league.games.view.TeamGameSummaries;
import io.pivotal.drive.mediatype.DriveDataResource;
import io.pivotal.drive.mediatype.DriveResource;
import io.pivotal.drive.mediatype.DriveResourceGenerator;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

import static io.pivotal.league.games.GameLinkConstants.gameLinks;
import static io.pivotal.league.games.GameLinkConstants.teamGameLinks;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
public class GamesController {

    private final GamesService gamesService;
    private final DriveResourceGenerator resourceGenerator;
    private final GameSummariesResourceGenerator gameSummariesResourceGenerator;

    @GetMapping("/teams/{teamId}/games")
    public DriveDataResource getTeamGames(@PathVariable UUID teamId) {
        TeamGameSummaries gameSummaries = gamesService.getTeamGames(teamId);
        return gameSummariesResourceGenerator.generateTeamGameSummariesResource(teamId, gameSummaries);
    }

    @GetMapping("/games/{gameId}")
    @SneakyThrows
    public DriveResource<Game> getGameById(@PathVariable UUID gameId) {
        return resourceGenerator.createDriveResource(gameLinks(), gamesService.getGameById(gameId));
    }

    @GetMapping("/teams/{teamId}/games/{gameId}")
    public DriveResource<Game> getTeamGameById(@PathVariable UUID teamId, @PathVariable UUID gameId) {
        Game game = gamesService.getGameById(gameId);
        String teamName = gamesService.getTeamNameByTeamId(teamId);
        return resourceGenerator.createDriveResource(teamGameLinks(teamId, teamName), game);
    }

    @GetMapping("/games")
    @SneakyThrows
    public DriveDataResource getGames() {
        GameSummaries gameSummaries = gamesService.getLatestGames();
        return gameSummariesResourceGenerator.generateDriveResource(gameSummaries);
    }
}
