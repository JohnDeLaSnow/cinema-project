package com.hua.demo.exceptions;

public class ReservationNotFoundException extends RuntimeException {
    public ReservationNotFoundException(int id) {
        super("Could not find reservation with id '" + id + "' in the database");
    }
}