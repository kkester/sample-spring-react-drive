package io.pivotal.drive.places;

import io.pivotal.drive.error.ApiError;
import io.pivotal.drive.error.ApplicationException;
import io.pivotal.drive.places.model.PlaceEntity;
import io.pivotal.drive.places.view.NewPlace;
import io.pivotal.drive.places.view.Place;
import io.pivotal.drive.places.view.PlaceSummaries;
import io.pivotal.drive.places.view.PlaceSummary;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
                .orElseThrow(() -> new ApplicationException(HttpStatus.NOT_FOUND, ApiError.builder()
                        .code("resource-not-found")
                        .description("Place could not be found for id " + id)
                        .build()));
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
                .orElseThrow(() -> new ApplicationException(HttpStatus.NOT_FOUND, ApiError.builder()
                        .code("resource-not-found")
                        .description("Place could not be found for id " + id)
                        .build()));

        placeEntity.setCity(place.getCity());
        placeEntity.setCountry(place.getCountry());
        placeEntity.setDescription(place.getDescription());
        placeEntity.setVisited(place.getVisited());
        placeEntity.setHasBeenVisited(place.getVisited() != null);
        placeEntity.setState(place.getState());
        placeEntity.setLastUpdatedDate(LocalDate.now());

        placeRepository.save(placeEntity);
    }
}
