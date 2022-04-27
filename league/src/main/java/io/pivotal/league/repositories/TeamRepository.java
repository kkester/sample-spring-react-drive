package io.pivotal.league.repositories;

import io.pivotal.league.model.TeamEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TeamRepository extends JpaRepository<TeamEntity, UUID> {
}
