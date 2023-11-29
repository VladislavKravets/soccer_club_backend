package com.example.soccer_club_backend.exceptions;

public class NoDataFoundException extends RuntimeException{

    public NoDataFoundException(String message) {
        super(message);
    }
}
