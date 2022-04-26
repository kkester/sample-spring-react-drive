package io.pivotal.drive.league.players.view;

import com.kjetland.jackson.jsonSchema.annotations.JsonSchemaTitle;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@Builder
@ToString
public class PlayerStatsSummaries {
    @JsonSchemaTitle("Players")
    private List<PlayerStatsSummary> players;
}
