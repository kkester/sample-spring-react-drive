package io.pivotal.league.games.view;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class GamesPage {
    List<LatestGames> latestGames;
}
