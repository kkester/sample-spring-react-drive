package io.pivotal.drive.league.players;

import com.fasterxml.jackson.module.jsonSchema.JsonSchemaGenerator;
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

import static io.pivotal.drive.league.LeagueLinkConstants.*;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
public class PlayersController {

    private final PlayersService playersService;
    private final PlayerSummariesResourceGenerator resourceGenerator;
    private final JsonSchemaGenerator schemaGenerator;

    @GetMapping("/teams/{teamId}/players")
    public DriveResourceList<PlayerStatsSummary> getTeamPlayers(@PathVariable UUID teamId) {
        PlayerStatsSummaries playerStatsSummaries = playersService.getPlayersForTeam(teamId);
        return resourceGenerator.generateTeamPlayersResource(teamId, playerStatsSummaries);
    }

    @GetMapping("/teams/{teamId}/roster")
    public DriveResourceList<PlayerSummary> getTeamRoster(@PathVariable UUID teamId) {
        PlayerSummaries playerSummaries = playersService.getRosterForTeam(teamId);
        return resourceGenerator.generateTeamRosterResource(teamId, playerSummaries);
    }

    @GetMapping("/players")
    public DriveResourceList<PlayerStatsSummary> getPlayers() {
        PlayerStatsSummaries playerStatsSummaries = playersService.getAllPlayers();
        return resourceGenerator.generateAllPlayersResource(playerStatsSummaries);
    }

    @GetMapping("/players/{playerId}")
    @SneakyThrows
    public DriveResource<Player> getPlayerById(@PathVariable UUID playerId) {
        Player player = playersService.getPlayerById(playerId);
        return DriveResource.<Player>builder()
                .links(Map.of(
                        "home", HOME_LINK,
                        "team", TEAM_LINK.format(player.getTeamId()),
                        "players", PLAYERS_LINK
                ))
                .data(player)
                .schema(schemaGenerator.generateSchema(Player.class))
                .build();
    }

}
