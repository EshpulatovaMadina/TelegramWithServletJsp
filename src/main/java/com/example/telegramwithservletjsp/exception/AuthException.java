package com.example.telegramwithservletjsp.exception;

public class AuthException extends RuntimeException {
    public AuthException(String message) {
        super(message);
    }
}
