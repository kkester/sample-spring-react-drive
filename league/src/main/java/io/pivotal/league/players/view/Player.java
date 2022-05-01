package io.pivotal.league.players.view;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kjetland.jackson.jsonSchema.annotations.JsonSchemaBool;
import com.kjetland.jackson.jsonSchema.annotations.JsonSchemaInject;
import com.kjetland.jackson.jsonSchema.annotations.JsonSchemaTitle;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;
import java.util.UUID;

@Getter
@Builder
@ToString
public class Player {
    @JsonIgnore
    private UUID teamId;

    @JsonSchemaTitle("Team Name")
    @JsonSchemaInject(bools = {@JsonSchemaBool(path = "readOnly", value = true)})
    private String teamName;

    private PlayerInfo info;

    @JsonSchemaTitle("Stats")
    private PlayerStats stats;

    @JsonSchemaTitle("Games")
    private List<PlayerGameStats> games;
}
