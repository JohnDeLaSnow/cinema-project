package com.hua.demo.user;

import com.hua.demo.exceptions.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok().body(userService.getAllUsers());
    }

    @GetMapping("/{userEmail}")
    public Object getUserByEmail(@PathVariable("userEmail") String userEmail) {
        try {
            return ResponseEntity.ok().body(userService.getUserByEmail(userEmail));
        } catch (UserNotFoundException e) {
            log.error("Could not find user {} in the database", userEmail);
            HashMap<String, String> error = new HashMap<>();
            error.put("error", "User not found");
            return new ResponseEntity<>(error, null, 404);
        }
    }

    @GetMapping("/{userEmail}/reservations")
    public Object getUserReservations(@PathVariable("userEmail") String userEmail) {
        try {
            return ResponseEntity.ok().body(userService.getUserReservations(userEmail));
        } catch (UserNotFoundException e) {
            log.error("Could not find user {} in the database", userEmail);
            HashMap<String, String> error = new HashMap<>();
            error.put("error", "User not found");
            return new ResponseEntity<>(error, null, 404);
        }
    }

    @PutMapping("/{userEmail}")
    public Object editUserByEmail(@PathVariable("userEmail") String userEmail,@RequestBody User user){
        try {
            return ResponseEntity.ok().body(userService.editUser(userEmail,user));
        }catch (UserNotFoundException e){
            log.error("Could not find user {} in the database", userEmail);
            HashMap<String, String> error = new HashMap<>();
            error.put("error", "User not found");
            return new ResponseEntity<>(error, null, 404);
        }
    }
    @DeleteMapping("/delete/{username}")
    public ResponseEntity<?>deleteUser(@PathVariable("username") String username){
        try {
            userService.deleteUser(username);
            HashMap<String, String> success = new HashMap<>();
            success.put("message", "User successfully deleted");
            return ResponseEntity.ok(success);
        } catch (UserNotFoundException e) {
            log.error("Could not find user {} in the database", username);
            HashMap<String, String> error = new HashMap<>();
            error.put("message", "User not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
    }

}
