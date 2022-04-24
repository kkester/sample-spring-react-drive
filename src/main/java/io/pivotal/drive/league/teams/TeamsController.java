package io.pivotal.drive.league.teams;

import com.fasterxml.jackson.module.jsonSchema.JsonSchemaGenerator;
import io.pivotal.drive.mediatype.DriveLink;
import io.pivotal.drive.mediatype.DriveResource;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.UUID;

import static io.pivotal.drive.league.LeagueLinkConstants.*;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
public class TeamsController {

    private final TeamService teamService;
    private final JsonSchemaGenerator schemaGenerator;

    @GetMapping("/teams/{teamId}")
    @SneakyThrows
    public DriveResource<Team> getTeam(@PathVariable UUID teamId) {
        Team team = teamService.getTeamById(teamId);
        Map<String, DriveLink> links = Map.of(
                "home", HOME_LINK,
                "standings", STANDINGS_LINK,
                "games", TEAM_GAMES_LINK.format(teamId),
                "roster", TEAM_ROSTER_LINK.format(teamId),
                "stats", TEAM_PLAYER_STATS_LINK.format(teamId)
        );
        return DriveResource.<Team>builder()
                .links(links)
                .data(team)
                .schema(schemaGenerator.generateSchema(Team.class))
                .build();
    }
}
