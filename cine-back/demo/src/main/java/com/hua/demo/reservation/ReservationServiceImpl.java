package com.hua.demo.reservation;

import com.hua.demo.exceptions.MovieNotFoundException;
import com.hua.demo.exceptions.ReservationNotFoundException;
import com.hua.demo.exceptions.ShowingNotFoundException;
import com.hua.demo.exceptions.UserNotFoundException;
import com.hua.demo.movie.MovieRepository;
import com.hua.demo.showing.ShowingRepository;
import com.hua.demo.user.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ReservationServiceImpl implements ReservationService{

    public final ReservationRepository reservationRepository;
    public final ShowingRepository showingRepository;
    public final UserRepository userRepository;

    @Override
    public Reservation getReservationById(int id) {
        log.info("Fetching reservation with id: {}", id);
        return reservationRepository.findById(id).orElseThrow(() -> new ShowingNotFoundException(id));
    }

    @Override
    public List<Reservation> getAllReservations() {
        log.info("Fetching all reservations");
        return reservationRepository.findAll();
    }

    @Override
    public Reservation saveReservation(String username, int id) {
        Reservation reservation = new Reservation();
        reservation.setReservedUser(userRepository.findById(username).orElseThrow(()->new UserNotFoundException(username)));
        reservation.setReservedShowing(showingRepository.findById(id).orElseThrow(()->new ShowingNotFoundException(id)));
        return saveReservation(reservation);
    }

    @Override
    public Reservation saveReservation(Reservation reservation) {
        log.info("Saving new reservation with id '{}' to the database", reservation.getId());
        return reservationRepository.save(reservation);
    }

    @Override
    public Reservation editReservation(Reservation reservation) {
        //TODO
        return null;
    }

    @Override
    public void deleteReservation(int id) {
        log.info("Deleting reservation with id  '{}' from the database", id);
        reservationRepository.findById(id).orElseThrow(()-> new ReservationNotFoundException(id));
        reservationRepository.deleteById(id);
    }
}
