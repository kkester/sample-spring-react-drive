package io.pivotal.drive.league.standings;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class TeamStanding {
    private String teamName;
    private int total;
    private int wins;
    private int loses;
    private int ties;
    private Integer points;
}
