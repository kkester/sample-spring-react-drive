package io.pivotal.drive.places;

import io.pivotal.drive.mediatype.DriveLink;
import io.pivotal.drive.mediatype.DriveResource;
import io.pivotal.drive.places.view.PlaceSummariesDriveResource;
import io.pivotal.drive.places.view.PlaceSummaries;
import io.pivotal.drive.places.view.PlaceSummary;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.stream.Collectors;

import static io.pivotal.drive.places.PlaceLinkConstants.*;

@Component
@RequiredArgsConstructor
public class DrivePlacesSummaryResourceGenerator {

    private final SchemaGenerator schemaGenerator;

    public DriveResource<PlaceSummariesDriveResource> generateDriveResource(PlaceSummaries placeSummaries, boolean visited) {
        DriveLink createLink = DriveLink.builder()
                .title("New")
                .href("/places/default")
                .build();
        Map<String, DriveLink> links = visited ? Map.of(
                "home", HOME_LINK,
                "create", createLink,
                NON_VISITED_LINK_NAME, PLACES_TO_VISIT_LINK
        ) : Map.of(
                "home", HOME_LINK,
                "create", createLink,
                VISITED_LINK_NAME, PLACES_VISITED_LINK
        );
        PlaceSummariesDriveResource placeSummariesResource = PlaceSummariesDriveResource.builder()
                .places(placeSummaries.getPlaces().stream()
                        .map(this::convert)
                        .collect(Collectors.toList()))
                .build();
        return DriveResource.<PlaceSummariesDriveResource>builder()
                .links(links)
                .data(placeSummariesResource)
                .schema(schemaGenerator.getPlaceSummariesSchema())
                .build();
    }

    private DriveResource<PlaceSummary> convert(PlaceSummary placeSummary) {
        Map<String, DriveLink> links = Map.of(
                "place", DriveLink.builder()
                        .href("/places/" + placeSummary.getId())
                        .title("View")
                        .build(),
                "delete", DriveLink.builder()
                        .href("/places/" + placeSummary.getId())
                        .title("Delete")
                        .method(HttpMethod.DELETE)
                        .build()
        );
        return DriveResource.<PlaceSummary>builder()
                .links(links)
                .data(placeSummary)
                .build();
    }
}
