package io.pivotal.drive.league.players;

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
