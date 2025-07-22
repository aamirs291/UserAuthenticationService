package com.scaler.userauthenticationoauth2service.exceptions;

public class UserAlreadyPresentException extends RuntimeException {

    public UserAlreadyPresentException(String message) {
        super(message);
    }
}
