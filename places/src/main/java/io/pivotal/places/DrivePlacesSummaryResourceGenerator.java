package io.pivotal.places;

import io.pivotal.drive.mediatype.DriveDataResource;
import io.pivotal.drive.mediatype.DriveLink;
import io.pivotal.drive.mediatype.DriveResource;

import io.pivotal.drive.mediatype.DriveResourceGenerator;
import io.pivotal.places.view.PlaceSummaries;
import io.pivotal.places.view.PlaceSummary;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static io.pivotal.places.PlaceLinkConstants.*;


@Component
@RequiredArgsConstructor
public class DrivePlacesSummaryResourceGenerator {

    private final DriveResourceGenerator resourceGenerator;

    public DriveDataResource generateDriveResource(PlaceSummaries placeSummaries, boolean visited) {
        Map<String, DriveLink> links = visited ? DriveLink.of(
                HOME_LINK_NAME, HOME_LINK,
                CREATE_LINK_NAME, NEW_PLACE_LINK,
                NON_VISITED_LINK_NAME, PLACES_TO_VISIT_LINK
        ) : DriveLink.of(
                HOME_LINK_NAME, HOME_LINK,
                CREATE_LINK_NAME, NEW_PLACE_LINK,
                VISITED_LINK_NAME, PLACES_VISITED_LINK
        );
        List<DriveResource<PlaceSummary>> resources = resourceGenerator.createDriveResourceList(
                placeSummaries.getPlaces(), placeSummary -> placeLinks(placeSummary.getId()));
        return resourceGenerator.createDriveDataResource(links, Map.of("places", resources), PlaceSummaries.class);
    }

    private DriveResource<PlaceSummary> convert(PlaceSummary placeSummary) {
        Map<String, DriveLink> links = DriveLink.of(
                PLACE_LINK_NAME, VIEW_PLACE_LINK,
                DELETE_LINK_NAME, DELETE_PLACE_LINK
        );
        return resourceGenerator.createDriveResource(links, placeSummary);
    }
}
