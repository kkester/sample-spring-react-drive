package io.pivotal.drive.league.games;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class Score {
    private int homePoints;
    private int visitorPoints;
}
