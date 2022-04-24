package io.pivotal.drive.league.players;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class PlayerStats {
    private int gamesPlayed;
    private int points;
    private int rating;
    private int highestRating;
    private int lowestRating;
}
