package io.pivotal.drive.league.standings;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.UUID;

@Getter
@Builder
@ToString
public class TeamStanding {
    @JsonIgnore
    private UUID teamId;
    private String teamName;
    private int total;
    private int wins;
    private int loses;
    private int ties;
    private Integer points;
}
