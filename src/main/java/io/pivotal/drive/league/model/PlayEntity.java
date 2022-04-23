package io.pivotal.drive.league.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity
public class PlayEntity {
    @Id
    @GeneratedValue
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "game_id", columnDefinition = "BINARY(16)")
    @NotNull
    private GameEntity game;

    @ManyToOne
    @JoinColumn(name = "player_id", columnDefinition = "BINARY(16)")
    @NotNull
    private PlayerEntity player;

    private int playerRating;

    private int chance;

    private boolean scored;
}
