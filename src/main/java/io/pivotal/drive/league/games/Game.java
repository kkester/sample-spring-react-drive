package io.pivotal.drive.league.games;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@Builder
@ToString
public class Game {
    private Teams teams;
    private Score score;
    private List<PlaySummary> plays;
}
