package net.az3l1t.alphabet_server.infrastructure.repository;

import net.az3l1t.alphabet_server.core.entity.Letter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LetterRepository extends JpaRepository<Letter, Integer> {
}
