package io.pivotal.drive.league.players;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@Builder
@ToString
public class PlayerStatsSummaries {
    private List<PlayerStatsSummary> players;
}
