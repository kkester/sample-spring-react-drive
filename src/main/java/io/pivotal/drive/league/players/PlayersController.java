package io.pivotal.drive.league.players;

import com.fasterxml.jackson.module.jsonSchema.JsonSchemaGenerator;
import io.pivotal.drive.league.games.GameSummaries;
import io.pivotal.drive.league.model.PlayerEntity;
import io.pivotal.drive.league.model.TeamEntity;
import io.pivotal.drive.league.repositories.GameRepository;
import io.pivotal.drive.league.repositories.PlayerRepository;
import io.pivotal.drive.league.repositories.TeamRepository;
import io.pivotal.drive.mediatype.DriveResource;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

import static io.pivotal.drive.league.LeagueLinkConstants.MAIN_LINKS;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
public class PlayersController {

    private final PlayersService playersService;
    private final JsonSchemaGenerator schemaGenerator;

    @GetMapping("/teams/{teamId}/players")
    public List<PlayerEntity> getTeamPlayers(@PathVariable UUID teamId) {
        return playersService.getPlayersForTeam(teamId);
    }

    @GetMapping("/players")
    @SneakyThrows
    public DriveResource<PlayerSummaries> getPlayers() {
        PlayerSummaries gamesSummaries = playersService.getLatestGames();
        return DriveResource.<PlayerSummaries>builder()
                .links(MAIN_LINKS)
                .data(gamesSummaries)
                .schema(schemaGenerator.generateSchema(PlayerSummaries.class))
                .build();
    }

}
