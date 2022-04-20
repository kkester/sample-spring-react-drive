package io.pivotal.drive;

import io.pivotal.drive.mediatype.DriveLink;
import io.pivotal.drive.mediatype.DriveResource;
import io.pivotal.drive.mediatype.PlaceSummariesDriveResource;
import io.pivotal.drive.view.Place;
import io.pivotal.drive.view.PlaceSummaries;
import io.pivotal.drive.view.PlaceSummary;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class DrivePlacesSummaryResourceGenerator {

    public static final String NON_VISITED_LINK_NAME = "nonVisited";
    public static final String VISITED_LINK_NAME = "visited";
    public static final DriveLink PLACES_TO_VISIT_LINK = DriveLink.builder()
            .href("/non-visited-places")
            .title("Places to Visit")
            .build();
    public static final DriveLink PLACES_VISITED_LINK = DriveLink.builder()
            .href("/visited-places")
            .title("Places Visited")
            .build();

    private final SchemaGenerator schemaGenerator;

    public DriveResource<Void> generateDriveResource() {
        Map<String, DriveLink> links =  Map.of(
                NON_VISITED_LINK_NAME, PLACES_TO_VISIT_LINK,
                VISITED_LINK_NAME, PLACES_VISITED_LINK
        );
        return DriveResource.<Void>builder()
                .links(links)
                .build();
    }

    public DriveResource<PlaceSummariesDriveResource> generateDriveResource(PlaceSummaries placeSummaries, boolean visited) {
        DriveLink createLink = DriveLink.builder()
                .title("New")
                .href("/places/default")
                .build();
        Map<String, DriveLink> links = visited ? Map.of(
                "create", createLink,
                NON_VISITED_LINK_NAME, PLACES_TO_VISIT_LINK
        ) : Map.of(
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

    public DriveResource<Place> generateDriveResource(Place place) {
        Map<String, DriveLink> links = Map.of(
                NON_VISITED_LINK_NAME, DriveLink.builder()
                        .href("/non-visited-places")
                        .title("Places to Visit")
                        .build(),
                VISITED_LINK_NAME, DriveLink.builder()
                        .href("/visited-places")
                        .title("Places Visited")
                        .build(),
                "save", DriveLink.builder()
                        .href(place.getId() == null ? "/places/" : "/places/" + place.getId())
                        .title("Save")
                        .method(place.getId() == null ? HttpMethod.POST : HttpMethod.PUT)
                        .type(MediaType.APPLICATION_JSON)
                        .build()
        );
        return DriveResource.<Place>builder()
                .links(links)
                .data(place)
                .schema(schemaGenerator.getPlaceSchema())
                .build();
    }
}
