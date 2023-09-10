package com.hua.demo.auth;

import com.hua.demo.config.JWTService;
import com.hua.demo.exceptions.EmailAlreadyExistsException;
import com.hua.demo.exceptions.UserNotFoundException;
import com.hua.demo.user.Role;
import com.hua.demo.user.User;
import com.hua.demo.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTService jwtService;
    private final AuthenticationManager authenticationManager;

    public User register(RegisterRequest registerRequest) throws EmailAlreadyExistsException {
        if(userRepository.findByEmail(registerRequest.getEmail()).isPresent()){
            throw new EmailAlreadyExistsException(registerRequest.getEmail());
        }
        var user = User.builder()
                .firstname(registerRequest.getFirstname())
                .lastname(registerRequest.getLastname())
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .role(Role.USER)
                .reservations(new ArrayList<>())
                .build();
        userRepository.save(user);
        return user;
        /*var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();*/
    }

    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.getEmail(),
                        authenticationRequest.getPassword()
                )
        );
        var user = userRepository.findByEmail(authenticationRequest.getEmail()).orElseThrow(() -> new UserNotFoundException(authenticationRequest.getEmail()));
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
