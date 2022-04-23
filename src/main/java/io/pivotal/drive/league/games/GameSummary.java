package io.pivotal.drive.league.games;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
@ToString
public class GameSummary {
    @JsonIgnore
    private UUID id;
    private String home;
    private int homePoints;
    private String visitor;
    private int visitorPoints;
    private LocalDateTime gameTime;
}
