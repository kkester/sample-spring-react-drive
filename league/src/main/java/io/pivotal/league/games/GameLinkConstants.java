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
            .title("Team Games")
            .build();

    static Map<String, DriveLink> gameViewLink(UUID gameId) {
        return DriveLink.of(
                "game", GAME_LINK.format("view", gameId)
        );
    }

    static Map<String, DriveLink> teamGameViewLink(UUID teamId, UUID gameId) {
        return DriveLink.of(
                "game", TEAM_GAME_LINK.format("view", teamId, gameId)
        );
    }

    static Map<String, DriveLink> gameLinks() {
        return DriveLink.of("home", HOME_LINK, "games", GAMES_LINK);
    }

    static Map<String, DriveLink> teamGameLinks(UUID teamId) {
        return DriveLink.of(
                "home", HOME_LINK,
                "team", TEAM_LINK.format(teamId),
                "games", TEAM_GAMES_LINK.format(teamId)
        );
    }

    private GameLinkConstants() {
    }
}
