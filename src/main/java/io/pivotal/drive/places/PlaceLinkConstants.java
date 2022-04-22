package io.pivotal.drive.places;

import io.pivotal.drive.mediatype.DriveLink;

public interface PlaceLinkConstants {
    String NON_VISITED_LINK_NAME = "nonVisited";
    String VISITED_LINK_NAME = "visited";

    DriveLink HOME_LINK = DriveLink.builder()
            .href("/places")
            .title("Home")
            .build();
    DriveLink PLACES_TO_VISIT_LINK = DriveLink.builder()
            .href("/non-visited-places")
            .title("Places to Visit")
            .build();
    DriveLink PLACES_VISITED_LINK = DriveLink.builder()
            .href("/visited-places")
            .title("Places Visited")
            .build();
}
