package io.pivotal.drive.league.teams;

import io.pivotal.drive.mediatype.DriveLink;

import java.util.Map;
import java.util.UUID;

import static io.pivotal.drive.league.LeagueLinkConstants.*;
import static io.pivotal.drive.league.games.GameLinkConstants.TEAM_GAMES_LINK;
import static io.pivotal.drive.league.players.PlayerLinkConstants.TEAM_PLAYERS_LINK;
import static io.pivotal.drive.league.standings.StandingsLinkConstants.STANDINGS_LINK;

public class TeamLinkConstants {
    public static final DriveLink TEAMS_LINK = DriveLink.builder()
            .href("/teams")
            .title("Teams")
            .build();

    public static final DriveLink TEAM_LINK = DriveLink.builder()
            .href("/teams/%s")
            .title("Team")
            .build();

    public static final DriveLink TEAM_ROSTER_LINK = DriveLink.builder()
            .href("/teams/%s/roster")
            .title("Roster")
            .build();

    public static Map<String, DriveLink> teamViewLink(UUID id) {
        return DriveLink.of(
                "team", TEAM_LINK.format(id, "view")
        );
    }

    public static Map<String, DriveLink> teamLinks(UUID teamId) {
        return DriveLink.of(
                "home", HOME_LINK,
                "team", TEAM_LINK.format(teamId)
        );
    }

    public static Map<String, DriveLink> mainTeamLinks(UUID teamId) {
        return DriveLink.of(
                "home", HOME_LINK,
                "standings", STANDINGS_LINK,
                "games", TEAM_GAMES_LINK.format(teamId),
                "roster", TEAM_ROSTER_LINK.format(teamId),
                "stats", TEAM_PLAYERS_LINK.format(teamId)
        );
    }

    private TeamLinkConstants() {
    }
}
