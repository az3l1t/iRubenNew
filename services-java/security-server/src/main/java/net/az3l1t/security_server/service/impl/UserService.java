package net.az3l1t.security_server.service.impl;

import net.az3l1t.security_server.api.dto.AuthenticationResponse;
import net.az3l1t.security_server.api.dto.ExpResponse;
import net.az3l1t.security_server.api.dto.UserRequestAuthenticate;
import net.az3l1t.security_server.core.entity.Role;
import net.az3l1t.security_server.core.entity.User;
import net.az3l1t.security_server.infrastructure.repository.UserRepository;
import net.az3l1t.security_server.service.jwt.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public AuthenticationResponse register(User request){
        User user = new User();
        user.setFirstname(request.getFirstname());
        user.setLastname(request.getLastname());
        user.setUsername(request.getUsername());
        user.setExp(0);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        if(request.getRole()==null){
            user.setRole(Role.USER);
        } else {
            user.setRole(request.getRole());
        }
        user = userRepository.save(user);

        String token = jwtService.generateToken(user);
        return new AuthenticationResponse(token, user.getUsername());
    }

    //todo Not user but userRequest
    public AuthenticationResponse authenticate(UserRequestAuthenticate request){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        User user = userRepository.findByUsername(request.getUsername()).orElseThrow(
                ()-> new RuntimeException("User was not found! : %s".formatted(request.getUsername()))
        );
        String token = jwtService.generateToken(user);

        return new AuthenticationResponse(token, user.getUsername());
    }

    public ExpResponse getExpByUsername(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(
                ()->new RuntimeException("User was not found : %s".formatted(username))
        );
        return new ExpResponse(user.getExp());
    }
}
