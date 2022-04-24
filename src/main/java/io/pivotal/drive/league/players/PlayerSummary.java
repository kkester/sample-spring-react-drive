package io.pivotal.drive.league.players;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Builder
@ToString
public class PlayerSummary {
    @JsonIgnore
    private UUID id;
    private String name;
    private LocalDate birthDate;
    private String homeTown;
    private String homeState;
}
