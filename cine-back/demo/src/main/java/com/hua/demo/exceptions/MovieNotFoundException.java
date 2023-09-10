package com.hua.demo.exceptions;

public class MovieNotFoundException extends RuntimeException{
        public MovieNotFoundException(String title) {
            super("Could not find movie with title '" + title + "' in the database");;
        }
}
