package io.pivotal.drive.league.repositories;

import io.pivotal.drive.league.model.PlayerEntity;
import io.pivotal.drive.league.model.TeamEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PlayerRepository extends JpaRepository<PlayerEntity, UUID> {
    List<PlayerEntity> findAllByTeamId(UUID teamId);

    List<PlayerEntity> findByOrderByPoints();
}
