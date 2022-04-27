package io.pivotal.league.repositories;

import io.pivotal.league.model.GameEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface GameRepository extends JpaRepository<GameEntity, UUID> {
    List<GameEntity> findAllByHomeTeamIdOrVisitingTeamId(UUID homeId, UUID visitorId, Sort gameTime);
}
