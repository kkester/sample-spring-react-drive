package io.pivotal.drive.league.games;

import com.fasterxml.jackson.module.jsonSchema.JsonSchemaGenerator;
import io.pivotal.drive.mediatype.DriveLink;
import io.pivotal.drive.mediatype.DriveResource;
import io.pivotal.drive.mediatype.DriveResourceList;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.UUID;

import static io.pivotal.drive.league.LeagueLinkConstants.GAMES_LINK;
import static io.pivotal.drive.league.LeagueLinkConstants.HOME_LINK;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
public class GamesController {

    private final GamesService gamesService;
    private final JsonSchemaGenerator schemaGenerator;
    private final GameSummariesResourceGenerator gameSummariesResourceGenerator;

    @GetMapping("/teams/{teamId}/games")
    public DriveResourceList<GameSummary> getTeamGames(@PathVariable UUID teamId) {
        GameSummaries gameSummaries = gamesService.getTeamGames(teamId);
        return gameSummariesResourceGenerator.generateDriveResource(teamId, gameSummaries);
    }

    @GetMapping("/games/{gameId}")
    @SneakyThrows
    public DriveResource<Game> getGameById(@PathVariable UUID gameId) {
        Map<String, DriveLink> links = Map.of("home", HOME_LINK, "games", GAMES_LINK);
        return DriveResource.<Game>builder()
                .links(links)
                .data(gamesService.getGameById(gameId))
                .schema(schemaGenerator.generateSchema(Game.class))
                .build();
    }

    @GetMapping("/games")
    @SneakyThrows
    public DriveResourceList<GameSummary> getGames() {
        GameSummaries gameSummaries = gamesService.getLatestGames();
        return gameSummariesResourceGenerator.generateDriveResource(gameSummaries);
    }
}
