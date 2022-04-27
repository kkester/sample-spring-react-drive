package io.pivotal.league.players;

import io.pivotal.league.players.view.*;
import io.pivotal.drive.mediatype.DriveDataResource;
import io.pivotal.drive.mediatype.DriveResource;
import io.pivotal.drive.mediatype.DriveResourceGenerator;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
public class PlayersController {

    private final PlayersService playersService;
    private final PlayerSummariesResourceGenerator playerSummariesResourceGenerator;
    private final DriveResourceGenerator resourceGenerator;

    @GetMapping("/teams/{teamId}/players")
    public DriveDataResource getTeamPlayers(@PathVariable UUID teamId) {
        TeamPlayerStatsSummaries playerStatsSummaries = playersService.getPlayersForTeam(teamId);
        return playerSummariesResourceGenerator.generateTeamPlayersResource(teamId, playerStatsSummaries);
    }

    @GetMapping("/teams/{teamId}/roster")
    public DriveDataResource getTeamRoster(@PathVariable UUID teamId) {
        PlayerSummaries playerSummaries = playersService.getRosterForTeam(teamId);
        return playerSummariesResourceGenerator.generateTeamRosterResource(teamId, playerSummaries);
    }

    @GetMapping("/players")
    public DriveDataResource getPlayers() {
        PlayerStatsSummaries playerStatsSummaries = playersService.getAllPlayers();
        return playerSummariesResourceGenerator.generateAllPlayersResource(playerStatsSummaries);
    }

    @GetMapping("/players/{playerId}")
    @SneakyThrows
    public DriveResource<Player> getPlayerById(@PathVariable UUID playerId) {
        Player player = playersService.getPlayerById(playerId);
        return resourceGenerator.createDriveResource(PlayerLinkConstants.playerLinks(player.getTeamId()), player);
    }

}
