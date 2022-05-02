package io.pivotal.league.games;

import io.pivotal.drive.mediatype.DriveLink;

import java.util.Map;
import java.util.UUID;

import static io.pivotal.league.LeagueLinkConstants.HOME_LINK;
import static io.pivotal.league.teams.TeamLinkConstants.TEAM_LINK;

public class GameLinkConstants {
    public static final DriveLink GAMES_LINK = DriveLink.builder()
            .href("/games")
            .title("Games")
            .build();

    public static final DriveLink GAME_LINK = DriveLink.builder()
            .href("/games/%s")
            .title("Game")
            .build();

    public static final DriveLink TEAM_GAME_LINK = DriveLink.builder()
            .href("/teams/%s/games/%s")
            .title("Game")
            .build();

    public static final DriveLink TEAM_GAMES_LINK = DriveLink.builder()
            .href("/teams/%s/games")
            .title("Games")
            .build();

    public static Map<String, DriveLink> gameViewLinks(UUID gameId, UUID homeTeamId, String homeTeam, UUID visitorTeamId, String visitorTeam) {
        return DriveLink.of(
                "game", GAME_LINK.applyTitleAndVariables("view", gameId),
                "home", TEAM_LINK.applyTitleAndVariables(homeTeam, homeTeamId),
                "visitor", GAME_LINK.applyTitleAndVariables(visitorTeam, visitorTeamId)
        );
    }

    public static Map<String, DriveLink> teamGameViewLink(UUID teamId, UUID gameId, String result) {
        return DriveLink.of(
                "result", TEAM_GAME_LINK.applyTitleAndVariables(result, teamId, gameId)
        );
    }

    public static Map<String, DriveLink> gameLinks() {
        return DriveLink.of("home", HOME_LINK, "games", GAMES_LINK);
    }

    static Map<String, DriveLink> teamGameLinks(UUID teamId, String teamName) {
        return DriveLink.of(
                "home", HOME_LINK,
                "team", TEAM_LINK.applyTitleAndVariables(teamName, teamId),
                "games", TEAM_GAMES_LINK.applyVariables(teamId)
        );
    }

    private GameLinkConstants() {
    }
}
