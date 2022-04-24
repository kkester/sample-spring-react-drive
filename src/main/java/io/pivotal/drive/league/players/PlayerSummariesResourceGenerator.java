package io.pivotal.drive.league.players;

import com.fasterxml.jackson.module.jsonSchema.JsonSchemaGenerator;
import io.pivotal.drive.mediatype.DriveLink;
import io.pivotal.drive.mediatype.DriveResource;
import io.pivotal.drive.mediatype.DriveResourceList;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import static io.pivotal.drive.league.LeagueLinkConstants.*;

@Component
@RequiredArgsConstructor
public class PlayerSummariesResourceGenerator {

    public static final String PLAYERS_KEY = "players";
    private final JsonSchemaGenerator schemaGenerator;

    @SneakyThrows
    public DriveResourceList<PlayerStatsSummary> generateAllPlayersResource(PlayerStatsSummaries playerStatsSummaries) {
        List<DriveResource<PlayerStatsSummary>> playerSummaries = createItems(playerStatsSummaries);
        return DriveResourceList.<PlayerStatsSummary>builder()
                .links(MAIN_LINKS)
                .data(Map.of(PLAYERS_KEY, playerSummaries))
                .schema(schemaGenerator.generateSchema(PlayerStatsSummaries.class))
                .build();
    }

    @SneakyThrows
    public DriveResourceList<PlayerStatsSummary> generateTeamPlayersResource(UUID teamId, PlayerStatsSummaries playerStatsSummaries) {
        List<DriveResource<PlayerStatsSummary>> playerSummaries = createItems(playerStatsSummaries);
        Map<String, DriveLink> links = createTeamLinks(teamId);
        return DriveResourceList.<PlayerStatsSummary>builder()
                .links(links)
                .data(Map.of(PLAYERS_KEY, playerSummaries))
                .schema(schemaGenerator.generateSchema(PlayerStatsSummaries.class))
                .build();
    }

    private Map<String, DriveLink> createTeamLinks(UUID teamId) {
        return Map.of(
                "home", HOME_LINK,
                "team", TEAM_LINK.format(teamId)
        );
    }

    private List<DriveResource<PlayerStatsSummary>> createItems(PlayerStatsSummaries playerStatsSummaries) {
        return playerStatsSummaries.getPlayers().stream()
                .map(this::convert)
                .collect(Collectors.toList());
    }

    private DriveResource<PlayerStatsSummary> convert(PlayerStatsSummary playerSummary) {
        Map<String, DriveLink> links = Map.of(
                "player", DriveLink.builder()
                        .href("/players/" + playerSummary.getId())
                        .title("View")
                        .build()
        );
        return DriveResource.<PlayerStatsSummary>builder()
                .links(links)
                .data(playerSummary)
                .build();
    }

    @SneakyThrows
    public DriveResourceList<PlayerSummary> generateTeamRosterResource(UUID teamId, PlayerSummaries playerSummaries) {
        List<DriveResource<PlayerSummary>> playerSummariesList = playerSummaries.getPlayers().stream()
                .map(this::convert)
                .collect(Collectors.toList());

        Map<String, DriveLink> links = createTeamLinks(teamId);

        return DriveResourceList.<PlayerSummary>builder()
                .links(links)
                .data(Map.of(PLAYERS_KEY, playerSummariesList))
                .schema(schemaGenerator.generateSchema(PlayerSummaries.class))
                .build();
    }

    private DriveResource<PlayerSummary> convert(PlayerSummary playerSummary) {
        Map<String, DriveLink> links = Map.of(
                "player", DriveLink.builder()
                        .href("/players/" + playerSummary.getId())
                        .title("View")
                        .build()
        );
        return DriveResource.<PlayerSummary>builder()
                .links(links)
                .data(playerSummary)
                .build();
    }
}
