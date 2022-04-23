package io.pivotal.drive.league.games;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class Teams {
    private String home;
    private String visitor;
}
