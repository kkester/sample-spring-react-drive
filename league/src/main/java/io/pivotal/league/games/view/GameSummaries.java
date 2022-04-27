package io.pivotal.league.games.view;

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
