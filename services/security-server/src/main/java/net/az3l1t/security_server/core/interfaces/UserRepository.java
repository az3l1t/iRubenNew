package net.az3l1t.security_server.core.interfaces;

import net.az3l1t.security_server.core.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Integer> {
    Optional<User> findByEmail(String email);
}
