package io.pivotal.league;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;
import io.pivotal.league.games.GamesService;
import io.pivotal.league.games.view.GameSummaries;
import io.pivotal.league.games.view.GamesPage;
import io.pivotal.league.players.PlayersService;
import io.pivotal.league.standings.StandingsService;
import io.pivotal.drive.mediatype.DriveResource;
import io.pivotal.drive.mediatype.DriveResourceGenerator;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

import static io.pivotal.league.LeagueLinkConstants.MAIN_LINKS;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
public class LeagueController {

    private final StandingsService standingsService;
    private final PlayersService playersService;
    private final GamesService gamesService;
    private final DriveResourceGenerator resourceGenerator;

    @GetMapping("/league")
    @SneakyThrows
    public DriveResource<League> getLeague() {
        GamesPage latestResults = gamesService.getLatestResults();
        League league = League.builder()
                .topTeams(standingsService.getTopTeams())
                .topPlayers(playersService.getTopPlayers())
                .latestResults(latestResults)
                .build();
        return resourceGenerator.createDriveResource(MAIN_LINKS, league);
    }
}
