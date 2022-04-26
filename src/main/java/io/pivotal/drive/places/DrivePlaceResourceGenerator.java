package io.pivotal.drive.places;

import io.pivotal.drive.mediatype.DriveLink;
import io.pivotal.drive.mediatype.DriveResource;
import io.pivotal.drive.places.view.NewPlace;
import io.pivotal.drive.places.view.Place;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.util.Map;

import static io.pivotal.drive.places.PlaceLinkConstants.*;

@Component
@RequiredArgsConstructor
public class DrivePlaceResourceGenerator {

    private final PlaceTypesSchemaGenerator schemaGenerator;

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
        return DriveResource.<Place>builder()
                .links(links)
                .data(place)
                .schema(schemaGenerator.getPlaceSchema())
                .build();
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
        return DriveResource.<NewPlace>builder()
                .links(links)
                .data(newPlace)
                .schema(schemaGenerator.getNewPlaceSchema())
                .build();
    }
}
