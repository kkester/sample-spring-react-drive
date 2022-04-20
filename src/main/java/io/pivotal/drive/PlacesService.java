package io.pivotal.drive;

import io.pivotal.drive.mediatype.DriveLink;
import io.pivotal.drive.mediatype.DriveResource;
import io.pivotal.drive.view.Place;
import io.pivotal.drive.view.PlaceSummaries;
import io.pivotal.drive.view.PlaceSummary;
import io.pivotal.drive.view.States;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class PlacesService {

    public PlaceSummaries getPlacesToVisit() {
        return PlaceSummaries.builder()
                .places(List.of(PlaceSummary.builder()
                        .id(UUID.randomUUID())
                        .city("Houston")
                        .state(States.TX)
                        .country("USA")
                        .visited(LocalDate.now())
                        .build()))
                .build();
    }

    public PlaceSummaries getPlacesVisited() {
        return PlaceSummaries.builder()
                .places(List.of(PlaceSummary.builder()
                        .id(UUID.randomUUID())
                        .city("Charlotte")
                        .state(States.NC)
                        .country("USA")
                        .visited(LocalDate.now())
                        .build()))
                .build();
    }

    public Place getPlaceById(UUID id) {
        return Place.builder()
                .id(id)
                .city("Charlotte")
                .state(States.NC)
                .country("USA")
                .visited(LocalDate.now())
                .createdDate(LocalDate.now().minusDays(7))
                .lastUpdatedDate(LocalDate.now().minusDays(3))
                .build();
    }

    public void deletePlaceById(UUID id) {

    }
}
