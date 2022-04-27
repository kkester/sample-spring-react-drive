package io.pivotal.league.teams.view;

import com.kjetland.jackson.jsonSchema.annotations.JsonSchemaTitle;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@Builder
@ToString
public class TeamSummaries {
    @JsonSchemaTitle("Teams")
    private List<TeamSummary> teams;
}
