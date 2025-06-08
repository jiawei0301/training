package com.example.springpractice.exception;

public class UserNotFoundException extends RuntimeException {

    // missing status code
    // missing serilizationUID
    public UserNotFoundException(Long id) {
        super("User not found with id: " + id);
    }
}
