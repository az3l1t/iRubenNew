package net.az3l1t.security_server.api.dto;

import jakarta.persistence.Column;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestAuthenticate {
    @Column(unique = true,nullable = false)
    private String username;
    @Column(nullable = false)
    private String password;
}
