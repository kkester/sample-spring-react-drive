package io.pivotal.drive.places;

import io.pivotal.drive.mediatype.DriveMediaType;
import io.pivotal.drive.mediatype.DriveResource;
import io.pivotal.drive.places.view.PlaceSummariesDriveResource;
import io.pivotal.drive.places.view.NewPlace;
import io.pivotal.drive.places.view.Place;
import io.pivotal.drive.places.view.PlaceSummaries;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
@Slf4j
public class PlaceSummariesController {

    private final PlacesService placesService;
    private final DrivePlacesSummaryResourceGenerator resourceGenerator;

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

    @PostMapping(value = "/places", produces = DriveMediaType.APPLICATION_DRIVE_PLUS_JSON_VALUE)
    public DriveResource<PlaceSummariesDriveResource> savePlace(@RequestBody NewPlace place) {
        log.info("Saving place {}", place);
        placesService.save(place);
        PlaceSummaries placeSummaries = placesService.getPlacesToVisit();
        return resourceGenerator.generateDriveResource(placeSummaries, false);
    }

    @PutMapping(value = "/places/{id}", produces = DriveMediaType.APPLICATION_DRIVE_PLUS_JSON_VALUE)
    public DriveResource<PlaceSummariesDriveResource> updatePlace(@PathVariable UUID id, @RequestBody Place place) {
        log.info("Updating place {} with {}", id, place);
        placesService.update(id, place);
        PlaceSummaries placeSummaries = placesService.getPlacesVisited();
        return resourceGenerator.generateDriveResource(placeSummaries, true);
    }

    @DeleteMapping(value = "/places/{id}", produces = DriveMediaType.APPLICATION_DRIVE_PLUS_JSON_VALUE)
    public DriveResource<PlaceSummariesDriveResource> deletePlaceById(@PathVariable UUID id) {
        placesService.deletePlaceById(id);
        PlaceSummaries placeSummaries = placesService.getPlacesToVisit();
        return resourceGenerator.generateDriveResource(placeSummaries, false);
    }
}
