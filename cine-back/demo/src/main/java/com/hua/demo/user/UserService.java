package com.hua.demo.user;

import com.hua.demo.reservation.Reservation;

import java.util.Collection;
import java.util.List;

public interface UserService {
    User getUserByEmail(String email);
    List<User> getAllUsers();
    User saveUser(User user);
    User editUser(String email, User user);
    void deleteUser(String email);
    Collection<Reservation> getUserReservations (String email);
}
