package com.hua.demo.auth;

import com.hua.demo.exceptions.EmailAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public Object register(
            @RequestBody RegisterRequest registerRequest
    ) {
        try {
            return ResponseEntity.ok(authenticationService.register(registerRequest));
        } catch (JpaSystemException e){
            log.error(String.valueOf("Bad Request"));
            HashMap<String, String> error = new HashMap<>();
            error.put("error", "Bad request");
            return new ResponseEntity<>(error, null, 400);
        } catch (IllegalArgumentException | EmailAlreadyExistsException e){
            log.error(e.getMessage());
            HashMap<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return new ResponseEntity<>(error, null, 400);
        }
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody AuthenticationRequest authenticationRequest
    ) {
        return ResponseEntity.ok(authenticationService.authenticate(authenticationRequest));
    }

}
