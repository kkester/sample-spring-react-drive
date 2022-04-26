package io.pivotal.drive.league.standings;

import io.pivotal.drive.league.standings.view.Standings;
import io.pivotal.drive.league.standings.view.TeamStanding;
import io.pivotal.drive.mediatype.DriveDataResource;
import io.pivotal.drive.mediatype.DriveResource;
import io.pivotal.drive.mediatype.DriveResourceGenerator;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

import static io.pivotal.drive.league.LeagueLinkConstants.MAIN_LINKS;
import static io.pivotal.drive.league.teams.TeamLinkConstants.teamViewLink;

@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
public class StandingsController {

    private final StandingsService standingsService;
    private final DriveResourceGenerator resourceGenerator;

    @GetMapping("/standings")
    @SneakyThrows
    public DriveDataResource getStandings() {
        Standings standings = standingsService.getStandings();
        List<DriveResource<TeamStanding>> resources = resourceGenerator.createDriveResourceList(
                standings.getTeams(), team -> teamViewLink(team.getTeamId()));
        return resourceGenerator.createDriveDataResource(MAIN_LINKS, Map.of("teams", resources), Standings.class);
    }
}
