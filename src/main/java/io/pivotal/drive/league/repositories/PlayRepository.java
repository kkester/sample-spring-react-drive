package io.pivotal.drive.league.repositories;

import io.pivotal.drive.league.model.PlayEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PlayRepository extends JpaRepository<PlayEntity, UUID> {
    List<PlayEntity> findAllByGameId(UUID gameId);
    List<PlayEntity> findAllByPlayerId(UUID playerId);
}
