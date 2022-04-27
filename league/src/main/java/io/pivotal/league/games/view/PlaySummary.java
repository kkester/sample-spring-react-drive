package io.pivotal.league.games.view;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class PlaySummary {
    private String playerName;
    private String description;
}
