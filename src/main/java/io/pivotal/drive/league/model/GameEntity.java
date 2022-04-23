package io.pivotal.drive.league.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity
public class GameEntity {
    @Id
    @GeneratedValue
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    @OneToOne
    @JoinColumn(name = "home_team_id", columnDefinition = "BINARY(16)")
    @NotNull
    private TeamEntity homeTeam;

    @OneToOne
    @JoinColumn(name = "visiting_team_id", columnDefinition = "BINARY(16)")
    @NotNull
    private TeamEntity visitingTeam;

    @NotNull
    private int homeTeamPoints;

    @NotNull
    private int visitingTeamPoints;

    @NotNull
    private LocalDateTime gameTime;
}
