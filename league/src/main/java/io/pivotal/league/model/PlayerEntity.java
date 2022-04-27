package io.pivotal.league.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class PlayerEntity {
    @Id
    @GeneratedValue
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    @NotNull
    private String name;

    @NotNull
    private LocalDate birthDate;

    @NotNull
    private String homeTown;

    @NotNull
    private String homeState;

    @NotNull
    private int points;

    @NotNull
    private int rating;

    @NotNull
    private int highestRating;

    @NotNull
    private int lowestRating;

    @NotNull
    @Builder.Default
    private boolean scored = true;

    @ManyToOne
    @JoinColumn(name = "team_id", columnDefinition = "BINARY(16)")
    @NotNull
    private TeamEntity team;
}
