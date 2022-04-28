package io.pivotal.league;

import io.pivotal.league.model.TeamEntity;

public class PointsCalculater {
    public static int calculatePoints(TeamEntity teamEntity) {
        return (teamEntity.getWins() * 3) + teamEntity.getTies();
    }
}
