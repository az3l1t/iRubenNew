package net.az3l1t.security_server.api.controller;

import net.az3l1t.security_server.api.dto.AuthenticationResponse;
import net.az3l1t.security_server.api.dto.UserRequestAuthenticate;
import net.az3l1t.security_server.core.entity.User;
import net.az3l1t.security_server.service.impl.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

//TODO НУЖНО СДЕЛАТЬ ТАК ЧТОБЫ ДРУГИЕ МИКРОСЕРВИСЫ ПОЛУЧАЛИ ЭТОТ ТОКЕН В СВОЕМ ЗАГОЛОВКЕ ()->ТОКЕН ХРАНИТЬ В БРАУЗЕРЕ
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
