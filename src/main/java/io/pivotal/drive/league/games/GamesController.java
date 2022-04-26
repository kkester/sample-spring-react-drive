package io.pivotal.drive.league.games;

import io.pivotal.drive.league.games.view.Game;
import io.pivotal.drive.league.games.view.GameSummaries;
import io.pivotal.drive.league.games.view.TeamGameSummaries;
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

import static io.pivotal.drive.league.games.GameLinkConstants.gameLinks;

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
        return resourceGenerator.createDriveResource(gameLinks(gameId), gamesService.getGameById(gameId));
    }

    @GetMapping("/games")
    @SneakyThrows
    public DriveDataResource getGames() {
        GameSummaries gameSummaries = gamesService.getLatestGames();
        return gameSummariesResourceGenerator.generateDriveResource(gameSummaries);
    }
}
