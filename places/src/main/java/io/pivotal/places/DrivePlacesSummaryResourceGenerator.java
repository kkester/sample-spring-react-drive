package io.pivotal.places;

import io.pivotal.drive.mediatype.DriveLink;
import io.pivotal.drive.mediatype.DriveResource;

import io.pivotal.drive.mediatype.DriveResourceGenerator;
import io.pivotal.places.view.PlaceSummaries;
import io.pivotal.places.view.PlaceSummariesDriveResource;
import io.pivotal.places.view.PlaceSummary;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.stream.Collectors;

import static io.pivotal.places.PlaceLinkConstants.*;


@Component
@RequiredArgsConstructor
public class DrivePlacesSummaryResourceGenerator {

    private final DriveResourceGenerator resourceGenerator;

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
        return resourceGenerator.createDriveResource(links, placeSummariesResource);
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
        return resourceGenerator.createDriveResource(links, placeSummary);
    }
}
