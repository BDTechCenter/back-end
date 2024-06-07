package com.bdtc.techradar.infra.exception.validation;

public class ConflictInPathParameters extends RuntimeException{
    public ConflictInPathParameters(String message) {
        super(message);
    }
}