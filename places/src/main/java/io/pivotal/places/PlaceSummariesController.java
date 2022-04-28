package io.pivotal.places;

import io.pivotal.drive.mediatype.DriveDataResource;
import io.pivotal.drive.mediatype.DriveMediaType;
import io.pivotal.places.view.NewPlace;
import io.pivotal.places.view.Place;
import io.pivotal.places.view.PlaceSummaries;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
@Slf4j
public class PlaceSummariesController {

    private final PlacesService placesService;
    private final DrivePlacesSummaryResourceGenerator resourceGenerator;

    @GetMapping(value = "/visited-places", produces = DriveMediaType.APPLICATION_DRIVE_PLUS_JSON_VALUE)
    public DriveDataResource getVisitedPlaces() {
        PlaceSummaries placeSummaries = placesService.getPlacesVisited();
        return resourceGenerator.generateDriveResource(placeSummaries, true);
    }

    @GetMapping(value = "/non-visited-places", produces = DriveMediaType.APPLICATION_DRIVE_PLUS_JSON_VALUE)
    public DriveDataResource getNonVisitedPlaces() {
        PlaceSummaries placeSummaries = placesService.getPlacesToVisit();
        return resourceGenerator.generateDriveResource(placeSummaries, false);
    }

    @PostMapping(value = "/places", produces = DriveMediaType.APPLICATION_DRIVE_PLUS_JSON_VALUE)
    public DriveDataResource savePlace(@Valid @RequestBody NewPlace place) {
        log.info("Saving place {}", place);
        placesService.save(place);
        PlaceSummaries placeSummaries = placesService.getPlacesToVisit();
        return resourceGenerator.generateDriveResource(placeSummaries, false);
    }

    @PutMapping(value = "/places/{id}", produces = DriveMediaType.APPLICATION_DRIVE_PLUS_JSON_VALUE)
    public DriveDataResource updatePlace(@PathVariable UUID id, @RequestBody Place place) {
        log.info("Updating place {} with {}", id, place);
        placesService.update(id, place);
        PlaceSummaries placeSummaries = placesService.getPlacesVisited();
        return resourceGenerator.generateDriveResource(placeSummaries, true);
    }

    @DeleteMapping(value = "/places/{id}", produces = DriveMediaType.APPLICATION_DRIVE_PLUS_JSON_VALUE)
    public DriveDataResource deletePlaceById(@PathVariable UUID id) {
        placesService.deletePlaceById(id);
        PlaceSummaries placeSummaries = placesService.getPlacesToVisit();
        return resourceGenerator.generateDriveResource(placeSummaries, false);
    }
}
