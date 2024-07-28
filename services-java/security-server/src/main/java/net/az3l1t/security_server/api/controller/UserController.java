package net.az3l1t.security_server.api.controller;

import net.az3l1t.security_server.api.dto.AuthenticationResponse;
import net.az3l1t.security_server.api.dto.UserRequestAuthenticate;
import net.az3l1t.security_server.core.entity.User;
import net.az3l1t.security_server.service.impl.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:8010")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody User request
    ) {
        return ResponseEntity.ok(userService.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> login(
            @RequestBody UserRequestAuthenticate request
            ) {
        return ResponseEntity.ok(userService.authenticate(request));
    }

}
