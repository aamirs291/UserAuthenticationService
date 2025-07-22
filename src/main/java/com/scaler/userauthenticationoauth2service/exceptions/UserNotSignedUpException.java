package com.scaler.userauthenticationoauth2service.exceptions;

public class UserNotSignedUpException extends RuntimeException {
    public UserNotSignedUpException(String message) {
        super(message);
    }
}
