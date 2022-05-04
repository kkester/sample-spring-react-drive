package io.pivotal.places;


import io.pivotal.common.error.ApplicationException;
import io.pivotal.places.model.PlaceEntity;
import io.pivotal.places.view.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlacesService {

    private final PlaceRepository placeRepository;
    private final PlaceConverter placeConverter;

    public PlaceSummaries getPlacesToVisit() {
        List<PlaceSummary> places = placeRepository.findAllByHasBeenVisited(false).stream()
                .map(placeConverter::convert)
                .collect(Collectors.toList());
        return PlaceSummaries.builder()
                .places(places)
                .build();
    }

    public PlaceSummaries getPlacesVisited() {
        List<PlaceSummary> places = placeRepository.findAllByHasBeenVisited(true).stream()
                .map(placeConverter::convert)
                .collect(Collectors.toList());
        return PlaceSummaries.builder()
                .places(places)
                .build();
    }

    public Place getPlaceById(UUID id) {
        return placeRepository.findById(id)
                .map(placeConverter::convertToPlace)
                .orElseThrow(() -> ApplicationException.resourceNotFound("Place Not Found"));
    }

    public void deletePlaceById(UUID id) {
        placeRepository.deleteById(id);
    }

    public void save(NewPlace newPlace) {
        PlaceEntity placeEntity = placeConverter.convertToPlaceEntity(newPlace);
        placeRepository.save(placeEntity);
    }

    public void update(UUID id, Place place) {
        PlaceEntity placeEntity = placeRepository.findById(id)
                .orElseThrow(() -> ApplicationException.resourceNotFound("Place Not Found"));

        AuditDetails auditDetails = place.getAuditDetails();
        placeEntity.setCity(place.getCity());
        placeEntity.setCountry(place.getCountry());
        placeEntity.setDescription(place.getDescription());
        placeEntity.setState(place.getState());
        placeEntity.setVisited(auditDetails.getVisited());
        placeEntity.setHasBeenVisited(auditDetails.getVisited() != null);
        placeEntity.setPlannedVisitDate(auditDetails.getPlannedVisitDate());
        placeEntity.setLastUpdatedDate(LocalDate.now());

        placeRepository.save(placeEntity);
    }
}
