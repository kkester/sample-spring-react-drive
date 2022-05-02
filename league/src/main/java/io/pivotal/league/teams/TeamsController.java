package io.pivotal.league.teams;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.pivotal.league.games.view.TeamGameSummary;
import io.pivotal.league.players.view.TeamPlayerStatsSummary;
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
import static io.pivotal.league.games.GameLinkConstants.teamGameViewLink;
import static io.pivotal.league.players.PlayerLinkConstants.playerViewLink;
import static io.pivotal.league.teams.TeamLinkConstants.mainTeamLinks;
import static io.pivotal.league.teams.TeamLinkConstants.teamViewLink;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
public class TeamsController {

    private final TeamService teamService;
    private final DriveResourceGenerator resourceGenerator;

    @GetMapping("/teams/{teamId}")
    public DriveDataResource getTeam(@PathVariable UUID teamId) {
        Team team = teamService.getTeamById(teamId);
        Map<String,Object> data = resourceGenerator.createData(team);
        List<DriveResource<TeamGameSummary>> latestResults = resourceGenerator.createDriveResourceList(
                team.getLatestResults(), game -> teamGameViewLink(teamId, game.getId(), game.getResult()));
        List<DriveResource<TeamPlayerStatsSummary>> topPlayers = resourceGenerator.createDriveResourceList(
                team.getTopPlayers(), player -> playerViewLink(player.getId(), player.getPlayer()));
        data.put("latestResults", latestResults);
        data.put("topPlayers", topPlayers);
        return resourceGenerator.createDriveDataResource(mainTeamLinks(teamId, team.getName()), data, Team.class);
    }

    @GetMapping("/teams")
    public DriveDataResource getTeams() {
        TeamSummaries teamSummaries = teamService.getAllTeams();
        List<DriveResource<TeamSummary>> resources = resourceGenerator.createDriveResourceList(
                teamSummaries.getTeams(), teamSummary -> teamViewLink(teamSummary.getId(), teamSummary.getTeam()));
        return resourceGenerator.createDriveDataResource(MAIN_LINKS, Map.of("teams", resources), TeamSummaries.class);
    }
}
