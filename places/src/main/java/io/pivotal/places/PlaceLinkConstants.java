package io.pivotal.places;

import io.pivotal.drive.mediatype.DriveLink;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;

import java.util.Map;
import java.util.UUID;

public class PlaceLinkConstants {

    public static final String NON_VISITED_LINK_NAME = "nonVisited";
    public static final String VISITED_LINK_NAME = "visited";
    public static final String SAVE_LINK_NAME = "save";
    public static final String HOME_LINK_NAME = "home";
    public static final String CREATE_LINK_NAME = "save";
    public static final String PLACE_LINK_NAME = "place";
    public static final String DELETE_LINK_NAME = "delete";

    public static final DriveLink HOME_LINK = DriveLink.builder()
            .href("/places")
            .title("Home")
            .build();
    public static final DriveLink PLACES_TO_VISIT_LINK = DriveLink.builder()
            .href("/non-visited-places")
            .title("Places to Visit")
            .build();
    public static final DriveLink PLACES_VISITED_LINK = DriveLink.builder()
            .href("/visited-places")
            .title("Places Visited")
            .build();
    public static final DriveLink NEW_PLACE_LINK = DriveLink.builder()
            .title("New")
            .href("/places/default")
            .build();
    public static final DriveLink SAVE_PLACE_LINK = DriveLink.builder()
            .href("/places")
            .title("Save")
            .method(HttpMethod.POST)
            .type(MediaType.APPLICATION_JSON)
            .build();
    public static final DriveLink UPDATE_PLACE_LINK = DriveLink.builder()
            .href("/places/%s")
            .title("Save")
            .method(HttpMethod.PUT)
            .type(MediaType.APPLICATION_JSON)
            .build();
    public static final DriveLink VIEW_PLACE_LINK = DriveLink.builder()
            .href("/places/%s")
            .title("View")
            .build();
    public static final DriveLink DELETE_PLACE_LINK = DriveLink.builder()
            .href("/places/%s")
            .title("Delete")
            .method(HttpMethod.DELETE)
            .build();

    public static Map<String, DriveLink> placeLinks(UUID placeId) {
        return DriveLink.of(
                PLACE_LINK_NAME, VIEW_PLACE_LINK.format(placeId),
                DELETE_LINK_NAME, DELETE_PLACE_LINK.format(placeId)
        );
    }

    private PlaceLinkConstants() {
    }
}
