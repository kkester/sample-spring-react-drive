package io.pivotal.places;

import io.pivotal.drive.mediatype.DriveLink;
import io.pivotal.drive.mediatype.DriveResource;
import io.pivotal.drive.mediatype.DriveResourceGenerator;
import io.pivotal.places.view.NewPlace;
import io.pivotal.places.view.Place;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.util.Map;

import static io.pivotal.places.PlaceLinkConstants.*;


@Component
@RequiredArgsConstructor
public class DrivePlaceResourceGenerator {

    private final DriveResourceGenerator resourceGenerator;

    public DriveResource<Void> generatePlacesDriveResource() {
        Map<String, DriveLink> links =  Map.of(
                NON_VISITED_LINK_NAME, PLACES_TO_VISIT_LINK,
                VISITED_LINK_NAME, PLACES_VISITED_LINK
        );
        return DriveResource.<Void>builder()
                .links(links)
                .build();
    }

    public DriveResource<Place> generateDriveResource(Place place) {
        Map<String, DriveLink> links = Map.of(
                NON_VISITED_LINK_NAME, PLACES_TO_VISIT_LINK,
                VISITED_LINK_NAME, PLACES_VISITED_LINK,
                "save", DriveLink.builder()
                        .href("/places/" + place.getId())
                        .title("Save")
                        .method(HttpMethod.PUT)
                        .type(MediaType.APPLICATION_JSON)
                        .build()
        );
        return resourceGenerator.createDriveResource(links, place);
    }

    public DriveResource<NewPlace> generateDriveResource(NewPlace newPlace) {
        Map<String, DriveLink> links = Map.of(
                NON_VISITED_LINK_NAME, PLACES_TO_VISIT_LINK,
                VISITED_LINK_NAME, PLACES_VISITED_LINK,
                "save", DriveLink.builder()
                        .href("/places")
                        .title("Save")
                        .method(HttpMethod.POST)
                        .type(MediaType.APPLICATION_JSON)
                        .build()
        );
        return resourceGenerator.createDriveResource(links, newPlace);
    }
}
