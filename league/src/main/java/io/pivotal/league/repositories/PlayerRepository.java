package io.pivotal.league.repositories;

import io.pivotal.league.model.PlayerEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PlayerRepository extends JpaRepository<PlayerEntity, UUID> {
    List<PlayerEntity> findAllByTeamId(UUID teamId, Sort points);
}
