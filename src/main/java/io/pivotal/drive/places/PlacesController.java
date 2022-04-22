package io.pivotal.drive.places;

import io.pivotal.drive.mediatype.DriveMediaType;
import io.pivotal.drive.mediatype.DriveResource;
import io.pivotal.drive.places.view.NewPlace;
import io.pivotal.drive.places.view.Place;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
@Slf4j
public class PlacesController {

    private final PlacesService placesService;
    private final DrivePlaceResourceGenerator resourceGenerator;

    @GetMapping(value = "/places", produces = DriveMediaType.APPLICATION_DRIVE_PLUS_JSON_VALUE)
    public DriveResource<Void> getPlaces() {
        return resourceGenerator.generatePlacesDriveResource();
    }

    @GetMapping(value = "/places/{id}", produces = DriveMediaType.APPLICATION_DRIVE_PLUS_JSON_VALUE)
    public DriveResource<Place> getPlaceById(@PathVariable UUID id) {
        Place place = placesService.getPlaceById(id);
        return resourceGenerator.generateDriveResource(place);
    }

    @GetMapping(value = "/places/default", produces = DriveMediaType.APPLICATION_DRIVE_PLUS_JSON_VALUE)
    public DriveResource<NewPlace> getDefaultPlace() {
        NewPlace newPlace = NewPlace.builder()
                .country("United States")
                .build();
        return resourceGenerator.generateDriveResource(newPlace);
    }
}
