package io.pivotal.places;

import io.pivotal.drive.mediatype.DriveLink;
import io.pivotal.drive.mediatype.DriveResource;
import io.pivotal.drive.mediatype.DriveResourceGenerator;
import io.pivotal.places.view.NewPlace;
import io.pivotal.places.view.Place;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

import static io.pivotal.places.PlaceLinkConstants.*;

@Component
@RequiredArgsConstructor
public class DrivePlaceResourceGenerator {

    private final DriveResourceGenerator resourceGenerator;

    public DriveResource<Void> generatePlacesDriveResource() {
        Map<String, DriveLink> links =  DriveLink.of(
                NON_VISITED_LINK_NAME, PLACES_TO_VISIT_LINK,
                VISITED_LINK_NAME, PLACES_VISITED_LINK
        );
        return DriveResource.<Void>builder()
                .links(links)
                .build();
    }

    public DriveResource<Place> generateDriveResource(Place place) {
        Map<String, DriveLink> links = DriveLink.of(
                NON_VISITED_LINK_NAME, PLACES_TO_VISIT_LINK,
                VISITED_LINK_NAME, PLACES_VISITED_LINK,
                SAVE_LINK_NAME, UPDATE_PLACE_LINK.format(place.getId())
        );
        return resourceGenerator.createDriveResource(links, place);
    }

    public DriveResource<NewPlace> generateDriveResource(NewPlace newPlace) {
        Map<String, DriveLink> links = DriveLink.of(
                NON_VISITED_LINK_NAME, PLACES_TO_VISIT_LINK,
                VISITED_LINK_NAME, PLACES_VISITED_LINK,
                SAVE_LINK_NAME, SAVE_PLACE_LINK
        );
        return resourceGenerator.createDriveResource(links, newPlace);
    }
}
