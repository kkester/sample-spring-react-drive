package io.pivotal.drive.league;

import io.pivotal.drive.mediatype.DriveLink;

import java.util.Map;

public interface LeagueLinkConstants {

    DriveLink HOME_LINK = DriveLink.builder()
            .href("/league")
            .title("Home")
            .build();
    DriveLink TEAM_LINK = DriveLink.builder()
            .href("/teams/%s")
            .title("Team")
            .build();
    DriveLink GAMES_LINK = DriveLink.builder()
            .href("/games")
            .title("Games")
            .build();
    DriveLink PLAYERS_LINK = DriveLink.builder()
            .href("/players")
            .title("Players")
            .build();
    DriveLink STANDINGS_LINK = DriveLink.builder()
            .href("/standings")
            .title("Standings")
            .build();
    DriveLink TEAM_GAMES_LINK = DriveLink.builder()
            .href("/teams/%s/games")
            .title("Games")
            .build();
    DriveLink TEAM_PLAYER_STATS_LINK = DriveLink.builder()
            .href("/teams/%s/players")
            .title("Players")
            .build();
    DriveLink TEAM_ROSTER_LINK = DriveLink.builder()
            .href("/teams/%s/roster")
            .title("Roster")
            .build();

    Map<String, DriveLink> MAIN_LINKS = Map.of(
            "home", LeagueLinkConstants.HOME_LINK,
            "games", LeagueLinkConstants.GAMES_LINK,
            "players", LeagueLinkConstants.PLAYERS_LINK,
            "standings", LeagueLinkConstants.STANDINGS_LINK
    );
}
