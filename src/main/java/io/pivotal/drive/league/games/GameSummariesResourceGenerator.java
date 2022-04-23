package io.pivotal.drive.league.games;

import com.fasterxml.jackson.module.jsonSchema.JsonSchemaGenerator;
import io.pivotal.drive.mediatype.DriveLink;
import io.pivotal.drive.mediatype.DriveResource;
import io.pivotal.drive.places.view.PlaceSummary;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.stream.Collectors;

import static io.pivotal.drive.league.LeagueLinkConstants.MAIN_LINKS;
import static io.pivotal.drive.places.PlaceLinkConstants.*;

@Component
@RequiredArgsConstructor
public class GameSummariesResourceGenerator {

    private final JsonSchemaGenerator schemaGenerator;

    @SneakyThrows
    public DriveResource<GameSummariesDriveResource> generateDriveResource(GameSummaries gameSummaries) {
        GameSummariesDriveResource gameSummariesResource = GameSummariesDriveResource.builder()
                .games(gameSummaries.getGames().stream()
                        .map(this::convert)
                        .collect(Collectors.toList()))
                .build();

        return DriveResource.<GameSummariesDriveResource>builder()
                .links(MAIN_LINKS)
                .data(gameSummariesResource)
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
