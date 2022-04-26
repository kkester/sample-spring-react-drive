package io.pivotal.drive.league.games;

import io.pivotal.drive.league.games.view.GameSummaries;
import io.pivotal.drive.league.games.view.GameSummary;
import io.pivotal.drive.league.games.view.TeamGameSummaries;
import io.pivotal.drive.league.games.view.TeamGameSummary;
import io.pivotal.drive.mediatype.DriveDataResource;
import io.pivotal.drive.mediatype.DriveResource;
import io.pivotal.drive.mediatype.DriveResourceGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static io.pivotal.drive.league.LeagueLinkConstants.MAIN_LINKS;
import static io.pivotal.drive.league.games.GameLinkConstants.gameViewLink;
import static io.pivotal.drive.league.teams.TeamLinkConstants.teamLinks;

@Component
@RequiredArgsConstructor
public class GameSummariesResourceGenerator {

    private static final String GAMES_KEY = "games";

    private final DriveResourceGenerator driveResourceGenerator;

    public DriveDataResource generateTeamGameSummariesResource(UUID teamId, TeamGameSummaries gameSummaries) {
        List<DriveResource<TeamGameSummary>> resources = driveResourceGenerator.createDriveResourceList(
                gameSummaries.getGames(), game -> gameViewLink(game.getId()));
        Map<String, Object> data = Map.of(
                "teamName", gameSummaries.getTeamName(),
                GAMES_KEY, resources
        );
        return driveResourceGenerator.createDriveDataResource(teamLinks(teamId), data, TeamGameSummaries.class);
    }

    public DriveDataResource generateDriveResource(GameSummaries gameSummaries) {
        List<DriveResource<GameSummary>> resources = driveResourceGenerator.createDriveResourceList(
                gameSummaries.getGames(), game -> gameViewLink(game.getId()));
        return driveResourceGenerator.createDriveDataResource(MAIN_LINKS, Map.of(GAMES_KEY, resources), GameSummaries.class);
    }
}
