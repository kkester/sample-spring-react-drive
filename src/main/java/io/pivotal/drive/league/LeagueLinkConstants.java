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
            .title("Teams")
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
    DriveLink TEAM_PLAYERS_LINK = DriveLink.builder()
            .href("/teams/%s/players")
            .title("Players")
            .build();

    Map<String, DriveLink> MAIN_LINKS = Map.of(
            "home", LeagueLinkConstants.HOME_LINK,
            "games", LeagueLinkConstants.GAMES_LINK,
            "players", LeagueLinkConstants.PLAYERS_LINK,
            "standings", LeagueLinkConstants.STANDINGS_LINK
    );
}
