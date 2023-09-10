package com.hua.demo.exceptions;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String username) {
        super("Could not find user " + username + " in the database");
    }
}
