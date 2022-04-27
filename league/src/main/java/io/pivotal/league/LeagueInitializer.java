package io.pivotal.league;

import com.github.javafaker.Faker;
import io.pivotal.league.model.PlayerEntity;
import io.pivotal.league.model.TeamEntity;
import io.pivotal.league.repositories.GameRepository;
import io.pivotal.league.repositories.PlayRepository;
import io.pivotal.league.repositories.PlayerRepository;
import io.pivotal.league.repositories.TeamRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.IntStream;

@Component
@RequiredArgsConstructor
@Slf4j
public class LeagueInitializer {

    private final TeamRepository teamRepository;
    private final PlayerRepository playerRepository;
    private final GameRepository gameRepository;
    private final PlayRepository playRepository;

    @EventListener
    public void onApplicationEvent(ContextRefreshedEvent event) {
        log.info("Initializing League");
        playRepository.deleteAll();
        playerRepository.deleteAll();
        gameRepository.deleteAll();
        teamRepository.deleteAll();
        List<TeamEntity> teams = initializeTeams();
        teams.forEach(this::initializePlayers);
        log.info("League Initialized");
    }

    private List<TeamEntity> initializeTeams() {
        Faker faker = new Faker();
        List<TeamEntity> teams = new ArrayList<>();
        IntStream.range(0, 10).forEach(i -> {
            TeamEntity team = TeamEntity.builder()
                    .name(faker.team().name())
                    .city(faker.address().city())
                    .build();
            teamRepository.save(team);
            teams.add(team);
        });
        return teams;
    }

    private void initializePlayers(TeamEntity team) {
        Faker faker = new Faker();
        IntStream.range(0, 10).forEach(i -> {
            Date birthday = faker.date().birthday(17, 47);
            Integer rating = faker.random().nextInt(3, 7);
            PlayerEntity player = PlayerEntity.builder()
                    .team(team)
                    .name(faker.funnyName().name())
                    .birthDate(birthday.toInstant().atZone(ZoneId.systemDefault()).toLocalDate())
                    .homeTown(faker.address().city())
                    .homeState(faker.address().state())
                    .rating(rating)
                    .highestRating(rating)
                    .lowestRating(rating)
                    .build();
            playerRepository.save(player);
        });
    }
}
