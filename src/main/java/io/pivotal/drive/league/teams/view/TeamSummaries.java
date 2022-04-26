package io.pivotal.drive.league.teams.view;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@Builder
@ToString
public class TeamSummaries {
    private List<TeamSummary> teams;
}