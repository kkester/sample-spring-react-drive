package io.pivotal.league.players.view;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kjetland.jackson.jsonSchema.annotations.JsonSchemaTitle;
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
    @JsonSchemaTitle("Player Name")
    private String player;
    @JsonSchemaTitle("Birth Date")
    private LocalDate birthDate;
    @JsonSchemaTitle("Home Town")
    private String homeTown;
    @JsonSchemaTitle("Home State")
    private String homeState;
}
