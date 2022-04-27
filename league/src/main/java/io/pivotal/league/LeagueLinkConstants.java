package io.pivotal.league;

import io.pivotal.drive.mediatype.DriveLink;

import java.util.Map;

import static io.pivotal.league.games.GameLinkConstants.GAMES_LINK;
import static io.pivotal.league.players.PlayerLinkConstants.PLAYERS_LINK;
import static io.pivotal.league.standings.StandingsLinkConstants.STANDINGS_LINK;
import static io.pivotal.league.teams.TeamLinkConstants.TEAMS_LINK;

public class LeagueLinkConstants {

    public static final DriveLink HOME_LINK = DriveLink.builder()
            .href("/league")
            .title("Home")
            .build();

    public static final Map<String, DriveLink> MAIN_LINKS = DriveLink.of(
            "home", HOME_LINK,
            "teams", TEAMS_LINK,
            "games", GAMES_LINK,
            "players", PLAYERS_LINK,
            "standings", STANDINGS_LINK
    );

    private LeagueLinkConstants() {
    }
}
