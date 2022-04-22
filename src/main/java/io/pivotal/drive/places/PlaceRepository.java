package io.pivotal.drive.places;

import io.pivotal.drive.places.model.PlaceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PlaceRepository extends JpaRepository<PlaceEntity, UUID> {
    List<PlaceEntity> findAllByHasBeenVisited(boolean hasBeenVisited);
}
