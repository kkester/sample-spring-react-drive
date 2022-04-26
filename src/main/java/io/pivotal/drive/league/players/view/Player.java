package io.pivotal.drive.league.players.view;

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
@JsonSchemaInject(bools = {@JsonSchemaBool(path = "readOnly", value = true)})
public class Player {
    @JsonIgnore
    private UUID teamId;
    @JsonSchemaTitle("Team Name")
    private String teamName;
    private PlayerInfo info;
    private PlayerStats stats;
    @JsonSchemaTitle("Games")
    private List<PlayerGameStats> games;
}
