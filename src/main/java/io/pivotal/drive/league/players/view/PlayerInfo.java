package io.pivotal.drive.league.players.view;

import com.kjetland.jackson.jsonSchema.annotations.JsonSchemaTitle;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Builder
@ToString
public class PlayerInfo {
    @JsonSchemaTitle("Name")
    private String name;
    @JsonSchemaTitle("Birth Date")
    private LocalDate birthDate;
    @JsonSchemaTitle("Home Town")
    private String homeTown;
    @JsonSchemaTitle("Home State")
    private String homeState;
}
