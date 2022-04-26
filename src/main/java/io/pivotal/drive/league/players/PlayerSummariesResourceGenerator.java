package io.pivotal.drive.league.players;

import io.pivotal.drive.league.players.view.*;
import io.pivotal.drive.mediatype.DriveDataResource;
import io.pivotal.drive.mediatype.DriveLink;
import io.pivotal.drive.mediatype.DriveResource;
import io.pivotal.drive.mediatype.DriveResourceGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static io.pivotal.drive.league.LeagueLinkConstants.MAIN_LINKS;
import static io.pivotal.drive.league.players.PlayerLinkConstants.playerViewLink;
import static io.pivotal.drive.league.teams.TeamLinkConstants.teamLinks;

@Component
@RequiredArgsConstructor
public class PlayerSummariesResourceGenerator {

    public static final String PLAYERS_KEY = "players";

    private final DriveResourceGenerator driveResourceGenerator;

    public DriveDataResource generateAllPlayersResource(PlayerStatsSummaries playerStatsSummaries) {
        List<DriveResource<PlayerStatsSummary>> resources = driveResourceGenerator.createDriveResourceList(
                playerStatsSummaries.getPlayers(), player -> playerViewLink(player.getId()));
        return driveResourceGenerator.createDriveDataResource(MAIN_LINKS, Map.of(PLAYERS_KEY, resources), PlayerStatsSummaries.class);
    }

    public DriveDataResource generateTeamPlayersResource(UUID teamId, TeamPlayerStatsSummaries playerStatsSummaries) {
        Map<String, DriveLink> links = teamLinks(teamId);
        List<DriveResource<TeamPlayerStatsSummary>> resources = driveResourceGenerator.createDriveResourceList(
                playerStatsSummaries.getPlayers(), player -> playerViewLink(player.getId()));
        return driveResourceGenerator.createDriveDataResource(links, Map.of(PLAYERS_KEY, resources), TeamPlayerStatsSummaries.class);
    }

    public DriveDataResource generateTeamRosterResource(UUID teamId, PlayerSummaries playerSummaries) {
        Map<String, DriveLink> links = teamLinks(teamId);
        List<DriveResource<PlayerSummary>> resources = driveResourceGenerator.createDriveResourceList(
                playerSummaries.getPlayers(), player -> playerViewLink(player.getId()));
        return driveResourceGenerator.createDriveDataResource(links, Map.of(PLAYERS_KEY, resources), PlayerSummaries.class);
    }
}
