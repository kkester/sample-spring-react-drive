package io.pivotal.drive.league;

import io.pivotal.drive.league.games.GamesService;
import io.pivotal.drive.league.players.PlayersService;
import io.pivotal.drive.league.standings.StandingsService;
import io.pivotal.drive.mediatype.DriveResource;
import io.pivotal.drive.mediatype.DriveResourceGenerator;
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
    private final DriveResourceGenerator resourceGenerator;

    @GetMapping("/league")
    @SneakyThrows
    public DriveResource<League> getLeague() {
        League league = League.builder()
                .topTeams(standingsService.getTopTeams())
                .topPlayers(playersService.getTopPlayers())
                .latestResults(gamesService.getLatestResults())
                .build();
        return resourceGenerator.createDriveResource(MAIN_LINKS, league);
    }
}
