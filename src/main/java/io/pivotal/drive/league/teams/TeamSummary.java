package io.pivotal.drive.league.teams;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.UUID;

@Getter
@Builder
@ToString
public class TeamSummary {
    @JsonIgnore
    private UUID id;
    private String name;
    private int gamesPlayed;
    private int points;
    private int pointsFor;
    private int pointsAgainst;
    private double averagePlayerRating;
}
