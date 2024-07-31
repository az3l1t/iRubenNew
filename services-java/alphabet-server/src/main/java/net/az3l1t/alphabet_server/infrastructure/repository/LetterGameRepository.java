package net.az3l1t.alphabet_server.infrastructure.repository;

import net.az3l1t.alphabet_server.core.entity.LetterGame;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LetterGameRepository extends JpaRepository<LetterGame, String> {
}
