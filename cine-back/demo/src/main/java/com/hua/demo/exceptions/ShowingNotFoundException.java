package com.hua.demo.exceptions;

public class ShowingNotFoundException extends RuntimeException {
    public ShowingNotFoundException(int id) {
        super("Could not find showing with id '" + id + "' in the database");
    }
}
