package io.pivotal.drive.league.teams;

import io.pivotal.drive.league.model.TeamEntity;
import io.pivotal.drive.league.repositories.GameRepository;
import io.pivotal.drive.league.repositories.PlayerRepository;
import io.pivotal.drive.league.repositories.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
public class TeamsController {
    private final TeamRepository teamRepository;
    private final PlayerRepository playerRepository;
    private final GameRepository gameRepository;

    @GetMapping("/teams")
    public List<TeamEntity> getTeams() {
        return teamRepository.findAll();
    }
}
