package io.pivotal.league.teams;

import io.pivotal.league.teams.view.Team;
import io.pivotal.league.teams.view.TeamSummaries;
import io.pivotal.league.teams.view.TeamSummary;
import io.pivotal.drive.mediatype.DriveDataResource;
import io.pivotal.drive.mediatype.DriveResource;
import io.pivotal.drive.mediatype.DriveResourceGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static io.pivotal.league.LeagueLinkConstants.MAIN_LINKS;
import static io.pivotal.league.teams.TeamLinkConstants.mainTeamLinks;
import static io.pivotal.league.teams.TeamLinkConstants.teamViewLink;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
public class TeamsController {

    private final TeamService teamService;
    private final DriveResourceGenerator resourceGenerator;

    @GetMapping("/teams/{teamId}")
    public DriveResource<Team> getTeam(@PathVariable UUID teamId) {
        Team team = teamService.getTeamById(teamId);
        return resourceGenerator.createDriveResource(mainTeamLinks(teamId), team);
    }

    @GetMapping("/teams")
    public DriveDataResource getTeams() {
        TeamSummaries teamSummaries = teamService.getAllTeams();
        List<DriveResource<TeamSummary>> resources = resourceGenerator.createDriveResourceList(
                teamSummaries.getTeams(), team -> teamViewLink(team.getId()));
        return resourceGenerator.createDriveDataResource(MAIN_LINKS, Map.of("teams", resources), TeamSummaries.class);
    }
}
