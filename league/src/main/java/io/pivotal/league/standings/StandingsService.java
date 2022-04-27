package io.pivotal.league.standings;

import io.pivotal.league.model.TeamEntity;
import io.pivotal.league.repositories.TeamRepository;
import io.pivotal.league.standings.view.Standings;
import io.pivotal.league.standings.view.TeamStanding;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StandingsService {

    private final TeamRepository teamRepository;

    public Standings getStandings() {
        List<TeamStanding> teams = teamRepository.findAll().stream()
                .map(this::createTeamStanding)
                .sorted(Comparator.comparing(TeamStanding::getPoints).reversed())
                .collect(Collectors.toList());
        return Standings.builder().teams(teams).build();
    }

    private TeamStanding createTeamStanding(TeamEntity team) {
        return TeamStanding.builder()
                .teamId(team.getId())
                .teamName(team.getName())
                .wins(team.getWins())
                .loses(team.getLosses())
                .ties(team.getTies())
                .points((team.getWins() * 2) + team.getTies())
                .total(team.getWins() + team.getLosses() + team.getTies())
                .build();
    }

    public List<TeamStanding> getTopTeams() {
        List<TeamStanding> teams = getStandings().getTeams();
        return Arrays.asList(teams.get(0),teams.get(1),teams.get(2));
    }
}
