package io.pivotal.drive.league.standings;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@Builder
@ToString
public class Standings {
    List<TeamStanding> teams;
}
