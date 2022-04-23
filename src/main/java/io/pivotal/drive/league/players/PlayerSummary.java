package io.pivotal.drive.league.players;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class PlayerSummary {
    private String name;
    private String teamName;
    private Integer points;
    private int rating;
    private int highestRating;
    private int lowestRating;
}
