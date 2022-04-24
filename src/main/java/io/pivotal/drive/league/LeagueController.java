package io.pivotal.drive.league;

import com.fasterxml.jackson.module.jsonSchema.JsonSchemaGenerator;
import io.pivotal.drive.league.games.GamesService;
import io.pivotal.drive.league.players.PlayersService;
import io.pivotal.drive.league.standings.StandingsService;
import io.pivotal.drive.league.teams.TeamService;
import io.pivotal.drive.mediatype.DriveResource;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static io.pivotal.drive.league.LeagueLinkConstants.MAIN_LINKS;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
public class LeagueController {

    private final StandingsService standingsService;
    private final PlayersService playersService;
    private final GamesService gamesService;
    private final JsonSchemaGenerator schemaGenerator;

    @GetMapping("/league")
    @SneakyThrows
    public DriveResource<League> getLeague() {
        return DriveResource.<League>builder()
                .links(MAIN_LINKS)
                .data(League.builder()
                        .topTeams(standingsService.getTopTeams())
                        .topPlayers(playersService.getTopPlayers())
                        .latestResults(gamesService.getLatestResults())
                        .build())
                .schema(schemaGenerator.generateSchema(League.class))
                .build();
    }
}
