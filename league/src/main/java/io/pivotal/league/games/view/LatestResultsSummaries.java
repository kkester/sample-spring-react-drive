package io.pivotal.league.games.view;

import com.kjetland.jackson.jsonSchema.annotations.JsonSchemaTitle;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class LatestResultsSummaries {
    @JsonSchemaTitle("Latest Results")
    private List<GameSummary> games;
}
