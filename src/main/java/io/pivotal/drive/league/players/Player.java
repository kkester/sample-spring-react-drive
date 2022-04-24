package io.pivotal.drive.league.players;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;
import java.util.UUID;

@Getter
@Builder
@ToString
public class Player {
    @JsonIgnore
    private UUID teamId;
    private String teamName;
    private PlayerInfo info;
    private PlayerStats stats;
    private List<PlayerGameStats> games;
}
