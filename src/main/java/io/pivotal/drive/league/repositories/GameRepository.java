package io.pivotal.drive.league.repositories;

import io.pivotal.drive.league.model.GameEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface GameRepository extends JpaRepository<GameEntity, UUID> {
    List<GameEntity> findAllByHomeTeamIdOrVisitingTeamId(UUID homeId, UUID visitorId);
}
