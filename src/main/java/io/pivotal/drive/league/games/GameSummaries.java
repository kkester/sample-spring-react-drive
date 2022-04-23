package io.pivotal.drive.league.games;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@Builder
@ToString
public class GameSummaries {
    private List<GameSummary> games;
}
