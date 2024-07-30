package net.az3l1t.alphabet_server.infrastructure.repository;

import net.az3l1t.alphabet_server.core.entity.LetterButtonEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LetterButtonRepository extends JpaRepository<LetterButtonEntity, String> {
}
