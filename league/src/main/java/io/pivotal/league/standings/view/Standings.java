package io.pivotal.league.standings.view;

import com.kjetland.jackson.jsonSchema.annotations.JsonSchemaTitle;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@Builder
@ToString
public class Standings {
    @JsonSchemaTitle("Teams")
    List<TeamStanding> teams;
}
