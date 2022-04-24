package io.pivotal.drive.league.games;

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
public class GameSummariesResourceGenerator {

    private final JsonSchemaGenerator schemaGenerator;

    @SneakyThrows
    public DriveResourceList<GameSummary> generateDriveResource(GameSummaries gameSummaries) {
        return generateDriveResource(MAIN_LINKS, gameSummaries);
    }

    @SneakyThrows
    public DriveResourceList<GameSummary> generateDriveResource(UUID teamId, GameSummaries gameSummaries) {
        Map<String, DriveLink> links = Map.of(
                "home", HOME_LINK,
                "team", TEAM_LINK.format(teamId)
        );
        return generateDriveResource(links, gameSummaries);
    }

    @SneakyThrows
    private DriveResourceList<GameSummary> generateDriveResource(Map<String,DriveLink> links, GameSummaries gameSummaries) {
        List<DriveResource<GameSummary>> gameSummariesList = gameSummaries.getGames().stream()
                .map(this::convert)
                .collect(Collectors.toList());

        return DriveResourceList.<GameSummary>builder()
                .links(links)
                .data(Map.of("games", gameSummariesList))
                .schema(schemaGenerator.generateSchema(GameSummaries.class))
                .build();
    }

    private DriveResource<GameSummary> convert(GameSummary gameSummary) {
        Map<String, DriveLink> links = Map.of(
                "game", DriveLink.builder()
                        .href("/games/" + gameSummary.getId())
                        .title("View")
                        .build()
        );
        return DriveResource.<GameSummary>builder()
                .links(links)
                .data(gameSummary)
                .build();
    }
}
