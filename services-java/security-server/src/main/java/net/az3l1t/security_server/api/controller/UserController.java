package net.az3l1t.security_server.api.controller;

import net.az3l1t.security_server.api.dto.AuthenticationResponse;
import net.az3l1t.security_server.api.dto.ExpResponse;
import net.az3l1t.security_server.api.dto.UserRequestAuthenticate;
import net.az3l1t.security_server.core.entity.User;
import net.az3l1t.security_server.service.impl.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@RestController
@CrossOrigin(origins = "http://localhost:8010")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public CompletableFuture<AuthenticationResponse> register(
            @RequestBody User request
    ) {
        return userService.register(request);
    }

    @PostMapping("/authenticate")
    public CompletableFuture<AuthenticationResponse> login(
            @RequestBody UserRequestAuthenticate request
            ) {
        return userService.authenticate(request);
    }

    @GetMapping("/{username}")
    public CompletableFuture<ExpResponse> getExpByUsername(
            @PathVariable String username
    )
    {
        return userService.getExpByUsername(username);
    }

    @GetMapping("/addExp/{username}")
    public CompletableFuture<ExpResponse> addExp(
            @PathVariable String username
    ) {
        return userService.addExp(username);
    }

}
