package com.hua.demo.user;

import com.hua.demo.reservation.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> findByEmail(String email);

    @Query("SELECT reserv FROM reservation reserv WHERE reserv.reservedUser=?1")
    public Collection<Reservation> getUserReservations (User user);
}
