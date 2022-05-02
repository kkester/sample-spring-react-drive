package io.pivotal.league.teams;

import io.pivotal.drive.mediatype.DriveLink;

import java.util.Map;
import java.util.UUID;

import static io.pivotal.league.LeagueLinkConstants.*;
import static io.pivotal.league.games.GameLinkConstants.TEAM_GAMES_LINK;
import static io.pivotal.league.players.PlayerLinkConstants.TEAM_PLAYERS_LINK;
import static io.pivotal.league.standings.StandingsLinkConstants.STANDINGS_LINK;

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

    public static Map<String, DriveLink> teamViewLink(UUID id, String team) {
        return DriveLink.of(
                "team", TEAM_LINK.applyTitleAndVariables(team, id)
        );
    }

    public static Map<String, DriveLink> mainTeamLinks(UUID teamId, String teamName) {
        return DriveLink.of(
                "home", HOME_LINK,
                "team", TEAM_LINK.applyTitleAndVariables(teamName, teamId),
                "games", TEAM_GAMES_LINK.applyVariables(teamId),
                "roster", TEAM_ROSTER_LINK.applyVariables(teamId),
                "stats", TEAM_PLAYERS_LINK.applyVariables(teamId)
        );
    }

    private TeamLinkConstants() {
    }
}
