package io.pivotal.drive.league.standings;

import io.pivotal.drive.mediatype.DriveLink;

public class StandingsLinkConstants {
    public static final DriveLink STANDINGS_LINK = DriveLink.builder()
            .href("/standings")
            .title("Standings")
            .build();

    private StandingsLinkConstants() {
    }
}
