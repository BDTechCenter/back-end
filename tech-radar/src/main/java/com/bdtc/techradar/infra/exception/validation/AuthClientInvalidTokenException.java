package com.bdtc.techradar.infra.exception.validation;

public class AuthClientInvalidTokenException extends RuntimeException{
    public AuthClientInvalidTokenException() {
        super("Invalid token! Can't validate user!");
    }
}
