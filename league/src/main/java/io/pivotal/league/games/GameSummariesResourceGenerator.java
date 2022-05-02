package io.pivotal.league.games;

import io.pivotal.drive.mediatype.DriveDataResource;
import io.pivotal.drive.mediatype.DriveResource;
import io.pivotal.drive.mediatype.DriveResourceGenerator;
import io.pivotal.league.games.view.GameSummaries;
import io.pivotal.league.games.view.GameSummary;
import io.pivotal.league.games.view.TeamGameSummaries;
import io.pivotal.league.games.view.TeamGameSummary;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static io.pivotal.league.LeagueLinkConstants.MAIN_LINKS;
import static io.pivotal.league.games.GameLinkConstants.gameViewLinks;
import static io.pivotal.league.games.GameLinkConstants.teamGameViewLink;
import static io.pivotal.league.teams.TeamLinkConstants.mainTeamLinks;

@Component
@RequiredArgsConstructor
public class GameSummariesResourceGenerator {

    private static final String GAMES_KEY = "games";

    private final DriveResourceGenerator driveResourceGenerator;

    public DriveDataResource generateTeamGameSummariesResource(UUID teamId, TeamGameSummaries gameSummaries) {
        List<DriveResource<TeamGameSummary>> resources = driveResourceGenerator.createDriveResourceList(
                gameSummaries.getGames(), game -> teamGameViewLink(teamId, game.getId(), game.getResult()));
        Map<String, Object> data = Map.of(GAMES_KEY, resources);
        return driveResourceGenerator.createDriveDataResource(mainTeamLinks(teamId, gameSummaries.getTeamName()), data, TeamGameSummaries.class);
    }

    public DriveDataResource generateDriveResource(GameSummaries gameSummaries) {
        List<DriveResource<GameSummary>> resources = driveResourceGenerator.createDriveResourceList(
                gameSummaries.getGames(), game -> gameViewLinks(game.getId(),
                        game.getHomeTeamId(),
                        game.getHome(),
                        game.getVisitorTeamId(),
                        game.getVisitor()));
        return driveResourceGenerator.createDriveDataResource(MAIN_LINKS, Map.of(GAMES_KEY, resources), GameSummaries.class);
    }
}
