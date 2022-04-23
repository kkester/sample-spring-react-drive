package io.pivotal.drive.league.repositories;

import io.pivotal.drive.league.model.TeamEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public interface TeamRepository extends JpaRepository<TeamEntity, UUID> {
    List<TeamEntity> findByOrderByTotalGames();
}
