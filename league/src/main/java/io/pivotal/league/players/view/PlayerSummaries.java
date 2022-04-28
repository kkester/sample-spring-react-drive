package io.pivotal.league.players.view;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@Builder
@ToString
public class PlayerSummaries {
    @JsonIgnore
    private String teamName;
    private List<PlayerSummary> players;
}
