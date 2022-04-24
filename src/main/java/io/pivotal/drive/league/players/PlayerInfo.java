package io.pivotal.drive.league.players;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Builder
@ToString
public class PlayerInfo {
    private String name;
    private LocalDate birthDate;
    private String homeTown;
    private String homeState;
}
