package com.ottonsam.revezai.exceptions;

public class UserFoundException extends RuntimeException {

    public UserFoundException() {
        super("User already exists");
    }
    
}
