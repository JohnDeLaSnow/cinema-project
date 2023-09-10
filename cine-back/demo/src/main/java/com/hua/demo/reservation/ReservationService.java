package com.hua.demo.reservation;

import java.util.List;

public interface ReservationService {

    Reservation getReservationById(int id);
    List<Reservation> getAllReservations();
    Reservation saveReservation(String username,int id);
    Reservation saveReservation(Reservation reservation);
    Reservation editReservation(Reservation reservation);
    void deleteReservation(int id);
}

