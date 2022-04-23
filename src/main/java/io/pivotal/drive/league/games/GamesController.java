package io.pivotal.drive.league.games;

import com.fasterxml.jackson.module.jsonSchema.JsonSchemaGenerator;
import io.pivotal.drive.league.model.GameEntity;
import io.pivotal.drive.mediatype.DriveLink;
import io.pivotal.drive.mediatype.DriveResource;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static io.pivotal.drive.league.LeagueLinkConstants.*;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
public class GamesController {

    private final GamesService gamesService;
    private final JsonSchemaGenerator schemaGenerator;
    private final GameSummariesResourceGenerator gameSummariesResourceGenerator;

    @GetMapping("/teams/{teamId}/games")
    public List<GameEntity> getTeamGames(@PathVariable UUID teamId) {
        return gamesService.getTeamGames(teamId);
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
    public DriveResource<GameSummariesDriveResource> getGames() {
        GameSummaries gamesSummaries = gamesService.getLatestGames();
        return gameSummariesResourceGenerator.generateDriveResource(gamesSummaries);
    }
}
