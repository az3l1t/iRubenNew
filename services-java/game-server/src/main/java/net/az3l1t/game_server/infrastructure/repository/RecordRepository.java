package net.az3l1t.game_server.infrastructure.repository;

import net.az3l1t.game_server.core.entity.RecordEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecordRepository extends JpaRepository<RecordEntity, String> {
}
