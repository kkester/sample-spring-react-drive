package io.pivotal.league;

import io.pivotal.drive.mediatype.DriveDataResource;
import io.pivotal.drive.mediatype.DriveResource;
import io.pivotal.drive.mediatype.DriveResourceGenerator;
import io.pivotal.league.games.GamesService;
import io.pivotal.league.games.view.GamesPage;
import io.pivotal.league.players.PlayersService;
import io.pivotal.league.players.view.PlayerStatsSummary;
import io.pivotal.league.standings.StandingsService;
import io.pivotal.league.standings.view.TeamStanding;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

import static io.pivotal.league.LeagueLinkConstants.MAIN_LINKS;
import static io.pivotal.league.players.PlayerLinkConstants.playerViewLink;
import static io.pivotal.league.teams.TeamLinkConstants.teamViewLink;

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
    public DriveDataResource getLeague() {
        GamesPage latestResults = gamesService.getLatestResults();
        League league = League.builder()
                .topTeams(standingsService.getTopTeams())
                .topPlayers(playersService.getTopPlayers())
                .latestResults(latestResults)
                .build();

        Map<String,Object> data = resourceGenerator.createData(league);
        List<DriveResource<TeamStanding>> topTeams = resourceGenerator.createDriveResourceList(
                league.getTopTeams(), teamStanding -> teamViewLink(teamStanding.getTeamId(), teamStanding.getTeam()));
        List<DriveResource<PlayerStatsSummary>> topPlayers = resourceGenerator.createDriveResourceList(
                league.getTopPlayers(), player -> playerViewLink(player.getId(), player.getPlayer()));
        data.put("topTeams", topTeams);
        data.put("topPlayers", topPlayers);
        return resourceGenerator.createDriveDataResource(MAIN_LINKS, data, League.class);
    }
}
