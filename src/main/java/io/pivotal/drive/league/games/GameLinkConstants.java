package io.pivotal.drive.league.games;

import io.pivotal.drive.mediatype.DriveLink;

import java.util.Map;
import java.util.UUID;

import static io.pivotal.drive.league.LeagueLinkConstants.HOME_LINK;

public class GameLinkConstants {
    public static final DriveLink GAMES_LINK = DriveLink.builder()
            .href("/games")
            .title("Games")
            .build();

    public static final DriveLink GAME_LINK = DriveLink.builder()
            .href("/games/%s")
            .title("Game")
            .build();

    public static final DriveLink TEAM_GAMES_LINK = DriveLink.builder()
            .href("/teams/%s/games")
            .title("Team Games")
            .build();

    static Map<String, DriveLink> gameViewLink(UUID id) {
        return DriveLink.of(
                "player", GAME_LINK.format(id, "view")
        );
    }

    static Map<String, DriveLink> gameLinks(UUID id) {
        return DriveLink.of("home", HOME_LINK, "games", GAMES_LINK);
    }

    private GameLinkConstants() {
    }
}
