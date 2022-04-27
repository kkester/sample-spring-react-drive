package io.pivotal.league.players.view;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@Builder
@ToString
public class PlayerSummaries {
    private List<PlayerSummary> players;
}
