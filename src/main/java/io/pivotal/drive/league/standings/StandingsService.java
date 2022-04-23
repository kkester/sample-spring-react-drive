package io.pivotal.drive.league.standings;

import io.pivotal.drive.league.repositories.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StandingsService {

    private final TeamRepository teamRepository;

    public Standings getStandings() {
        List<TeamStanding> teams = teamRepository.findAll().stream()
                .map(team -> TeamStanding.builder()
                        .teamName(team.getName())
                        .wins(team.getWins())
                        .loses(team.getLosses())
                        .ties(team.getTies())
                        .points((team.getWins() * 2) + team.getTies())
                        .total(team.getWins() + team.getLosses() + team.getTies())
                        .build())
                .sorted(Comparator.comparing(TeamStanding::getPoints).reversed())
                .collect(Collectors.toList());
        return Standings.builder().teams(teams).build();
    }
}
