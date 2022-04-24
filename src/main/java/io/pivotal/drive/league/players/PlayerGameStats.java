package io.pivotal.drive.league.players;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Builder
@ToString
public class PlayerGameStats {
    private String opponent;
    private int points;
    private LocalDateTime gameTime;
}
