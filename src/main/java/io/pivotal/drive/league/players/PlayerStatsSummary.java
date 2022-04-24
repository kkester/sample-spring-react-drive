package io.pivotal.drive.league.players;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.UUID;

@Getter
@Builder
@ToString
public class PlayerStatsSummary {
    @JsonIgnore
    private UUID id;
    private String name;
    private String teamName;
    private Integer points;
    private int rating;
    private int highestRating;
    private int lowestRating;
}
