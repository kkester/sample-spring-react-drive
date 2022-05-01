package io.pivotal.league.players.view;

import com.kjetland.jackson.jsonSchema.annotations.JsonSchemaBool;
import com.kjetland.jackson.jsonSchema.annotations.JsonSchemaInject;
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
    @JsonSchemaInject(bools = {@JsonSchemaBool(path = "readOnly", value = true)})
    private String name;

    @JsonSchemaTitle("Birth Date")
    @JsonSchemaInject(bools = {@JsonSchemaBool(path = "readOnly", value = true)})
    private LocalDate birthDate;

    @JsonSchemaTitle("Home Town")
    @JsonSchemaInject(bools = {@JsonSchemaBool(path = "readOnly", value = true)})
    private String homeTown;

    @JsonSchemaTitle("Home State")
    @JsonSchemaInject(bools = {@JsonSchemaBool(path = "readOnly", value = true)})
    private String homeState;
}
