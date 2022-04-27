package io.pivotal.places;

import io.pivotal.places.model.PlaceEntity;
import io.pivotal.places.view.AuditDetails;
import io.pivotal.places.view.NewPlace;
import io.pivotal.places.view.Place;
import io.pivotal.places.view.PlaceSummary;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class PlaceConverter {

    public PlaceSummary convert(PlaceEntity placeEntity) {
        return PlaceSummary.builder()
                .id(placeEntity.getId())
                .country(placeEntity.getCountry())
                .visited(placeEntity.getVisited())
                .city(placeEntity.getCity())
                .state(placeEntity.getState())
                .build();
    }

    public Place convertToPlace(PlaceEntity placeEntity) {
        return Place.builder()
                .id(placeEntity.getId())
                .country(placeEntity.getCountry())
                .visited(placeEntity.getVisited())
                .city(placeEntity.getCity())
                .state(placeEntity.getState())
                .auditDetails(AuditDetails.builder()
                        .lastUpdatedDate(placeEntity.getLastUpdatedDate())
                        .createdDate(placeEntity.getCreatedDate())
                        .build())
                .description(placeEntity.getDescription())
                .build();
    }

    public PlaceEntity convertToPlaceEntity(NewPlace place) {
        return PlaceEntity.builder()
                .country(place.getCountry())
                .city(place.getCity())
                .state(place.getState())
                .lastUpdatedDate(LocalDate.now())
                .createdDate(LocalDate.now())
                .description(place.getDescription())
                .hasBeenVisited(false)
                .build();
    }
}
