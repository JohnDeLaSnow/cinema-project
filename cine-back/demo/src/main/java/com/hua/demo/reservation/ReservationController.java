package com.hua.demo.reservation;

import com.hua.demo.exceptions.MovieNotFoundException;
import com.hua.demo.exceptions.ReservationNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/reservations")
@RequiredArgsConstructor
@Slf4j
public class ReservationController {
    public final ReservationService reservationService;

    @GetMapping
    public ResponseEntity<List<Reservation>> getAllReservations() {
        return ResponseEntity.ok().body(reservationService.getAllReservations());
    }

    @GetMapping("/{id}")
    public Object getReservationById(@PathVariable("id") int id) {
        try {
            return ResponseEntity.ok().body(reservationService.getReservationById(id));
        } catch (ReservationNotFoundException e) {
            log.error("Could not find Reservation with id '{}' in the database", id);
            HashMap<String, String> error = new HashMap<>();
            error.put("error", "Reservation not found");
            return new ResponseEntity<>(error, null, 404);
        }
    }
    @PostMapping("/save/{username}-{id}")
    public ResponseEntity<Reservation> saveReservation (@PathVariable("username") String username,@PathVariable("id") int id){
        return ResponseEntity.ok().body(reservationService.saveReservation(username,id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?>deleteReservation(@PathVariable("id") int id){
        try {
            reservationService.deleteReservation(id);
            return ResponseEntity.ok().build();
        } catch (ReservationNotFoundException e) {
            log.error("Could not find reservation with id '{}' in the database", id);
            HashMap<String, String> error = new HashMap<>();
            error.put("error", "Reservation not found");
            return new ResponseEntity<>(error, null, 404);
        }
    }

    //TODO GET ALL RESERVATIONS FROM SHOWING
}
