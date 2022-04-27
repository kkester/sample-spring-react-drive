package io.pivotal.league.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class TeamEntity {
    @Id
    @GeneratedValue
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    @NotNull
    private String name;

    @NotNull
    private String city;

    @NotNull
    private int totalGames;

    @NotNull
    private int wins;

    @NotNull
    private int losses;

    @NotNull
    private int ties;
}
