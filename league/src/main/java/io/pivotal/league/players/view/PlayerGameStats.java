package io.pivotal.league.players.view;

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
