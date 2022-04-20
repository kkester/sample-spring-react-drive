package io.pivotal.drive;

import io.pivotal.drive.mediatype.DriveMediaType;
import io.pivotal.drive.mediatype.DriveResource;
import io.pivotal.drive.mediatype.PlaceSummariesDriveResource;
import io.pivotal.drive.view.Place;
import io.pivotal.drive.view.PlaceSummaries;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class PlacesController {

    private final PlacesService placesService;
    private final DrivePlacesSummaryResourceGenerator resourceGenerator;

    @GetMapping(value = "/places", produces = DriveMediaType.APPLICATION_DRIVE_PLUS_JSON_VALUE)
    public DriveResource<Void> getPlaces() {
        return resourceGenerator.generateDriveResource();
    }

    @GetMapping(value = "/visited-places", produces = DriveMediaType.APPLICATION_DRIVE_PLUS_JSON_VALUE)
    public DriveResource<PlaceSummariesDriveResource> getVisitedPlaces() {
        PlaceSummaries placeSummaries = placesService.getPlacesVisited();
        return resourceGenerator.generateDriveResource(placeSummaries, true);
    }

    @GetMapping(value = "/non-visited-places", produces = DriveMediaType.APPLICATION_DRIVE_PLUS_JSON_VALUE)
    public DriveResource<PlaceSummariesDriveResource> getNonVisitedPlaces() {
        PlaceSummaries placeSummaries = placesService.getPlacesToVisit();
        return resourceGenerator.generateDriveResource(placeSummaries, false);
    }

    @GetMapping(value = "/places/{id}", produces = DriveMediaType.APPLICATION_DRIVE_PLUS_JSON_VALUE)
    public DriveResource<Place> getPlaceById(@PathVariable UUID id) {
        Place place = placesService.getPlaceById(id);
        return resourceGenerator.generateDriveResource(place);
    }

    @GetMapping(value = "/places/default", produces = DriveMediaType.APPLICATION_DRIVE_PLUS_JSON_VALUE)
    public DriveResource<Place> getDefaultPlace() {
        Place place = Place.builder()
                .createdDate(LocalDate.now())
                .lastUpdatedDate(LocalDate.now())
                .build();
        return resourceGenerator.generateDriveResource(place);
    }

    @DeleteMapping(value = "/places/{id}", produces = DriveMediaType.APPLICATION_DRIVE_PLUS_JSON_VALUE)
    public DriveResource<PlaceSummariesDriveResource> deletePlaceById(@PathVariable UUID id) {
        placesService.deletePlaceById(id);
        PlaceSummaries placeSummaries = placesService.getPlacesToVisit();
        return resourceGenerator.generateDriveResource(placeSummaries, false);
    }
}
