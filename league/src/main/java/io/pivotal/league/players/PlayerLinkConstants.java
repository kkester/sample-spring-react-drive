package io.pivotal.league.players;

import io.pivotal.drive.mediatype.DriveLink;

import java.util.Map;
import java.util.UUID;

import static io.pivotal.league.LeagueLinkConstants.*;
import static io.pivotal.league.teams.TeamLinkConstants.TEAM_LINK;

public class PlayerLinkConstants {

    public static final DriveLink PLAYERS_LINK = DriveLink.builder()
            .href("/players")
            .title("Players")
            .build();
    public static final DriveLink PLAYER_LINK = DriveLink.builder()
            .href("/players/%s")
            .title("Player")
            .build();
    public static final DriveLink TEAM_PLAYERS_LINK = DriveLink.builder()
            .href("/teams/%s/players")
            .title("Team Players")
            .build();

    static Map<String, DriveLink> playerLinks(UUID id, String teamName) {
        return DriveLink.of(
                "home", HOME_LINK,
                "team", TEAM_LINK.format(teamName, id),
                "players", PLAYERS_LINK
        );
    }

    static Map<String, DriveLink> playerViewLink(UUID id) {
        return DriveLink.of("player", PLAYER_LINK.format("view", id));
    }

    private PlayerLinkConstants() {
    }
}
