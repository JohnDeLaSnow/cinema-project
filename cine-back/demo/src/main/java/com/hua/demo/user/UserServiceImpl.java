package com.hua.demo.user;

import com.hua.demo.exceptions.UserNotFoundException;
import com.hua.demo.reservation.Reservation;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    public final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User getUserByEmail(String email) {
        log.info("Fetching user with email: {}", email);
        return userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException(email));
    }

    @Override
    public User editUser(String email,User user){
        User oldUser = getUserByEmail(email);
        if(user.getFirstname()!=null){
            oldUser.setFirstname(user.getFirstname());
        }
        if(user.getLastname()!=null){
            oldUser.setLastname(user.getLastname());
        }
        return oldUser;
    }

    @Override
    public List<User> getAllUsers() {
        log.info("Fetching all users");
        return userRepository.findAll();
    }

    @Override
    public User saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        log.info("Saving new user {} to the database", user.getUsername());
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(String email) {
        userRepository.findByEmail(email).orElseThrow(()->new UserNotFoundException(email));
        log.info("Deleting user {} from the database", email);
        userRepository.deleteById(email);
    }

    @Override
    public Collection<Reservation> getUserReservations(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException(email));
        return userRepository.getUserReservations(user);
    }
}
